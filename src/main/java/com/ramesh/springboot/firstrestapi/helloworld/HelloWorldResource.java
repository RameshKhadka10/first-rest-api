package com.ramesh.springboot.firstrestapi.helloworld;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloWorldResource {

    @RequestMapping("/hello-world")
    //@ResponseBody
    public String helloWorld() {
        return "Hello World";
    }

    @RequestMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean");
    }

    @RequestMapping(value = "hello-world/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloWorldPathVariable(@PathVariable String name) {
        return "Hello World " + name;
    }
}
