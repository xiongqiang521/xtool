package com.xq.xtool.http;

import com.xq.xtool.http.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@HttpApiHost("https://baidu.com")
public interface HttpRestApi {

    @HttpApiPath(value = "/v5/web/apps/{id}", method = RequestMethod.POST)
    @HttpApiHeader(value = "")
    public ResponseEntity<String> addApp(
            @Path("id") String id,
            @Param Map<String, String> param,
            @Data Object data,
            Header... header);
}
