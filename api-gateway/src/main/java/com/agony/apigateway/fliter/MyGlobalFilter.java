package com.agony.apigateway.fliter;

import com.agony.apiclient.utils.SignUtils;
import com.agony.project.model.entity.InterfaceInfo;
import com.agony.project.model.entity.User;
import com.agony.project.service.InnerInterfaceService;
import com.agony.project.service.InnerUserInterfaceService;
import com.agony.project.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {


    private static final List<String> WHITE_IP_LIST = Arrays.asList("127.0.0.1", "127.0.0.2");

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceService innerInterfaceService;

    @DubboReference
    private InnerUserInterfaceService innerUserInterfaceService;

    @Override
    public int getOrder() {
        // 指定过滤器的顺序
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //

        log.info(" =========== 日志检测测试 =========");
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识: {}", request.getId());
        log.info("请求路径: {}", path);
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", request.getQueryParams());
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求来源地址: {}", sourceAddress);
        log.info("请求来源地址: {}", request.getRemoteAddress());

        ServerHttpResponse response = exchange.getResponse();
        // 2. 访问控制， 黑白名单
        if (!WHITE_IP_LIST.contains(sourceAddress)) {
            // response.setStatusCode(HttpStatus.FORBIDDEN);
            // return response.setComplete();
            return handleNoAuth(response);
        }

        // 3. 用户鉴权
        String accessKey = request.getHeaders().getFirst("accessKey");
        String nonce = request.getHeaders().getFirst("nonce");
        String timestamp = request.getHeaders().getFirst("timestamp");
        String sign = request.getHeaders().getFirst("sign");
        String body = request.getHeaders().getFirst("body");
        if (StringUtils.isAnyBlank(accessKey, nonce, timestamp, sign, body)) {
            // throw new RuntimeException("请求参数为空");
            return handleNoAuth(response);
        }
        //   accessKey 校验 实际是从数据库中查询
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("get invokeUser error: ", e);
        }

        // if (!"agony".equals(accessKey)) {
        //     // throw new RuntimeException("accessKey 无权限");
        //     return handleNoAuth(response);
        // }


        // nonce 校验
        if (Long.parseLong(nonce) > 10000) {
            // throw new RuntimeException("nonce 无权限");
            return handleNoAuth(response);
        }
        // timestamp 校验
        long currentTime = System.currentTimeMillis() / 1000;
        long allTime = 5 * 60L; // 五分钟
        if (currentTime - Long.parseLong(timestamp) >= allTime) {
            // throw new RuntimeException("timestamp 无权限");
            return handleNoAuth(response);
        }

        // 从数据库查询 sign 校验

        String secretKey = invokeUser.getSecretKey();
        String genSign = SignUtils.genSign(body, secretKey);
        if (!genSign.equals(sign)) {
            // throw new RuntimeException("sign 无权限");
            return handleNoAuth(response);
        }

        // 4.从数据库中查询接口是否存在
        InterfaceInfo invokeInterface = null;
        try {
            invokeInterface = innerInterfaceService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("get invokeInterface error ", e);
        }
        if (invokeInterface == null) {
            return handleNoAuth(response);
        }

        // 响应日志处理
        return chain.filter(exchange.mutate().response(handlerDecorator(exchange, invokeInterface.getId(), invokeUser.getId())).build());
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @return
     */
    private ServerHttpResponseDecorator handlerDecorator(ServerWebExchange exchange, long interfaceId, long userId) {

        log.info("===== 响应处理 =====");
        var originalResponse = exchange.getResponse();
        var bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                log.info("body instanceof Flux: {}", (body instanceof Flux));
                // 判断响应体是否是Flux类型
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);


                    // 返回一个处理后的响应体
                    // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // todo 调用成功，接口次数加1 --> UserInterfaceInfoService.invokeCount
                        try {
                            innerUserInterfaceService.invokeCount(interfaceId, userId);
                        } catch (Exception e) {
                            log.error("invokeCount error ", e);
                        }
                        // 读取响应体的内容并转换为字节数组
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);//释放掉内存
                        // 构建日志
                        StringBuilder sb2 = new StringBuilder(200);
                        List<Object> rspArgs = new ArrayList<>();
                        rspArgs.add(originalResponse.getStatusCode());
                        String data = new String(content, StandardCharsets.UTF_8);//data
                        sb2.append(data);
                        log.info("响应结果 data: {}", data);
                        // 将处理后的内容重新包装成DataBuffer并返回
                        return bufferFactory.wrap(content);
                    }));
                } else {
                    log.error("<--- {} 响应code异常", getStatusCode());
                }
                return super.writeWith(body);
            }
        };
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}