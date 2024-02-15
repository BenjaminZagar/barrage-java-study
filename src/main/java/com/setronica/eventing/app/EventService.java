package com.setronica.eventing.app;

import com.setronica.eventing.dto.EventUpdate;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll() {
        log.info("Fetching all events from database");
        return eventRepository.findAll();
    }

    public Event findById(Integer id) {
        log.info("Fetching event with id {} from database", id);
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found with id=" + id));
    }

    public Event save(Event event) {
        log.info("Creating event in database");
        return eventRepository.save(event);
    }

    public Event update(EventUpdate eventUpdate, Event existingEvent) {
        log.info("Updating event with id {} in database", existingEvent.getId());
        existingEvent.setTitle(eventUpdate.getTitle());
        existingEvent.setDescription(eventUpdate.getDescription());
        existingEvent.setDate(eventUpdate.getDate());
        return eventRepository.save(existingEvent);
    }

    public void delete(int id) {
        log.info("Deleting event with id {} from database", id);
        eventRepository.deleteById(id);
    }

}
