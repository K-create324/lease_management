package com.example.lease_management.service;

import com.example.lease_management.repository.ClientRepository;
import com.example.lease_management.Contract;
import com.example.lease_management.repository.ContractRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;

    public ContractService(ContractRepository contractRepository, ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    public Contract saveContract(Contract contract) {
//        if(contract.getAmount()==null || contract.getInstalmentAmount()==null){
//            throw new IllegalArgumentException("Kwoty nie moga byc puste");
//        }.is
//        if(contract.getClient()!=null && contract.getClient().getId()!=0){
//            Client client = clientRepository.findById(contract.getClient().getId())
//                    .orElseThrow(()-> new IllegalArgumentException("Klient nie został znaleziony"));
//            contract.setClient(client);
//        }

        return contractRepository.save(contract);
    }

    public Iterable<Contract> getAllContracts() {
        Iterable<Contract> allContract = contractRepository.findAll();
        return allContract;
    }

    public Optional<Contract> getOneContract(Integer id) {
        Optional<Contract> byId = contractRepository.findById(id);
        return byId;
    }

    public Contract editContract(Integer id, Contract updatedContract) {
        Contract contract = contractRepository.findById(id).orElseThrow(() -> new RuntimeException("Nie ma umowy pod podanym id"));
        if (updatedContract.getContractNumber() != null && !updatedContract.getContractNumber().isEmpty())
            contract.setContractNumber(updatedContract.getContractNumber());
        if (updatedContract.getStartDate() != null) contract.setStartDate(updatedContract.getStartDate());
        if (updatedContract.getEndDate() != null) contract.setEndDate(updatedContract.getEndDate());
        if (updatedContract.getAmount() != null) contract.setAmount(updatedContract.getAmount());
        if (updatedContract.getInstalmentAmount() != null)
            contract.setInstalmentAmount(updatedContract.getInstalmentAmount());
        if (updatedContract.getInstalmentDate() != null)
            contract.setInstalmentDate(updatedContract.getInstalmentDate());
        if(updatedContract.getPdfFile() !=null)
            contract.setPdfFile(updatedContract.getPdfFile());
        return contractRepository.save(contract);
    }



    public void deleteContract(Integer id) {
            if (!contractRepository.existsById(id)) {
                throw new RuntimeException("Pod podanym adresem id nie ma umowy");
            }
        contractRepository.deleteById(id);
    }

}
