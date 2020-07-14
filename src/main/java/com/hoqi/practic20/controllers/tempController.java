package com.hoqi.practic20.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.hoqi.practic20.resources.temp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tempController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public temp greeting(@RequestParam(value = "name",defaultValue = "World")
                                     String name){
        return new temp(counter.incrementAndGet(),String.format(template,name));
    }


}
