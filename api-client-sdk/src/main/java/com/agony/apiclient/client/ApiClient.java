package com.agony.apiclient.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.agony.apiclient.model.User;
import com.agony.apiclient.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Agony
 * @create 2024/4/5 19:54
 */

public class ApiClient {

    private final String accessKey;
    private final String secretKey;

    private final String GATEWAY_HOST = "http://localhost:8090";

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        return HttpUtil.get(GATEWAY_HOST + "/api/name/get", hashMap);
    }


    public String getNameByPost(String name) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/name/para", hashMap);
    }


    private Map<String, String> getHeaders(String body) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);
        headers.put("nonce", RandomUtil.randomNumbers(4));
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("body", body);
        headers.put("sign", SignUtils.genSign(body, secretKey));
        return headers;
    }

    public String getUserNameByPost(User user) {

        String body = JSONUtil.toJsonStr(user);

        return HttpRequest.post(GATEWAY_HOST + "/api/name/body")
                .addHeaders(getHeaders(body))
                .body(body)
                .execute()
                .body();
    }
}
