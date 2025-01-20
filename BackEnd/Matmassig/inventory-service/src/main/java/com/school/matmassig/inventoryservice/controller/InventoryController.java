package com.school.matmassig.inventoryservice.controller;

import com.school.matmassig.inventoryservice.model.InventoryItem;
import com.school.matmassig.inventoryservice.service.InventoryProducer;
import com.school.matmassig.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;
    private final InventoryProducer producer;

    public InventoryController(InventoryService service, InventoryProducer producer) {
        this.service = service;
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<InventoryItem> addItem(@RequestBody InventoryItem item) {
        return ResponseEntity.ok(service.addItem(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Integer id, @RequestBody InventoryItem item) {
        return ResponseEntity.ok(service.updateItem(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InventoryItem>> getItemsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getItemsByUserId(userId));
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producer.sendMessage(message);
        return "Message envoyé: " + message;
    }
}
