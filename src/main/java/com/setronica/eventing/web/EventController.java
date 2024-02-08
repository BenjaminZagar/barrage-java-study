package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.dto.EventUpdate;
import com.setronica.eventing.persistence.Event;
import jakarta.validation.Valid;
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
    public List<Event> all() {
        return eventService.getAll();
    }

    @GetMapping("{id}")
    public Event show(
            @PathVariable Integer id
    ) {
        return eventService.findById(id);
    }

    @PostMapping
    public Event create(@Valid @RequestBody Event event) {
           return eventService.save(event);
    }

    @PutMapping("{id}")
    public Event update(@PathVariable Integer id, @RequestBody EventUpdate updatedEvent) {
            Event existingEvent = eventService.findById(id);
            return eventService.update(updatedEvent, existingEvent);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        Event existingEvent = eventService.findById(id);
        eventService.delete(existingEvent.getId());
    }
}
