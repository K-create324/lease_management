package com.example.lease_management.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Header extends HorizontalLayout {

    public Header() {
        Button logOutButton = new Button("Wyloguj", event -> {
            UI.getCurrent().getPage().executeJs("window.location.href = '/logout';");
        });

        setWidthFull();
        setAlignItems(FlexComponent.Alignment.END); // Wyrównanie do prawej strony
        add(logOutButton);
    }

}