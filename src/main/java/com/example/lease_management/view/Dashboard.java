package com.example.lease_management.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


@Route("dashboard")
public class Dashboard extends VerticalLayout {
    public Dashboard() {

        H1 headher = new H1("Witaj w Panelu Głównym");
        Header logOut = new Header();
        ;
        Button button1 = new Button("Widok ogólny", e -> getUI().ifPresent(ui -> ui.navigate("main_view")));
        Button button2 = new Button("Panel klienta", e -> getUI().ifPresent(ui -> ui.navigate("client_panel")));
        Button button3 = new Button("Panel umowy", e -> getUI().ifPresent(ui -> ui.navigate("contract_panel")));

        add(headher);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            add( button1);
        }


            add(button2, button3, logOut);
//Rola użytkownika w Spring Security to obiekt typu GrantedAuthority.
//
//Zalogowany użytkownik to obiekt typu Authentication.
    }
}