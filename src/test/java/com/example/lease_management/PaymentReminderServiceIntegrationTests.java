package com.example.lease_management;

import com.example.lease_management.email.EmailService;
import com.example.lease_management.email.PaymentReminderService;
import com.example.lease_management.repository.ClientRepository;
import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.view.EmailSender;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // używa prawdziwej bazy
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class PaymentReminderServiceIntegrationTests {

    @Autowired
    private ContractRepository contractRepository;


    @Autowired
    private ClientRepository clientRepository;

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private PaymentReminderService paymentReminderService;

@Test
    public void test1integration() {
    //GIVEN
        Client client = new Client();

        client.setEmail("a@wp.pl");

        clientRepository.save(client);

        Contract contract = new Contract();
        contract.setClient(client);

        Item item= new Item();
    List <Item> items= new ArrayList<>();
    items.add(item);
        contract.setItems(items);

        contract.setInstalmentDate(LocalDate.now().plusDays(3));

        contractRepository.save(contract);
//WHEN
       paymentReminderService.sendEmail();
    // THEN: brak wyjątku = pozytywnie, możesz też mockować wysyłkę i sprawdzać wywołanie
    // Tu mógłbyś podpiąć np. testową implementację EmailService i sprawdzić, czy metoda została wywołana.

verify(emailService,times(1)).sendEmail(
        "a@wp.pl",
        "Przypomnienie o racie za 3 dni",
        "Pamiętaj o zapłacie raty: " + LocalDate.now().plusDays(3));





    }

}