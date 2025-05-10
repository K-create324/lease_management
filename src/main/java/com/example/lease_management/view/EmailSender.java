package com.example.lease_management.view;


import com.example.lease_management.email.EmailService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;

@Route("/email_sender")
@CssImport("./themes/my-theme/styles.css")
public class EmailSender extends VerticalLayout {

    private final EmailService emailService;

    private TextField emailAddres = new TextField("Adres odbiorcy");
    private TextField subject= new TextField("Temat");
    private TextArea body= new TextArea("Treść wiadomości");
    private Button send= new Button("Wyślij");
    VerticalLayout form = new VerticalLayout();
    H3 header = new H3("WYSYŁKA EMAIL");




    public EmailSender(EmailService emailService) {
        this.emailService = emailService;

        header.addClassName("section-header");

        emailAddres.addClassName("half-width");
        subject.addClassName("half-width");
        body.addClassName("email-body");
        send.addClassName("button.send-email");
        form.addClassName("form-wrapper");
        form.add(header,emailAddres,subject,body,send);
        add(form);

        send.addClickListener(e-> {
            emailService.sendEmail(emailAddres.getValue(), subject.getValue(), body.getValue());
            Notification.show("Email wysłany poprawnie");
        });



    }


}
