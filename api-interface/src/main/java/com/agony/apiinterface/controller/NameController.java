package com.agony.apiinterface.controller;


import com.agony.apiclient.client.ApiClient;
import com.agony.apiclient.model.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Agony
 * @create 2024/4/3 21:22
 */
@RestController
@RequestMapping("/name")
@Slf4j
public class NameController {


    @GetMapping("/get")
    public String getNameByGet(@RequestParam("name") String name, HttpServletRequest request) {
        log.info(" ====== request {} ", request.getHeader("source"));
        return "GET 你的名字是 " + name;
    }

    @PostMapping("/para")
    public String getNameByPost(@RequestParam("name") String name) {
        return "POST 你的名字是 " + name + " by RequestParam";
    }

    @PostMapping("/body")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {

        /**
         * 以下内容在网关中实现了
         */
        // String accessKey = request.getHeader("accessKey");
        // String nonce = request.getHeader("nonce");
        // String timestamp = request.getHeader("timestamp");
        // String sign = request.getHeader("sign");
        // String body = request.getHeader("body");
        // // 不能直接获取秘钥
        // // String secretKey = request.getHeader("secretKey");
        //
        // // 2.校验权限,这里模拟一下,直接判断 accessKey 是否为"agony"
        // //  实际应该查询数据库验证权限
        // if (!accessKey.equals("agony")) {
        //     return "accessKey error";
        // }
        //
        // // 3.校验一下随机数,因为时间有限,就不带大家再到后端去存储了,后端存储用hashmap或redis都可以
        // // 校验随机数,模拟一下,直接判断nonce是否大于10000
        // if (Long.parseLong(nonce) > 10000) {
        //     return "nonce error";
        // }
        //
        // // todo 4.校验时间和当前时间不能超过5分钟
        //
        // // todo 5.校验时间戳与当前时间的差距,交给大家自己实现
        // // if (timestamp) {}
        //
        // String genSign = SignUtils.genSign(body, "abcd1234");
        // if (!sign.equals(genSign)) {
        //     return "sign error";
        // }

        return "POST 用户名字是 " + user.getUsername() + " by RequestBody";
    }

    // @Resource
    // private ApiClientConfig apiClientConfig;

    @Resource
    private ApiClient apiClient;

    @GetMapping("/test")
    public String test(@RequestParam("name") String name) {

        // ApiClient apiClient = apiClientConfig.apiClient();
        User user = new User();
        user.setUsername(name);
        return apiClient.getUserNameByPost(user);
    }

}
