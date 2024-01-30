package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventUpdate;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> all() {
        try {
            List<Event> allEvents = eventService.getAll();
            if (allEvents.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(allEvents);
        } catch (DataAccessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("{id}")
    public Event show(
            @PathVariable int id
    ) {
        return eventService.findById(id);
    }

    @PostMapping
    public Event create(@Valid @RequestBody Event event) {
           return eventService.save(event);
    }

    @PutMapping("{id}")
    public Event update(@PathVariable int id, @RequestBody EventUpdate updatedEvent) {
            Event existingEvent = eventService.findById(id);
            return eventService.update(updatedEvent, existingEvent);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        Event existingEvent = eventService.findById(id);
        eventService.delete(id);
    }
}
