package com.vicky.event.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = MainLayout.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(true);
        setPadding(true);

        add(new H2("About This Application"));

        String aboutText = "Created and Developed by Vikram Singh Ratour.<br><br>"
                + "The Event Reminder System is a simple yet powerful web application built with Spring Boot & Vaadin.<br>"
                + "It helps you:<br>"
                + "📝 Create, edit, and delete events<br>"
                + "⏰ Add dates and times for reminders<br>"
                + "✅ Track pending and completed events<br>"
                + "💾 Store data safely in a database (H2 by default)";

        Paragraph aboutParagraph = new Paragraph();
        aboutParagraph.getElement().setProperty("innerHTML", aboutText);

        add(aboutParagraph);
    }
}