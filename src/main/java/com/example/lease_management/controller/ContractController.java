package com.example.lease_management.controller;

import com.example.lease_management.Contract;
import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.lease_management.Contract;

import java.util.Optional;

@RestController
public class ContractController {

    
    private final ContractService contractService ;

    public ContractController(ContractService contractService) {
      
        this.contractService = contractService;
    }

    @GetMapping ("contract")
    public ResponseEntity <Iterable<Contract>> getAllContract(){
        Iterable<Contract> allContracts = contractService.getAllContracts();
        return ResponseEntity.ok(allContracts);

    }
    @GetMapping("contract/{id}")
    public ResponseEntity <Optional<Contract>> getOneContract(@PathVariable Integer id){
        Optional<Contract> oneContract = contractService.getOneContract(id);
        return  ResponseEntity.ok(oneContract);
    }
    @PostMapping("contract")
            public ResponseEntity <Contract> addContract( @RequestBody Contract newContract){
        Contract savedContract = contractService.saveContract(newContract);
        return ResponseEntity.ok(savedContract);
    }
    @DeleteMapping("contract/{id}")
    public  ResponseEntity <Contract> deleteContract(@PathVariable Integer id) {
        Optional<Contract> oneContract = contractService.getOneContract(id);
        if (oneContract.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("contract/{id}")
    public ResponseEntity <Contract> editContract(@PathVariable Integer id, @RequestBody Contract updatedContract){
        Contract savedContract2 = contractService.editContract(id, updatedContract);
        return ResponseEntity.ok(savedContract2);
    }
}
