package com.example.lease_management.view;

import com.example.lease_management.Client;
import com.example.lease_management.service.ClientService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import org.springframework.dao.DataIntegrityViolationException;

@Route("client panel")
public class ClientFormView extends VerticalLayout {

   private final ClientService clientService;


    private TextField login =new TextField("login");
    private TextField name =new TextField("imię");
    private TextField surname =new TextField("nazwisko");
    private TextField email =new TextField("email");
    private TextField phoneNumber =new TextField("numer telefonu");
    private TextField loginEdit =new TextField("login");
    private TextField nameEdit =new TextField("imię");
    private TextField surnameEdit =new TextField("nazwisko");
    private TextField emailEdit =new TextField("email");
    private TextField phoneNumberEdit =new TextField("numer telefonu");
    private Button saveButton= new Button("Zapisz");
    private Button editButton= new Button("Zapisz edycje");
    private Button deleteButton= new Button("Usuń");
    private TextField editId = new TextField("Id klienta do edycji");
    private TextField deleteId = new TextField("Id klienta do usunięcia");

    public ClientFormView(ClientService clientService) {
        this.clientService = clientService;

        H1 headher= new H1("PANEL KLIENTA");
        H3 headher1= new H3("Dodaj klienta");
        FormLayout form = new FormLayout();
        form.add(login,name,surname,email,phoneNumber,saveButton);

        saveButton.addClickListener(e->saveClient());

        H3 headher2= new H3("Edytuj klienta");

        FormLayout form1 = new FormLayout();
        form1.add(editId,loginEdit,nameEdit,surnameEdit,emailEdit,phoneNumberEdit,editButton);
        editButton.addClickListener(e->editClient());

        H3 headher3= new H3("Usuń klienta");
        FormLayout form2 = new FormLayout();
        form2.add(deleteId,deleteButton);
        deleteButton.addClickListener(
                e->{ try{
                    deleteClient();
                }catch (DataIntegrityViolationException ex){
                    Notification.show("Nie można usunąć klienta bo ma aktywne umowy", 5000, Notification.Position.MIDDLE);
                }
                });

        add(headher,headher1,form, headher2, form1,headher3,form2);
    }
    private void saveClient(){
        Client client= new Client();
        client.setLogin(login.getValue());
        client.setName(name.getValue());
        client.setSurname(surname.getValue());
        client.setEmail(email.getValue());
        client.setPhoneNumber(phoneNumber.getValue());
        clientService.saveClient(client);
         Notification.show("Klient zapisany");
    }
    private void editClient(){
        Integer id= Integer.parseInt(editId.getValue());
        Client updatedClient= new Client();
        updatedClient.setLogin(loginEdit.getValue());
        updatedClient.setName(nameEdit.getValue());
        updatedClient.setSurname(surnameEdit.getValue());
        updatedClient.setEmail(emailEdit.getValue());
        updatedClient.setPhoneNumber(phoneNumberEdit.getValue());
        clientService.editClient(id,updatedClient);
        Notification.show("Klient zaktualizowany");
    }
    private void deleteClient(){
        Integer id= Integer.parseInt(deleteId.getValue());
        clientService.deleteClient(id);
        Notification.show("Klient o id: " +id +" został usunięty" );
    }
}
