package rz.cloud.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping
    public Object home() {
        return "OK";
    }

    @GetMapping("/demo")
    public Object demo() {
        return "DEMO";
    }

}
