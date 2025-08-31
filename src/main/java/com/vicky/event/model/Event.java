package com.vicky.event.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  // DB generates UUID automatically
    private String id;

    private String title;
    private LocalDate date;
    private LocalTime time;

    @Column(length = 1000)   // description max length = 1000 chars
    private String description;

    private boolean completed = false;

    // Default constructor required by JPA
    public Event() {}

    // Constructor without ID (UI creates event with this)
    public Event(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getters & Setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}

