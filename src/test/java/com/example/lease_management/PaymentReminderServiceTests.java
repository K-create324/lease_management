package com.example.lease_management;


import com.example.lease_management.email.EmailService;
import com.example.lease_management.email.PaymentReminderService;
import com.example.lease_management.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaymentReminderServiceTests {

    @Mock
    ContractRepository contractRepository;


    @Mock
    private EmailService emailService;


    @Test
    public void test1(){

        Contract contract= new Contract();
        LocalDate instalmentDate = LocalDate.now().plusDays(3);

        contract.setInstalmentDate(instalmentDate);
//data raty a nie przypomnienia


        Client client= new Client();
   client.setEmail("a@wp.pl");
   contract.setClient(client);
        
        List<Contract> contracts= new ArrayList<>();
        contracts.add(contract);
        
      Mockito.when(contractRepository.findAll()).thenReturn(contracts);
PaymentReminderService paymentReminderService= new PaymentReminderService(contractRepository,emailService);

paymentReminderService.sendEmail();


// Weryfikujemy, czy metoda sendEmail z emailService została wywołana z odpowiednimi argumentami z klasy MOCKITO
        verify(emailService, times(1)).sendEmail(
                "a@wp.pl",
                "Przypomnienie o racie za 3 dni",
                "Pamiętaj o zapłacie raty: " + contract.getInstalmentDate());


    }




}
