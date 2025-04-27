package com.example.lease_management.view;

import com.example.lease_management.Client;
import com.example.lease_management.service.ClientService;
import com.example.lease_management.Contract;
import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.service.ContractService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;

@Route("/main_view")
@AnonymousAllowed
public class MainView extends VerticalLayout {



    private final ClientService clientService;
    private final ContractService contractService;


    public MainView( ClientService clientService, ContractService contractService) {

        this.clientService = clientService;
        this.contractService = contractService;


        H1 headher= new H1("PANEL GŁÓWNY");
        Header logOut= new Header();
        Button listaKlientówButton = new Button("Lista klietów -> rozwiń", event ->
        {showClientList();
        });
        Button listaUmówButton = new Button("Lista umów->rozwiń", event ->
        {shotContractList();
        });


        add(headher,listaKlientówButton, listaUmówButton, logOut);
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
    clientGrid.addComponentColumn(c-> {
        if(c.getPdfFile()!=null && c.getPdfFile().length>0){
           Anchor link=  new Anchor("/pdf/client/" +c.getId(), "Zobacz PDF");
           link.setTarget("_blank");
           return  link;
        } else {
            return new Span("brak pdf");
        }}).setHeader("DOKUMENTY REJESTROWE KLIENTA PDF");

    ListDataProvider<Client> dataProvider = new ListDataProvider<>(allClientList);
    clientGrid.setDataProvider(dataProvider);
    add(clientGrid);
}
private void shotContractList(){
    Iterable<Contract> allContracts = contractService.getAllContracts();
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
//PONIZEJ DO PODGLADU WGRANEJ UMOWY
contractGrid.addComponentColumn(c-> {
   if( c.getPdfFile() !=null && c.getPdfFile().length>0){
       Anchor link= new Anchor("/pdf/contract/"+ c.getId(), "Zobacz PDF"); //link do kontrolera
       link.setTarget("_blank"); // otwórz w nowej karcie
       return link;
   } else{
       return new Span("brak pdf");
   }
}).setHeader("UMOWA PDF");

    ListDataProvider<Contract> contractListDataProvider = new ListDataProvider<>(contractsArray);
    contractGrid.setDataProvider(contractListDataProvider);
    add(contractGrid);
}

}
