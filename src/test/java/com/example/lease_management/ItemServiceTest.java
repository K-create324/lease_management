package com.example.lease_management;

import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.repository.ItemRepository;
import com.example.lease_management.service.ItemService;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Mock
    ContractRepository contractRepository;

    @InjectMocks
    ItemService itemService;

    @Test
    public void getItem(){
        Item item = new Item();
        item.setId(1);
        item.setTypeOfItem("pojazd");

        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        Optional<Item> oneItem = itemService.getOneItem(item.getId());

        verify(itemRepository).findById(1);
        assertTrue(oneItem.isPresent(), "przedmiot znaleziony");
        assertEquals("pojazd", oneItem.get().getTypeOfItem());

    }

    @Test
    public void getAllItems(){
        Item item = new Item();
        Item item2 = new Item();
        List<Item> items = new ArrayList<>(List.of(item,item2));

        Mockito.when(itemRepository.findAll()).thenReturn(items);
        Iterable<Item> allItems = itemService.getAllItems();
        List<Item> listItem= new ArrayList<>((Collection) allItems);

        verify(itemRepository).findAll();
        assertEquals(2, listItem.size());

    }

    @Test
    public void save(){
        Item item= new Item();
        item.setTypeOfItem("maszyna");
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Item savedItem = itemService.saveItem(item);
        verify(itemRepository).save(item);
        assertNotNull(savedItem, "nie ma nulla");
        assertEquals("maszyna", savedItem.getTypeOfItem());

    }

    @Test
    public void delete(){

        Mockito.when(itemRepository.existsById(1)).thenReturn(true);
        itemService.deleteItem(1);
        verify(itemRepository).deleteById(1);
    }

    @Test
    public void edit(){
       Item item= new Item();
       item.setModel("Mazda");
       item.setMark("3");
       item.setId(1);

        Item editedItem= new Item();
        editedItem.setModel("BMW");
        editedItem.setMark("5");
       Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));
       Mockito.when(itemRepository.save(item)).thenReturn(item);


        Item editedSaved = itemService.editItem(item.getId(), editedItem);

        verify(itemRepository).findById(1);
        verify(itemRepository).save(item);
        assertEquals("BMW", editedSaved.getModel());
        assertEquals("5", editedSaved.getMark());

    }

    @Test
    public void editedItemNotFound(){

        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.empty());

        Item modifiedItem=new Item();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.editItem(1, modifiedItem));
    verify(itemRepository).findById(1);
    verify(itemRepository,Mockito.never()).save(Mockito.any());
    assertEquals("Nie mam w bazie przedmiotu o podanym ID", exception.getMessage());

    }

//    public Item editItemFromContract (Integer contractId, Item newItemData){
//        Contract contract = contractRepository.findWithItemsById(contractId);
//        List<Item> items = contract.getItems();
//        if(items==null || items.isEmpty()){
//            saveItemFromContract(contractId,newItemData);
//            return itemRepository.save(newItemData);

    @Test
    public void editItemFromContract(){
        Contract contract= new Contract();
        contract.setId(2);
        Item item = new Item();
        item.setMark("Mazda");
        List<Item> items= new ArrayList<>();
        items.add(item);
        contract.setItems(items);
        Item editedItem = new Item();
        editedItem.setMark("Bmw");
        Mockito.when(contractRepository.findWithItemsById(contract.getId())).thenReturn(contract);
        itemService.editItemFromContract(contract.getId(), editedItem );
        verify(contractRepository).findWithItemsById(2);
        assertEquals("Bmw",item.getMark());

    }


//    public Item saveItemFromContract (Integer contractId, Item newItem){
//        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new RuntimeJsonMappingException("Nie ma takiego kontraktu o podanym ID"));
//        newItem.setContract(contract);
//        return  itemRepository.save(newItem);
//    }
    @Test
    public void saveItemFromContract(){
        //Podzielony na trzy sekcje (Arrange-Act-Assert)
        Contract contract= new Contract();
        Mockito.when(contractRepository.findById(1)).thenReturn(Optional.of(contract));
        Item newItem= new Item();
        newItem.setContract(contract);
        Mockito.when(itemRepository.save(newItem)).thenReturn(newItem);

        //act
        Item savedItem = itemService.saveItemFromContract(1, newItem);

        verify(contractRepository).findById(1);
        verify(itemRepository).save(newItem);
//assert
        assertEquals(contract, savedItem.getContract());
    }
    @Test
    public void saveItemFromContractNotFound(){
        Item newItem= new Item();
        Contract contract= new Contract();
      contract.setId(1);
        Mockito.when(contractRepository.findById(contract.getId())).thenReturn(Optional.empty());
        RuntimeJsonMappingException runtimeJsonMappingException = assertThrows(RuntimeJsonMappingException.class, () -> itemService.saveItemFromContract(1, newItem));
verify(contractRepository).findById(1);
        assertEquals("Nie ma takiego kontraktu o podanym ID", runtimeJsonMappingException.getMessage());

    }
    //    @Transactional
//    public Item findItemFromContract(Integer contractId){
//        Contract contract3 = contractRepository.findWithItemsById(contractId);
//        if(contract3== null){
//            throw new RuntimeException("Nie ma takiego kontraktu o podanym ID");
//        }
//        List<Item> items = contract3.getItems();
//        if(items!=null && !items.isEmpty()){
//            return items.get(0);
//        }else{
//            throw  new RuntimeException("Brak przypisanych przedmiotów do tej umowy");
//        }
//    }
    @Test
    public void findItemFromContractPositive(){
        Contract contract=new Contract();
        Item item=new Item();
        List <Item> items=new ArrayList<>();
        items.add(item);
        contract.setItems(items);
        Integer contractId = 1;
        Mockito.when(contractRepository.findWithItemsById(contractId)).thenReturn(contract);
        Item itemFromContract = itemService.findItemFromContract(contractId);
        verify(contractRepository).findWithItemsById(contractId);
assertNotNull(itemFromContract);
    }

    @Test
    public void findItemFromContractNoContract(){
        Mockito.when(contractRepository.findWithItemsById(1)).thenReturn(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.findItemFromContract(1));
assertEquals("Nie ma takiego kontraktu o podanym ID", exception.getMessage());
verify(contractRepository).findWithItemsById(1);

    }

    @Test
    public void findItemFromContractNoItems(){
        Contract contract= new Contract();
        Mockito.when(contractRepository.findWithItemsById(1)).thenReturn(contract);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.findItemFromContract(1));
        assertEquals("Brak przypisanych przedmiotów do tej umowy",exception.getMessage());
    verify(contractRepository).findWithItemsById(1)  ;
    }
}
