package com.vicky.event.ui;

import com.vicky.event.manager.ReminderManager;
import com.vicky.event.model.Event;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Event Reminder")
@SuppressWarnings("deprecation")
public class MainView extends VerticalLayout {

    private final ReminderManager manager;

    @Autowired
    public MainView(ReminderManager manager) {
        this.manager = manager;

        setSpacing(true);
        setPadding(true);
        setSizeFull();
        getStyle().set("background", "#f9fafb");

        // Header
        H2 heading = new H2("Event Reminder");
        heading.getStyle().set("text-align", "left").set("margin-top", "10px").set("color", "#1a1a1a");
        add(heading);

        // Add Event Button
        Button addEventBtn = new Button("➕ Add New Event", e -> showAddDialog());
        addEventBtn.getStyle()
                .set("background-color", "#2563eb")
                .set("color", "white")
                .set("border-radius", "6px")
                .set("padding", "6px 14px")
                .set("font-size", "14px")
                .set("font-weight", "500");
        add(addEventBtn);

        refreshUI();

        // Footer pinned bottom
        Footer footer = new Footer();
        footer.add(new Label("© 2025 Event Reminder System - All rights reserved"));
        footer.getStyle()
                .set("text-align", "center")
                .set("color", "#6b7280")
                .set("padding", "12px")
                .set("background-color", "#f3f4f6")
                .set("position", "fixed")
                .set("bottom", "0")
                .set("left", "0")
                .set("width", "100%");
        add(footer);
    }

    private void refreshUI() {
        getChildren().filter(c -> !(c instanceof H2) && !(c instanceof Button) && !(c instanceof Footer))
                .forEach(this::remove);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Separate events
        List<Event> pendingEvents = manager.getAllEvents().stream()
                .filter(e -> !e.isCompleted())
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(e -> e.getTime() != null ? e.getTime() : LocalTime.MIN))
                .collect(Collectors.toList());

        List<Event> completedEvents = manager.getAllEvents().stream()
                .filter(Event::isCompleted)
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(e -> e.getTime() != null ? e.getTime() : LocalTime.MIN))
                .collect(Collectors.toList());

        // Render Pending Section
        if (!pendingEvents.isEmpty()) {
            H2 pendingHeader = new H2("📌 Pending Events");
            pendingHeader.getStyle().set("color", "red");
            add(pendingHeader);

            pendingEvents.forEach(event -> add(buildEventCard(event, dateFormatter, timeFormatter)));
        }

        // Render Completed Section
        if (!completedEvents.isEmpty()) {
            H2 completedHeader = new H2("✅ Completed Events");
            completedHeader.getStyle().set("color", "green");
            add(completedHeader);

            completedEvents.forEach(event -> add(buildEventCard(event, dateFormatter, timeFormatter)));
        }
    }

    private HorizontalLayout buildEventCard(Event event, DateTimeFormatter dateFormatter, DateTimeFormatter timeFormatter) {
        HorizontalLayout card = new HorizontalLayout();
        card.setWidthFull();
        card.setSpacing(true);
        card.getStyle()
                .set("background", "white")
                .set("padding", "16px")
                .set("border-radius", "10px")
                .set("margin", "12px 0")
                .set("box-shadow", "0 2px 6px rgba(0,0,0,0.06)");

        // Left Box
        VerticalLayout leftBox = new VerticalLayout();
        leftBox.setWidth("50%");
        leftBox.setHeight("150px");
        leftBox.getStyle()
                .set("background-color", "#f9fafb")
                .set("border-radius", "8px")
                .set("padding", "12px")
                .set("display", "flex")
                .set("justify-content", "space-between");

        // Title + Status
        HorizontalLayout topRow = new HorizontalLayout();
        String shortTitle = truncateText(event.getTitle(), 20);
        H2 title = new H2(shortTitle);
        title.getStyle().set("margin", "0").set("font-size", "16px").set("font-weight", "600");
        Label statusBadge = new Label(event.isCompleted() ? "Completed" : "Pending");
        statusBadge.getStyle()
                .set("padding", "2px 8px")
                .set("border-radius", "12px")
                .set("font-size", "12px")
                .set("font-weight", "500")
                .set("color", event.isCompleted() ? "#065f46" : "#92400e")
                .set("background-color", event.isCompleted() ? "#d1fae5" : "#fef3c7");
        topRow.add(title, statusBadge);
        topRow.setWidthFull();
        topRow.setJustifyContentMode(JustifyContentMode.BETWEEN);

        // Date + Time
        String dateTimeStr = "📅 " + event.getDate().format(dateFormatter);
        if (event.getTime() != null) {
            dateTimeStr += " ⏰ " + event.getTime().format(timeFormatter);
        }
        Label dateTimeLabel = new Label(dateTimeStr);
        dateTimeLabel.getStyle().set("font-size", "13px").set("color", "#4b5563");

        // Actions
        Button editBtn = new Button("Edit", e -> showEditDialog(event));
        styleSmallButton(editBtn, "#e0e7ff", "#3730a3");

        Button markDoneBtn = new Button(event.isCompleted() ? "✓ Done" : "Mark Done", ev -> {
            manager.markAsComplete(event.getId());
            showSuccess("Event Marked as Done");
            getUI().ifPresent(ui -> ui.getPage().reload()); // auto refresh
        });
        styleSmallButton(markDoneBtn,
                event.isCompleted() ? "#bbf7d0" : "#dcfce7",
                "#166534");

        Button deleteBtn = new Button("Delete", ev -> {
            manager.removeEvent(event.getId());
            showSuccess("Event Deleted");
            getUI().ifPresent(ui -> ui.getPage().reload()); // auto refresh
        });
        styleSmallButton(deleteBtn, "#fee2e2", "#991b1b");

        HorizontalLayout actions = new HorizontalLayout(editBtn, markDoneBtn, deleteBtn);
        actions.setSpacing(true);

        leftBox.add(topRow, dateTimeLabel, actions);

        // Right Box (Scrollable Description)
        VerticalLayout rightBox = new VerticalLayout();
        rightBox.setWidth("50%");
        rightBox.setHeight("150px");
        rightBox.getStyle()
                .set("background-color", "#f9fafb")
                .set("border-radius", "8px")
                .set("padding", "12px")
                .set("overflow", "auto"); // scrollable
        Label desc = new Label(event.getDescription());
        desc.getStyle()
                .set("font-size", "14px")
                .set("color", "#374151")
                .set("white-space", "normal");
        rightBox.add(desc);

        card.add(leftBox, rightBox);
        return card;
    }

    // Small Button Style Helper
    private void styleSmallButton(Button btn, String bgColor, String textColor) {
        btn.getStyle()
                .set("background-color", bgColor)
                .set("color", textColor)
                .set("border-radius", "4px")
                .set("font-size", "12px")
                .set("padding", "3px 8px");
    }

    // Truncate Helper
    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    // Dialogs
    private void showAddDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("480px");
        dialog.setHeight("520px");

        TextField titleField = new TextField("Title");
        DatePicker datePicker = new DatePicker("Date");
        datePicker.setValue(LocalDate.now());
        TimePicker timePicker = new TimePicker("Time");
        timePicker.setValue(LocalTime.now());
        TextField descField = new TextField("Description");

        Button saveBtn = new Button("Save", e -> {
            String title = titleField.getValue().trim();
            LocalDate selectedDate = datePicker.getValue();
            LocalTime selectedTime = timePicker.getValue();
            String description = descField.getValue().trim();

            if (title.isEmpty()) {
                showError("Title cannot be empty");
                return;
            }
            if (selectedDate == null) {
                showError("Please select a date");
                return;
            }
            if (description.length() > 1000) {
                showError("Description too long (max 1000 characters)");
                return;
            }

            Event event = new Event(title, selectedDate, description);
            event.setTime(selectedTime);
            manager.addEvent(event);
            showSuccess("Event Added Successfully");
            dialog.close();
            getUI().ifPresent(ui -> ui.getPage().reload()); // auto refresh
        });

        saveBtn.getStyle()
                .set("background-color", "#2563eb")
                .set("color", "white")
                .set("border-radius", "6px")
                .set("font-size", "14px")
                .set("padding", "6px 14px");

        VerticalLayout layout = new VerticalLayout(titleField, datePicker, timePicker, descField, saveBtn);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setAlignItems(Alignment.STRETCH);
        layout.getStyle().set("gap", "14px");

        dialog.add(layout);
        dialog.open();
    }

    private void showEditDialog(Event event) {
        Dialog dialog = new Dialog();
        dialog.setWidth("480px");
        dialog.setHeight("520px");

        TextField titleField = new TextField("Title", event.getTitle());
        DatePicker datePicker = new DatePicker("Date");
        datePicker.setValue(event.getDate());
        TimePicker timePicker = new TimePicker("Time");
        timePicker.setValue(event.getTime() != null ? event.getTime() : LocalTime.now());
        TextField descField = new TextField("Description", event.getDescription());

        Button saveBtn = new Button("Update", e -> {
            String title = titleField.getValue().trim();
            LocalDate selectedDate = datePicker.getValue();
            LocalTime selectedTime = timePicker.getValue();
            String description = descField.getValue().trim();

            if (title.isEmpty()) {
                showError("Title cannot be empty");
                return;
            }
            if (selectedDate == null) {
                showError("Please select a date");
                return;
            }
            if (description.length() > 1000) {
                showError("Description too long (max 1000 characters)");
                return;
            }

            event.setTitle(title);
            event.setDate(selectedDate);
            event.setTime(selectedTime);
            event.setDescription(description);

            manager.updateEvent(event);
            showSuccess("Event Updated Successfully");
            dialog.close();
            getUI().ifPresent(ui -> ui.getPage().reload()); // auto refresh
        });

        saveBtn.getStyle()
                .set("background-color", "#f59e0b")
                .set("color", "white")
                .set("border-radius", "6px")
                .set("font-size", "14px")
                .set("padding", "6px 14px");

        VerticalLayout layout = new VerticalLayout(titleField, datePicker, timePicker, descField, saveBtn);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setAlignItems(Alignment.STRETCH);
        layout.getStyle().set("gap", "14px");

        dialog.add(layout);
        dialog.open();
    }

    // Error notification
    private void showError(String message) {
        Notification notification = Notification.show(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    // Success notification
    private void showSuccess(String message) {
        Notification notification = Notification.show(message, 2500, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
