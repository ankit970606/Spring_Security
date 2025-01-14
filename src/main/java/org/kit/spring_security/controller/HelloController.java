package org.kit.spring_security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(HttpServletRequest request) {
       // return "hello"+ request.getSession().getId();
        return "Hello World";
    }
    @GetMapping("about")
    public String about(HttpServletRequest request) {
        return "ankit"+ request.getSession().getId();
    }
}
