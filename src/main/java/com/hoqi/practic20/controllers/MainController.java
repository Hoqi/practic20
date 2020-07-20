package com.hoqi.practic20.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/account")
    public String account(){
        return "cart.html";
    }
}
