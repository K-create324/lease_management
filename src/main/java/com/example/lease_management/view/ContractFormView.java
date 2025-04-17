package com.example.lease_management.view;


import com.example.lease_management.Client;
import com.example.lease_management.repository.ClientRepository;
import com.example.lease_management.repository.ContractRepository;
import com.example.lease_management.service.ContractService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import com.example.lease_management.Contract;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@SpringComponent
@UIScope
@Route("contract_panel")
public class ContractFormView extends VerticalLayout {
    private final ContractService contractService;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;

    private TextField contractNumber= new TextField("numer umowy");
    private DatePicker startDate= new DatePicker("początek umowy");
    private DatePicker endDate= new DatePicker("koniec umowy");
    private NumberField amount= new NumberField("wartość transakcji");
    private NumberField instrallmentAmount= new NumberField("wysokość raty ");
    private DatePicker instrallmentDate= new DatePicker("data wymagalności 1 raty");
    private TextField contractNumber1= new TextField("numer umowy");
    private DatePicker startDate1= new DatePicker("początek umowy");
    private DatePicker endDate1= new DatePicker("koniec umowy");
    private NumberField amount1= new NumberField("wartość transakcji");
    private NumberField instrallmentAmount1= new NumberField("wysokość raty ");
    private DatePicker instrallmentDate1= new DatePicker("data wymagalności 1 raty");
//    private IntegerField clientId= new IntegerField("client Id");
 private Button saveButton= new Button("Zapisz");
    private Button editButton= new Button("Zapisz edycje");
    private Button deleteButton= new Button("Usuń");

private ComboBox<Client> comboBoxClient= new ComboBox<>("Klient");
    private ComboBox<Client> comboBoxClientEdit= new ComboBox<>("Klient");
    private ComboBox<Contract> comboBoxContractEdit= new ComboBox<>("Umowy wybranego klienta");
private Binder<Contract> binder= new Binder<>(Contract.class);//??
    private TextField deletedId= new TextField("Podaj nr ID umowy do usunięcia");



    public ContractFormView(ContractService contractService, ClientRepository clientRepository, ContractRepository contractRepository) {
        this.contractService = contractService;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;


//COMBO BOX

comboBoxClient.setItems((Collection<Client>) clientRepository.findAll());
//etykieta
comboBoxClient.setItemLabelGenerator(client -> "Klient: " + client.getId() + ". " +client.getName() + " " + client.getSurname());


comboBoxClientEdit.setItems((Collection<Client>) clientRepository.findAll());
//etykieta
comboBoxClientEdit.setItemLabelGenerator(client-> "Klient: " + client.getId() + ". " +client.getName() + " " + client.getSurname());


//filtrowanie umów po wybraniu klienta
comboBoxClientEdit.addValueChangeListener(event->{

    Client selectedClient= event.getValue();
    if(selectedClient!=null){
        comboBoxContractEdit.setItems(contractRepository.findByclient(selectedClient));
    } else{
        comboBoxContractEdit.clear();
        comboBoxContractEdit.setItems();
    }
});
//etykieta:
comboBoxContractEdit.setItemLabelGenerator(contract-> "Umowa nr: " + contract.getContractNumber());

//automatyczne uzupelnianie danymi umowy po wybraniu umowy
        comboBoxContractEdit.addValueChangeListener(event->{
           Contract selectedContract =event.getValue();
if(selectedContract!=null){
    contractNumber1.setValue(selectedContract.getContractNumber());
    startDate1.setValue(selectedContract.getStartDate());
    endDate1.setValue(selectedContract.getEndDate());
    amount1.setValue(selectedContract.getAmount().doubleValue());
    instrallmentAmount1.setValue(selectedContract.getInstalmentAmount().doubleValue());
    instrallmentDate1.setValue(selectedContract.getInstalmentDate());
}
        });

        H1 headher= new H1("PANEL UMOWY");
        H3 headher1= new H3("Dodaj umowę");
        FormLayout form=new FormLayout();
        form.add( comboBoxClient, contractNumber,startDate,endDate,amount,instrallmentAmount,instrallmentDate,saveButton);
        saveButton.addClickListener(e-> saveContractPrivate());

        H3 headher2= new H3("Edytuj umowę");
       FormLayout form1=  new FormLayout();
       form1.add(comboBoxClientEdit, comboBoxContractEdit,startDate1,endDate1,amount1,instrallmentAmount1,instrallmentDate1,editButton);
       editButton.addClickListener((e->editContractPrivate()));

        H3 headher3= new H3("Usuń umowę");
       FormLayout form2= new FormLayout();
       form2.add(deletedId,deleteButton);
       deleteButton.addClickListener(event->deleteContract());

        add(headher, headher1,form, headher2,form1,headher3,form2);


}
    private void saveContractPrivate(){
        if(contractNumber.isEmpty()
                ||startDate.isEmpty()
                ||endDate.isEmpty()
                ||amount.isEmpty()
                ||instrallmentAmount.isEmpty()
                ||instrallmentDate.isEmpty()){
            Notification.show("Proszę uzupełnić wszystkie pola");
            return;
        }
       Contract contract= new Contract();
       contract.setContractNumber(contractNumber.getValue());
       contract.setStartDate(startDate.getValue());
       contract.setEndDate(endDate.getValue());
       contract.setAmount(BigDecimal.valueOf(amount.getValue()));
       contract.setInstalmentAmount (BigDecimal.valueOf(instrallmentAmount.getValue()));
       contract.setInstalmentDate(instrallmentDate.getValue());

        Client selectedClient= comboBoxClient.getValue();
if(selectedClient!=null){
    contract.setClient(selectedClient);
} else {
    Notification.show("Proszę wybrać klienta a jeżeli to nowy klient to należy najpierw wprowadzić go do bazy");
}
contractService.saveContract(contract);
Notification.show("Umowa zapisana");
    }


    public void editContractPrivate(){
        if(comboBoxContractEdit.isEmpty() ||contractNumber1.isEmpty() || startDate1.isEmpty()|| endDate1.isEmpty()|| amount1.isEmpty()|| instrallmentAmount1.isEmpty()|| instrallmentDate1.isEmpty()){
            Notification.show("Proszę o uzupełnienie wszystkich pól");
            return;//zatrzymuje dalsze wykonanie metody
        }
        Contract selected= comboBoxContractEdit.getValue();
        Integer id = selected.getId();
        Contract editContract= new Contract();
        editContract.setContractNumber(contractNumber1.getValue());
        editContract.setStartDate(startDate1.getValue());
        editContract.setEndDate(endDate1.getValue());
        editContract.setAmount(BigDecimal.valueOf(amount1.getValue()));
        editContract.setInstalmentAmount(BigDecimal.valueOf(instrallmentAmount1.getValue()));
        editContract.setInstalmentDate(instrallmentDate1.getValue());

        contractService.editContract(id, editContract);
        Notification.show("Edycja pomySlna");
    }
    public void deleteContract(){
        Integer idContract= Integer.parseInt(deletedId.getValue());
        contractService.deleteContract(idContract);
        Notification.show("Umowa o id: " + idContract + " została usunięta");
    }
}
