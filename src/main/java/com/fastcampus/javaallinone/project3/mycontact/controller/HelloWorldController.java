package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // => @ResponsiveBody + @Controller
public class HelloWorldController {

    @GetMapping(value = "/api/helloWorld")
    public String HelloWorld(){
        return "Hello World!";
    }
}
