package org.clever.graaljs.fast.api.demo;

import org.clever.graaljs.core.GraalConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/15 23:01 <br/>
 */
@SpringBootApplication
public class FastApiDemo {
    public static void main(String[] args) {
        System.setProperty(GraalConstant.ENGINE_EXECUTED_LIMIT, "5000");
        SpringApplication.run(FastApiDemo.class, args);
    }
}
