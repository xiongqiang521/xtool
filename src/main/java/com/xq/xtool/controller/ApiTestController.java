package com.xq.xtool.controller;

import com.xq.xtool.http.HttpRestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @Autowired
    private HttpRestApi httpRestApi;

    @RequestMapping("/api")
    public ResponseEntity<String> test(){
        return  httpRestApi.addApp("123", null, new Object(), "token");
    }
}
