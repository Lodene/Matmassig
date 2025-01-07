package com.school.matmassig.inventoryservice.service;

import com.school.matmassig.inventoryservice.model.InventoryItem;
import com.school.matmassig.inventoryservice.repository.InventoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryItemRepository repository;

    public InventoryService(InventoryItemRepository repository) {
        this.repository = repository;
    }

    public InventoryItem addItem(InventoryItem item) {
        return repository.save(item);
    }

    public InventoryItem updateItem(Long id, InventoryItem updatedItem) {
        return repository.findById(id).map(existingItem -> {
            existingItem.setIngredientId(updatedItem.getIngredientId());
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setUserId(updatedItem.getUserId());
            return repository.save(existingItem);
        }).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    public List<InventoryItem> getItemsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
