package com.school.matmassig.inventoryservice.service;

import com.school.matmassig.inventoryservice.model.InventoryItem;
import com.school.matmassig.inventoryservice.repository.InventoryItemRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class InventoryService {

    private final InventoryItemRepository repository;

    public InventoryService(InventoryItemRepository repository) {
        this.repository = repository;
    }

    public InventoryItem addItem(InventoryItem item) {
        System.out.println("Adding item: " + item.toString());
        InventoryItem itemFalse = new InventoryItem();
        itemFalse.setIngredientId(1);
        itemFalse.setQuantity(25);
        itemFalse.setUserId(1);
        itemFalse.setCreatedAt(LocalDateTime.now());
        itemFalse.setUpdatedAt(LocalDateTime.now());
        repository.save(itemFalse);
        return repository.save(item);
    }

    public InventoryItem updateItem(Integer id, InventoryItem updatedItem) {
        return repository.findById(id).map(existingItem -> {
            existingItem.setIngredientId(updatedItem.getIngredientId());
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setUserId(updatedItem.getUserId());
            return repository.save(existingItem);
        }).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
    }

    public void deleteItem(Integer id) {
        repository.deleteById(id);
    }

    public List<InventoryItem> getItemsByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }
}
