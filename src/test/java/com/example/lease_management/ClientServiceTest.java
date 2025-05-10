package com.example.lease_management;


import com.example.lease_management.repository.ClientRepository;
import com.example.lease_management.service.ClientService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
ClientService clientService;


    @Test
    public void save(){
        Client client=new Client();
        client.setName("Ania");
        client.setSurname("B");
        client.setPhoneNumber("123456789");
        client.setEmail("a@wp.pl");
        client.setLogin("ab");
        Mockito.when(clientRepository.save(client)).thenReturn(client);
        Client savedClient = clientService.saveClient(client);

        verify(clientRepository).save(client);
        assertNotNull(savedClient, "Nie ma nulla");
        assertEquals("Ania", savedClient.getName(),"Imie powinno się zgadzać");
        assertEquals("B", savedClient.getSurname(),"Nazwisko powinno się zgadzać");
        assertEquals("123456789", savedClient.getPhoneNumber(),"Telefon powinno się zgadzać");
        assertEquals("a@wp.pl", savedClient.getEmail(),"Mail powinno się zgadzać");
        assertEquals("ab", savedClient.getLogin(),"Login powinno się zgadzać");
    }

    @Test
    public void delete(){

Mockito.when(clientRepository.existsById(1)).thenReturn(true);
       clientService.deleteClient(1);
       verify(clientRepository).deleteById(1); //verify działa tylko na mockowych obiektach
    }

    @Test
    public void getAllClient(){
        Client client= new Client();
        Client client1= new Client();
        List<Client> listClient= new ArrayList<>(List.of(client,client1));
        Mockito.when(clientRepository.findAll()).thenReturn(listClient);
        Iterable<Client> result =  clientService.getAllClients();
        List<Client> resultList= new ArrayList<>((Collection) result);
        verify(clientRepository).findAll();
        assertEquals(2,resultList.size());
    }

    @Test
    public void getOneClient(){

        Client client=new Client();
        client.setName("Ania");
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Optional<Client> oneClient = clientService.getOneClient(1);
        verify(clientRepository).findById(1);
        assertTrue(oneClient.isPresent(),"klient jest w srodku" ); //bo test optionala
        assertEquals("Ania", oneClient.get().getName(), "Imię powinno się zgadzać");
    }

//    public Client editClient(Integer id, Client updatedClient) {
//
//        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Nie moge znależć klienta w bazie"));
//        if (updatedClient.getLogin() != null && !updatedClient.getLogin().isEmpty()) client.setLogin(updatedClient.getLogin());
//        if (updatedClient.getName() != null && !updatedClient.getName().isEmpty()) client.setName(updatedClient.getName());
//        if (updatedClient.getSurname() != null && !updatedClient.getSurname().isEmpty()) client.setSurname(updatedClient.getSurname());
//        if (updatedClient.getEmail() != null && !updatedClient.getEmail().isEmpty()) client.setEmail(updatedClient.getEmail());
//        if (updatedClient.getPhoneNumber() != null && !updatedClient.getPhoneNumber().isEmpty()) client.setPhoneNumber(updatedClient.getPhoneNumber());
//        if(updatedClient.getPdfFile()!=null) client.setPdfFile(updatedClient.getPdfFile());
//        return clientRepository.save(client);
    @Test
    public void editClient(){
        Client client=new Client();
        client.setName("Ania");
        client.setSurname("B");
        client.setPhoneNumber("123456789");
        client.setEmail("a@wp.pl");
        client.setLogin("ab");
        client.setId(1);

Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));

Client updatedClient= new Client();
updatedClient.setName("Zosia");
        updatedClient.setSurname("A");
        updatedClient.setPhoneNumber("6");
        updatedClient.setEmail("a@wp.pl");
        updatedClient.setLogin("WWWWW");

        clientService.editClient(client.getId(),updatedClient );

        verify(clientRepository).findById(1);
        verify(clientRepository).save(client);
        assertEquals("Zosia", client.getName(), "Imię powinno być zmienione" );
assertEquals("A", client.getSurname(),"Nazwisko powinno być zmienione" );
        assertEquals("6", client.getPhoneNumber(),"Telefon powinien być zmieniony" );
        assertEquals("a@wp.pl", client.getEmail(),"Email powinien być zmieniony" );
assertEquals("WWWWW", client.getLogin(), "Email powinien sie zgadzać");

        //skończyć??
    }
@Test
    public void clientEditNOTFOUND(){
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.empty());

        Client updatedClient= new Client();
        updatedClient.setName("Kasia");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.editClient(1, updatedClient));
assertEquals("Nie moge znależć klienta w bazie", exception.getMessage());
        verify(clientRepository).findById(1);
        verify(clientRepository,Mockito.never()).save(Mockito.any());
    }
}
