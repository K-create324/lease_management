package com.example.lease_management.controller;

import com.example.lease_management.Client;
import com.example.lease_management.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController( ClientService clientService) {
        this.clientService = clientService;

    }
    @GetMapping("client")
    public ResponseEntity <Iterable<Client>> getAllClient (){
        Iterable<Client> allClients = clientService.getAllClients();
        return ResponseEntity.ok(allClients);
    }
    @GetMapping("client/{id}")
    public ResponseEntity <Client> getOneClient(@PathVariable Integer id){
        return clientService.getOneClient(id)
                .map(u->ResponseEntity.ok(u))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("client")
    public ResponseEntity <Client> addClient(@RequestBody Client newClient){
        Client savedClient = clientService.saveClient(newClient);
        return ResponseEntity.ok(savedClient);
    }

    @DeleteMapping("client/{id}")
    public ResponseEntity <Client> deleteClient(@PathVariable Integer id){
        Optional<Client> oneClient = clientService.getOneClient(id);
        if(oneClient.isEmpty()){
            ResponseEntity.notFound().build();
        }
       clientService.deleteClient(id);
       return  ResponseEntity.noContent().build();
    }

    @PatchMapping("client/{id}")
    public  ResponseEntity <Client> partiallyUpdateClient(@PathVariable Integer id, @RequestBody Client modifiedClient){
        Client saveModifiedclient = clientService.editClient(id, modifiedClient);
        return ResponseEntity.ok(saveModifiedclient);
    }
    @PutMapping("client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client updateClient){
        Client saveAllModifiedclient = clientService.editAllClient(id, updateClient);
        return ResponseEntity.ok(saveAllModifiedclient);
    }


}
