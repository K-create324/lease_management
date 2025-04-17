package com.example.lease_management.repository;

import com.example.lease_management.Client;
import com.example.lease_management.Contract;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContractRepository extends CrudRepository<Contract,Integer> {
    List<Contract> findByclient(Client client);
}
