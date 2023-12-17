package com.eos.ors.inventory;

import java.util.List;

import com.eos.ors.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eos.ors.exception.InventoryNotFoundException;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value = AppConstant.BASE_CONTEXT)
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	
	@PostMapping
	@Operation(summary = "Api to create Inventory")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.createInventory(inventory);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary ="Update Inventory")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
        if (updatedInventory != null) {
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary ="Get Inventory by ID")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory != null) {
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        }
        throw new InventoryNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }

    @GetMapping
    @Operation(summary ="Get All Inventory")
    public ResponseEntity<List<Inventory>> getAllInventorys() {
        List<Inventory> inventories = inventoryService.getAllInventory();
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
        }
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary ="Delete Inventory by Id")
    public void deleteInventoryById(@PathVariable Long id) {
        inventoryService.deleteInventoryById(id);
    }
    

}
