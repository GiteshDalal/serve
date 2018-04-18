package com.giteshdalal.productservice.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class CounterController {

    private static AtomicLong count = new AtomicLong(0);

    @Value("${counter.prefixMessage: Hello Default}")
    private String prefixMessage;

    @GetMapping(path = "/count")
    public String getCount() {
        return prefixMessage + count.getAndIncrement();
    }
}

