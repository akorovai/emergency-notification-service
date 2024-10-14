package com.ad.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
public class OauthController {

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/secured")
    public String secured() {
        return "secured";
    }
}
