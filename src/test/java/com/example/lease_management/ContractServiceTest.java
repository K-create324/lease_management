package com.example.lease_management;

import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.service.ContractService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @Mock
    ContractRepository contractRepository;

    @InjectMocks
    ContractService contractService;

@Test
    public void save(){
        Contract contract = new Contract();
        contract.setInstalmentDate(LocalDate.of(2025,11, 1));
        contract.setContractNumber("123");
        contract.setInstalmentAmount(new BigDecimal(1000000));
        Mockito.when(contractRepository.save(contract)).thenReturn(contract);
        Contract contractSaved = contractService.saveContract(contract);
        verify(contractRepository).save(contract);
        assertNotNull(contractSaved, "nie ma nulla");
        assertEquals("123", contractSaved.getContractNumber());
    assertEquals(new BigDecimal(1000000), contractSaved.getInstalmentAmount());
    assertEquals(LocalDate.of(2025,11, 1), contractSaved.getInstalmentDate());

    }
    @Test
    public void delete(){

    Mockito.when(contractRepository.existsById(1)).thenReturn(true);
    contractService.deleteContract(1);
    verify(contractRepository).deleteById(1);

    }
    @Test
    public void getOneContract(){
    Contract contract = new Contract();
    contract.setContractNumber("12");
    Mockito.when(contractRepository.findById(1)).thenReturn(Optional.of(contract));
        Optional<Contract> oneContract = contractService.getOneContract(1);
        verify(contractRepository).findById(1);
        assertTrue(oneContract.isPresent(), "Umowa znaleziona");
        assertEquals("12", oneContract.get().getContractNumber());


    }
@Test
    public void getAllContract(){
    Contract contract= new Contract();
    Contract contract1 = new Contract();
    List<Contract> contracts= new ArrayList<>(List.of(contract, contract1));
    Mockito.when(contractRepository.findAll()).thenReturn(contracts);
    Iterable<Contract> allContracts = contractService.getAllContracts();
    List<Contract> contractsReturned= new ArrayList<>((Collection) allContracts);
    verify(contractRepository).findAll();
    assertEquals(2, contractsReturned.size());

}

//    public Contract editContract(Integer id, Contract updatedContract) {
//        Contract contract = contractRepository.findById(id).orElseThrow(() -> new RuntimeException("Nie ma umowy pod podanym id"));
//        if (updatedContract.getContractNumber() != null && !updatedContract.getContractNumber().isEmpty())
//            contract.setContractNumber(updatedContract.getContractNumber());
//        if (updatedContract.getStartDate() != null) contract.setStartDate(updatedContract.getStartDate());
//        if (updatedContract.getEndDate() != null) contract.setEndDate(updatedContract.getEndDate());
//        if (updatedContract.getAmount() != null) contract.setAmount(updatedContract.getAmount());
//        if (updatedContract.getInstalmentAmount() != null)
//            contract.setInstalmentAmount(updatedContract.getInstalmentAmount());
//        if (updatedContract.getInstalmentDate() != null)
//            contract.setInstalmentDate(updatedContract.getInstalmentDate());
//        if(updatedContract.getPdfFile() !=null)
//            contract.setPdfFile(updatedContract.getPdfFile());
//        return contractRepository.save(contract);
//    }

    @Test
    public void editContract(){
Contract contract= new Contract();
        contract.setContractNumber("1");
        contract.setAmount(new BigDecimal(100000));
        contract.setInstalmentAmount(new BigDecimal((1000)));
        contract.setInstalmentDate(LocalDate.of(2025, 9,01 ));
        contract.setStartDate(LocalDate.of(2025, 9,01 ));
        contract.setEndDate(LocalDate.of(2025, 9,01 ));
        contract.setId(1);

        Mockito.when(contractRepository.findById(1)).thenReturn(Optional.of(contract));

        Contract updatedContract= new Contract();
        updatedContract.setContractNumber("2");
        updatedContract.setAmount(new BigDecimal(200000));
        updatedContract.setInstalmentAmount(new BigDecimal((2000)));
        updatedContract.setInstalmentDate(LocalDate.of(2024, 9,01 ));
        updatedContract.setStartDate(LocalDate.of(2024, 9,01 ));
        updatedContract.setEndDate(LocalDate.of(2024, 9,01 ));
        contractService.editContract(contract.getId(), updatedContract);

        verify(contractRepository).findById(1);
        verify(contractRepository).save(contract);
        assertEquals("2", contract.getContractNumber());
        assertEquals(new BigDecimal(200000), contract.getAmount());
        assertEquals(new BigDecimal(2000), contract.getInstalmentAmount());
        assertEquals (LocalDate.of(2024, 9,01 ), contract.getStartDate());
        assertEquals (LocalDate.of(2024, 9,01 ), contract.getEndDate());


    }

    @Test
    public void contractEditNotFound(){

    Mockito.when(contractRepository.findById(1)).thenReturn(Optional.empty());
        Contract updatedcontract=new Contract();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> contractService.editContract(1, updatedcontract));
assertEquals("Nie ma umowy pod podanym id", exception.getMessage());
        verify(contractRepository).findById(1);
        verify(contractRepository,Mockito.never()).save(Mockito.any());


    }

}
