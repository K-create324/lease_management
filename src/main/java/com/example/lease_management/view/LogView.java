//package com.example.lease_management.view;
//
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.login.LoginForm;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//@Route("logowanie")
//@AnonymousAllowed
//public class LogView extends VerticalLayout {
//
//    public LogView() {
//
//
//        LoginForm loginForm = new LoginForm();
//        loginForm.setAction("login");
////        loginForm.addLoginListener(event->{
////            System.out.println("Próba logowania z loginem: " + event.getUsername());
////            // Sprawdzanie, czy login był poprawny i przekierowanie do dashboardu
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////            if (authentication != null) {
////                System.out.println("Authentication object: " + authentication.getName());
////                if (authentication.isAuthenticated()) {
////                    UI.getCurrent().navigate("dashboard");
////                } else {
////                    Notification.show("Nie udało się zalogować");
////                    UI.getCurrent().navigate("logowanie");
////                }
////            } else {
////                System.out.println("Brak Authentication w SecurityContext.");
////                Notification.show("Nie udało się zalogować");
////            }
////        });
//
//
//
////            UI.getCurrent().navigate("dashboard");
////        });
//
//        add(loginForm);
//
//    }
//}