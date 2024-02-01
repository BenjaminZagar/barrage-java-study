package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.mapper.EventMapper;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;
import com.setronica.eventing.persistence.EventUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public List<Event> getAll() {

        return eventRepository.findAll();
    }

    public Event findById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found with id=" + id));
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event update(EventUpdate eventUpdate, Event existingEvent) {
        if (eventUpdate.getTitle() != null) {
            existingEvent.setTitle(eventUpdate.getTitle());
        }
        if (eventUpdate.getDescription() != null) {
            existingEvent.setDescription(eventUpdate.getDescription());
        }
        if (eventUpdate.getDate() != null) {
            existingEvent.setDate(eventUpdate.getDate());
        }
        return eventRepository.save(existingEvent);
    }

    public void delete(int id) {
        eventRepository.deleteById(id);
    }

}
