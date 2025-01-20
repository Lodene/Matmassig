package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.Item;
import com.school.matmassig.orchestrator.model.DeleteItemRequest;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/inventory")
public class InventoryController {

    private final RabbitMQPublisherService publisherService;

    public InventoryController(RabbitMQPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createItem(@RequestBody Item item) {
        publisherService.publishMessage(EXCHANGE_NAME, "item.create", item);
        return ResponseEntity.ok("Item creation request sent to RabbitMQ");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody Item item) {
        publisherService.publishMessage(EXCHANGE_NAME, "item.update", item);
        return ResponseEntity.ok("Item update request sent to RabbitMQ");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody DeleteItemRequest deleteItemRequest) {
        publisherService.publishMessage(EXCHANGE_NAME, "item.delete", deleteItemRequest);
        return ResponseEntity.ok("Item delete request sent to RabbitMQ");
    }

    @GetMapping("/getbyuser/{userId}")
    public ResponseEntity<String> getItemsByUser(@PathVariable Integer userId) {
        publisherService.publishMessage(EXCHANGE_NAME, "item.getbyuser", userId);
        return ResponseEntity.ok("Get items by user request sent to RabbitMQ");
    }
}
