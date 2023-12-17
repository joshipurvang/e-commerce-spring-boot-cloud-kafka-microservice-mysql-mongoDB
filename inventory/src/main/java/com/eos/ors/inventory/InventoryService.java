package com.eos.ors.inventory;



import java.util.List;
import java.util.Optional;

import com.eos.ors.exception.InventoryNotFoundException;
import com.eos.ors.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long id, Inventory updatedInventory) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isPresent()) {
        	updatedInventory.setId(id);
            return inventoryRepository.save(updatedInventory);
        }
        throw new InventoryNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public void deleteInventoryById(Long id) {
    	inventoryRepository.deleteById(id);
    }

}
