package com.vicky.event.manager;

import com.vicky.event.model.Event;
import com.vicky.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderManager {

    @Autowired
    private EventRepository repository;

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public void addEvent(Event event) {
        repository.save(event);
    }

    public void updateEvent(Event event) {
        repository.save(event);
    }

    public void removeEvent(String id) {
        repository.deleteById(id);
    }

    public void markAsComplete(String id) {
        repository.findById(id).ifPresent(event -> {
            event.setCompleted(true);
            repository.save(event);
        });
    }
}
