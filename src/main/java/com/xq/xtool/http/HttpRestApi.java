package com.xq.xtool.http;

import com.xq.xtool.http.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@HttpApiHost("https://baidu.com")
public interface HttpRestApi {

    @HttpApiPath(value = "/v5/web/apps/{id}", method = HttpMethod.POST)
    @HttpApiHeader(key = "", value = "")
    public ResponseEntity<String> addApp(
            @PathVal("id") String id,
            @Param Map<String, String> param,
            @Data Object data,
            @Header("X-Auth-Type") String token
    );
}
