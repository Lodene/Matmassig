package com.school.matmassig.notificationservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/target-service")
public class TargetController {

    @PostMapping("/endpoint")
    public String handleMessage(@RequestBody String message) {
        System.out.println("Message received at target service: " + message);
        return "Message processed successfully";
    }
}