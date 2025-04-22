package com.example.lease_management.view;

import com.example.lease_management.Client;
import com.example.lease_management.Contract;
import com.example.lease_management.service.ClientService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

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
    private MemoryBuffer buffer= new MemoryBuffer();
    private Upload upload= new Upload(buffer);
    private Div uploadSection= new Div();
    private byte[] uploadedPdfBytes;
    private ComboBox<Client> comboBoxEditClient= new ComboBox<>("Wybierz klienta po ID");

    public ClientFormView(ClientService clientService) {
        this.clientService = clientService;


        //combo box do wyboru klienta do edycji
        comboBoxEditClient.setItems((Collection<Client>) clientService.getAllClients());
comboBoxEditClient.setItemLabelGenerator(client -> "Klient ID: " +client.getId() + "IMIĘ: " +client.getName() + "NAZWISKO: " + client.getSurname());

        //automatyczne uzupelnianie danymi umowy po wybraniu umowy
comboBoxEditClient.addValueChangeListener(event-> {Client selectedClient= event.getValue();


if(selectedClient!=null){
    loginEdit.setValue(selectedClient.getLogin());
    nameEdit.setValue(selectedClient.getName());
    surnameEdit.setValue(selectedClient.getSurname());
    phoneNumberEdit.setValue(selectedClient.getPhoneNumber());
    emailEdit.setValue(selectedClient.getEmail());
}



});




//upload pliku pdf
upload.setAcceptedFileTypes(".pdf");
upload.addSucceededListener(event->{
    try(InputStream inputStream= buffer.getInputStream()) {
        uploadedPdfBytes = inputStream.readAllBytes();
        Notification.show("Plik został zapisany tymczasowo");
    } catch (IOException e) {
        Notification.show("Błąd przy zapisie pliku");
                e.printStackTrace();
    }

    });

        H1 headher= new H1("PANEL KLIENTA");
        H3 headher1= new H3("Dodaj klienta");
        FormLayout form = new FormLayout();
        form.add(login,name,surname,email,phoneNumber,saveButton);

        saveButton.addClickListener(e->saveClient());

        H3 headher2= new H3("Edytuj klienta");

        FormLayout form1 = new FormLayout();
        uploadSection.add(new H3("Wgraj dokumenty rejestrowe klienta"),upload);
        form1.add(comboBoxEditClient,loginEdit,nameEdit,surnameEdit,emailEdit,phoneNumberEdit,uploadSection, editButton);
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



//if(comboBoxEditClient.isEmpty()|| login.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()){
//    Notification.show("Proszę o uzupełnienie wszystkich pól");
//    return;
//}
        Client clientSelectedFromCombo = comboBoxEditClient.getValue();
if(clientSelectedFromCombo== null) {
    Notification.show("nie wybrano klienta do edycji");
    return;
}
    Optional<Client> oneClientFromOptional = clientService.getOneClient(clientSelectedFromCombo.getId());
    Integer clientIDfromOptional= oneClientFromOptional.orElseThrow(() -> new RuntimeException("nie ma takiego klienta")).getId();

//        Integer id= Integer.parseInt(editId.getValue());
//        Client updatedClient= new Client();
        clientSelectedFromCombo.setLogin(loginEdit.getValue());
        clientSelectedFromCombo.setName(nameEdit.getValue());
        clientSelectedFromCombo.setSurname(surnameEdit.getValue());
        clientSelectedFromCombo.setEmail(emailEdit.getValue());
        clientSelectedFromCombo.setPhoneNumber(phoneNumberEdit.getValue());
        if(uploadedPdfBytes!=null){
            clientSelectedFromCombo.setPdfFile(uploadedPdfBytes);
           Notification.show("Plik wgrany poprawnie");
        }

        clientService.editClient(clientIDfromOptional,clientSelectedFromCombo);
        Notification.show("Klient zaktualizowany");
    }
    private void deleteClient(){
        Integer id= Integer.parseInt(deleteId.getValue());
        clientService.deleteClient(id);
        Notification.show("Klient o id: " +id +" został usunięty" );
    }
}
