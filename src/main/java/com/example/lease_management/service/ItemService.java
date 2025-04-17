package com.example.lease_management.service;

import com.example.lease_management.Item;
import com.example.lease_management.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public Iterable<Item>getAllItems(){
        Iterable<Item> allItems = itemRepository.findAll();
        return allItems;
        
    }
    public Item saveItem(Item item){
        return itemRepository.save(item);

    }
    public Item editItem(Integer id, Item modifiedItem){
        Item item= itemRepository.findById(id).orElseThrow(()-> new RuntimeException("Nie mam w bazie przedmiotu o podanym ID"));
if(modifiedItem.getTypeOfItem()!=null && !modifiedItem.getTypeOfItem().isEmpty()) item.setTypeOfItem(modifiedItem.getTypeOfItem());
if(modifiedItem.getMark()!=null && !modifiedItem.getMark().isEmpty()) item.setMark(modifiedItem.getMark());
if(modifiedItem.getModel()!=null && !modifiedItem.getModel().isEmpty()) item.setModel(modifiedItem.getModel());
if(modifiedItem.getProductionYear()!=0 ) item.setProductionYear(modifiedItem.getProductionYear());
return itemRepository.save(item);
    }

}
