package com.school.matmassig.notificationservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/target-service")
public class TargetController {

    @PostMapping("/endpoint")
    public String handleMessage(@RequestBody String message) {
        System.out.println("Message received at target service: " + message);
        return "Message processed successfully";
    }
}