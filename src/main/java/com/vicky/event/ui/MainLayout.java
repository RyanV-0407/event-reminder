package com.vicky.event.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        // Top navbar
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Event Reminder System");
        title.getStyle()
             .set("font-size", "1.8em")
             .set("color", "#4A4AFF")  // trendy blue
             .set("margin", "0");

        getElement().getStyle()
                .set("background", "linear-gradient(to right, #dfe9f3, #ffffff)");

        addToNavbar(toggle, title);

// Sidebar links
RouterLink eventPage = new RouterLink("Event Reminder", MainView.class);
eventPage.getStyle()
        .set("color", "#1e293b")
        .set("font-family", "Arial, sans-serif")
        .set("font-size", "16px")
        .set("font-weight", "600")
        .set("margin-bottom", "14px")
        .set("padding-left", "18px")
        .set("cursor", "pointer");

RouterLink aboutPage = new RouterLink("About", AboutView.class);
aboutPage.getStyle()
        .set("color", "#1e293b")
        .set("font-family", "Arial, sans-serif")
        .set("font-size", "16px")
        .set("font-weight", "600")
        .set("margin-bottom", "14px")
        .set("padding-left", "18px")
        .set("cursor", "pointer");

// Active link styling
eventPage.setHighlightCondition((link, event) -> link.getHref().equals(event.getLocation().getPath()));
aboutPage.setHighlightCondition((link, event) -> link.getHref().equals(event.getLocation().getPath()));

eventPage.setHighlightAction((link, active) -> {
    if (active) {
        link.getStyle()
            .set("color", "#2563eb")
            .set("font-weight", "700")
            .set("text-decoration", "underline");
    }
});
aboutPage.setHighlightAction((link, active) -> {
    if (active) {
        link.getStyle()
            .set("color", "#2563eb")
            .set("font-weight", "700")
            .set("text-decoration", "underline");
    }
});

// Hover effect (light blue)
eventPage.getElement().setAttribute("onmouseover", "this.style.color='#3b82f6'");
eventPage.getElement().setAttribute("onmouseout", "this.style.color='#1e293b'");
aboutPage.getElement().setAttribute("onmouseover", "this.style.color='#3b82f6'");
aboutPage.getElement().setAttribute("onmouseout", "this.style.color='#1e293b'");

// Footer
Span footer = new Span("© 2025 Event Reminder");
footer.getStyle()
        .set("color", "#64748b")
        .set("font-size", "13px")
        .set("font-family", "Arial, sans-serif")
        .set("margin-top", "25px")
        .set("display", "block")
        .set("text-align", "center");

addToDrawer(eventPage, aboutPage, footer);





    }
}
