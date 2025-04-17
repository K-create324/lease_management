package com.example.lease_management.view;

import com.example.lease_management.Client;
import com.example.lease_management.service.ClientService;
import com.example.lease_management.Contract;
import com.example.lease_management.repository.ContractRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;

import java.util.ArrayList;

@Route("main-view")
public class MainView extends VerticalLayout {


    private  final ContractRepository contractRepository;
    private final ClientService clientService;


    public MainView( ContractRepository contractRepository, ClientService clientService) {
        this.contractRepository = contractRepository;
        this.clientService = clientService;


        H1 headher= new H1("PANEL GŁÓWNY");
        Button listaKlientówButton = new Button("Lista klietów -> rozwiń", event ->
        {showClientList();
        });
        Button listaUmówButton = new Button("Lista umów->rozwiń", event ->
        {shotContractList();
        });


        add(headher,listaKlientówButton, listaUmówButton);
    }

private void showClientList(){
    Iterable<Client> allClients = clientService.getAllClients();
    ArrayList<Client> allClientList = new ArrayList<>();
    for (Client client:allClients){
        allClientList.add(client);
    }
    Grid<Client> clientGrid = new Grid<>(Client.class);
    clientGrid.removeAllColumns();
    clientGrid.addColumn(client -> client.getId()).setHeader("ID");
    clientGrid.addColumn(client -> client.getLogin()).setHeader("Login klienta");
    clientGrid.addColumn(client -> client.getName()).setHeader("Imię");
    clientGrid.addColumn(client -> client.getSurname()).setHeader("Nazwisko");
    clientGrid.addColumn(client -> client.getEmail()).setHeader("adres email");
    clientGrid.addColumn(client -> client.getPhoneNumber()).setHeader("numer telefonu");

    ListDataProvider<Client> dataProvider = new ListDataProvider<>(allClientList);
    clientGrid.setDataProvider(dataProvider);
    add(clientGrid);
}
private void shotContractList(){
    Iterable<Contract> allContracts = contractRepository.findAll();
    ArrayList<Contract> contractsArray = new ArrayList<>();
    for (Contract contract: allContracts){
        contractsArray.add(contract);
    }
    Grid<Contract> contractGrid = new Grid<>(Contract.class);
    contractGrid.removeAllColumns();
    contractGrid.addColumn(contract -> contract.getId()).setHeader("Id umowy");
    contractGrid.addColumn(contract -> contract.getClient().getId()).setHeader("ID Klienta");
    contractGrid.addColumn(contract->contract.getContractNumber()).setHeader("numer umowy");
    contractGrid.addColumn(contract->contract.getStartDate()).setHeader("data rozpoczęcia umowy");
    contractGrid.addColumn(contract->contract.getEndDate()).setHeader("data zakończenia umowy");
    contractGrid.addColumn(contract->contract.getAmount()).setHeader("wartość umowy");
    contractGrid.addColumn(contract->contract.getInstalmentAmount()).setHeader("wysokość raty");
    contractGrid.addColumn(contract->contract.getInstalmentDate()).setHeader("data wymagalności 1 raty");
    ListDataProvider<Contract> contractListDataProvider = new ListDataProvider<>(contractsArray);
    contractGrid.setDataProvider(contractListDataProvider);
    add(contractGrid);
}

}
