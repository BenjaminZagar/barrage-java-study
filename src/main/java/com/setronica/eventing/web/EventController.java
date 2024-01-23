package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> findAll() {
        return eventService.getAll();
    }

    @GetMapping("search")
    public List<Event> searchEvents(
            @RequestParam String q
    ) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
