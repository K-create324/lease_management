package com.example.lease_management.controller;


import com.example.lease_management.Item;
import com.example.lease_management.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ItemController {

    public final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("items")
    public ResponseEntity<Iterable<Item>> getAllItems() {
        Iterable<Item> allItems = itemService.getAllItems();
        return ResponseEntity.ok(allItems);
    }

    @GetMapping("item/{id}")
    public ResponseEntity<Item> getOneItem(@PathVariable Integer id) {
        return itemService.getOneItem(id)
                .map(u -> ResponseEntity.ok(u))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("item")
    public ResponseEntity<Item> addItem(@RequestBody Item newItem) {
        Item savedItem = itemService.saveItem(newItem);
        return ResponseEntity.ok(savedItem);
    }

    @PostMapping("contract/{contractId}/item")
    public ResponseEntity <Item> addItemFromContract(@PathVariable Integer contractId, @RequestBody Item newItem){
        Item savedItem2 = itemService.saveItemFromContract(contractId, newItem);
        return ResponseEntity.ok(savedItem2);
    }

    @PatchMapping("item/{id}")
    public ResponseEntity<Item> editItem(@PathVariable Integer id, @RequestBody Item modifiedItem) {
        Item savedModyfiedItem = itemService.editItem(id, modifiedItem);
        return ResponseEntity.ok(savedModyfiedItem);
    }



    @DeleteMapping("item/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Integer id){
        Optional<Item> selectedItem = itemService.getOneItem(id);
        if (selectedItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();


    }

}





