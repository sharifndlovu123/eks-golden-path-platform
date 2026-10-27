package com.example.greeter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    // Set by the platform per tenant namespace (env var TENANT_NAME).
    @Value("${tenant.name}")
    private String tenant;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "world") String name) {
        return "Hello " + name + " from " + tenant;
    }
}
