package com.xq.xtool;

import com.xq.xtool.http.annotation.HttpApiScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@HttpApiScan("com.xq.xtool.http")
public class XtoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(XtoolApplication.class, args);
    }

}
