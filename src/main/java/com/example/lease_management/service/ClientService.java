package com.example.lease_management.service;

import com.example.lease_management.Client;
import com.example.lease_management.repository.ClientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Iterable<Client> getAllClients(){
        Iterable<Client> allClients = clientRepository.findAll();
        return allClients;
    }
    public Optional<Client> getOneClient(Integer id){
        Optional<Client> byId = clientRepository.findById(id);
        return byId;

    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client editClient(Integer id, Client updatedClient) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Nie moge znależć klienta w bazie"));
        if (updatedClient.getLogin() != null && !updatedClient.getLogin().isEmpty()) client.setLogin(updatedClient.getLogin());
        if (updatedClient.getName() != null && !updatedClient.getName().isEmpty()) client.setName(updatedClient.getName());
        if (updatedClient.getSurname() != null && !updatedClient.getSurname().isEmpty()) client.setSurname(updatedClient.getSurname());
        if (updatedClient.getEmail() != null && !updatedClient.getEmail().isEmpty()) client.setEmail(updatedClient.getEmail());
        if (updatedClient.getPhoneNumber() != null && !updatedClient.getPhoneNumber().isEmpty()) client.setPhoneNumber(updatedClient.getPhoneNumber());

        return clientRepository.save(client);

    }
    public Client editAllClient(Integer id, Client updatedClient) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Nie moge znależć klienta w bazie"));
        if (updatedClient.getLogin() != null && !updatedClient.getLogin().isEmpty()) client.setLogin(updatedClient.getLogin());
        client.setName(updatedClient.getName());
        client.setSurname(updatedClient.getSurname());
       client.setEmail(updatedClient.getEmail());
        client.setPhoneNumber(updatedClient.getPhoneNumber());

        return clientRepository.save(client);

    }

    public void deleteClient(Integer id) {

        try {
            if (!clientRepository.existsById(id)) {
                throw new RuntimeException("Nie ma takiego klienta w bazie");
            }
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Nie można usunąć klienta powiązanego z aktywnymi umowam");
        }
        clientRepository.deleteById(id);
    }
}
