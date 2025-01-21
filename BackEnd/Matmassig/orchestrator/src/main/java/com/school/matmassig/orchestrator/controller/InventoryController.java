package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.Item;
import com.school.matmassig.orchestrator.model.DeleteItemRequest;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import com.school.matmassig.orchestrator.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/inventory")
public class InventoryController {

    private final JwtUtils jwtUtils;
    private final RabbitMQPublisherService publisherService;

    public InventoryController(RabbitMQPublisherService publisherService, JwtUtils jwtUtils) {
        this.publisherService = publisherService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createItem(@RequestBody Item item, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        item.setEmail(email);
        item.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "item.create", item);
        return ResponseEntity.ok("Item creation request sent to RabbitMQ");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody Item item, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        item.setEmail(email);
        item.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "item.update", item);
        return ResponseEntity.ok("Item update request sent to RabbitMQ");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody DeleteItemRequest deleteItemRequest, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        deleteItemRequest.setEmail(email);
        deleteItemRequest.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "item.delete", deleteItemRequest);
        return ResponseEntity.ok("Item delete request sent to RabbitMQ");
    }

    @GetMapping("/getbyuser/{userId}")
    public ResponseEntity<String> getItemsByUser(@PathVariable Integer userId, @RequestHeader("Authorization") String authHeader){
        String email = extractEmailFromToken(authHeader);
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        publisherService.publishMessageWithAdditionalData(EXCHANGE_NAME, "item.getbyuser", payload, email);
        return ResponseEntity.ok("Get items by user request sent to RabbitMQ");
    }

    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getEmailFromToken(token);
    }

    private Integer extractUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getUserIdFromToken(token);
    }
}
