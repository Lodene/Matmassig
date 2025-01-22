package com.school.matmassig.inventoryservice.repository;

import com.school.matmassig.inventoryservice.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    List<InventoryItem> findByUserId(Integer userId);

    List<InventoryItem> getItemsByUserId(Integer userId);

}
