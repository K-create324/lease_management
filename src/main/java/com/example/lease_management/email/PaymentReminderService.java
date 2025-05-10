package com.example.lease_management.email;

import com.example.lease_management.Contract;
import com.example.lease_management.repository.ContractRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentReminderService {

    private final ContractRepository contractRepository;
    private final EmailService emailService;


    public PaymentReminderService(ContractRepository contractRepository, EmailService emailService) {
        this.contractRepository = contractRepository;

        this.emailService = emailService;
    }
    @Scheduled(cron = "0 43 14 * * ?")
    public void sendEmail(){
        List<LocalDate> dateOfInstalment= new ArrayList<>();
        Iterable<Contract> allContract = contractRepository.findAll();

        for (Contract contract: allContract) {
            LocalDate instalmentDateMinus3 = contract.getInstalmentDate().minusDays(3);
            LocalDate instalmentDate = contract.getInstalmentDate();
            dateOfInstalment.add(instalmentDateMinus3);
            String email = contract.getClient().getEmail();
            if (LocalDate.now().isEqual(instalmentDateMinus3)) {
                emailService.sendEmail(email, "Przypomnienie o racie za 3 dni", "Pamiętaj o zapłacie raty: " + instalmentDate);
                System.out.println("wysłano email do: " + email);

            }
        }

    }
}
