package com.example.lease_management.service;

import com.example.lease_management.Contract;
import com.example.lease_management.Item;
import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.repository.ItemRepository;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ContractRepository contractRepository;

    public ItemService(ItemRepository itemRepository, ContractRepository contractRepository) {
        this.itemRepository = itemRepository;
        this.contractRepository = contractRepository;
    }


    public Iterable<Item> getAllItems() {
        Iterable<Item> allItems = itemRepository.findAll();
        return allItems;
    }

    public Optional<Item> getOneItem(Integer id) {
        Optional<Item> byId = itemRepository.findById(id);
        return byId;
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);

    }
    @Transactional
    public Item findItemFromContract(Integer contractId){
        Contract contract3 = contractRepository.findWithItemsById(contractId);
        if(contract3== null){
            throw new RuntimeException("Nie ma takiego kontraktu o podanym ID");
        }
        List<Item> items = contract3.getItems();
        if(items!=null && !items.isEmpty()){
            return items.get(0);
        }else{
            throw  new RuntimeException("Brak przypisanych przedmiotów do tej umowy");
        }

    }
    public Item saveItemFromContract (Integer contractId, Item newItem){
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new RuntimeJsonMappingException("Nie ma takiego kontraktu o podanym ID"));
      newItem.setContract(contract);

        return  itemRepository.save(newItem);

    }
    @Transactional
    public Item editItemFromContract (Integer contractId, Item newItemData){
        Contract contract = contractRepository.findWithItemsById(contractId);
        List<Item> items = contract.getItems();
        if(items==null || items.isEmpty()){
            saveItemFromContract(contractId,newItemData);
            return itemRepository.save(newItemData);
//            throw  new RuntimeException("Brak przypisanego przedmiotu dp umowy");
        }

        Item editedItem = items.get(0);
        if (newItemData.getTypeOfItem() != null && !newItemData.getTypeOfItem().isEmpty())
            editedItem.setTypeOfItem(newItemData.getTypeOfItem());
        if (newItemData.getMark() != null && !newItemData.getMark().isEmpty()) editedItem.setMark(newItemData.getMark());
        if (newItemData.getModel() != null && !newItemData.getModel().isEmpty())
            editedItem.setModel(newItemData.getModel());
        if (newItemData.getProductionYear() != 0) editedItem.setProductionYear(newItemData.getProductionYear());
        return  itemRepository.save(editedItem);

    }

    public Item editItem(Integer id, Item modifiedItem) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Nie mam w bazie przedmiotu o podanym ID"));
        if (modifiedItem.getTypeOfItem() != null && !modifiedItem.getTypeOfItem().isEmpty())

            item.setTypeOfItem(modifiedItem.getTypeOfItem());
        if (modifiedItem.getMark() != null && !modifiedItem.getMark().isEmpty()) item.setMark(modifiedItem.getMark());
        if (modifiedItem.getModel() != null && !modifiedItem.getModel().isEmpty())
            item.setModel(modifiedItem.getModel());
        if (modifiedItem.getProductionYear() != 0) item.setProductionYear(modifiedItem.getProductionYear());
        return itemRepository.save(item);
    }

    public void deleteItem(Integer id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("W bazie danych nie ma przedmiotu o podanym Id");
        }
        itemRepository.deleteById(id);

    }
}
