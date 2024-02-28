package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.dto.EventUpdate;
import com.setronica.eventing.persistence.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Operation(tags = {"Event management"}, summary = "Returns a list of events")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<Event> all() {
        log.info("Request all events");
        return eventService.getAll();
    }

    @GetMapping("{id}")
    @Operation(tags = {"Event management"}, summary = "Returns event by id")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Event show(
            @PathVariable Integer id
    ) {
        log.info("Request event with id {}", id);
        return eventService.findById(id);
    }

    @PostMapping
    @Operation(tags = {"Event management"}, summary = "Creates new event")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Event create(@Valid @RequestBody Event event) {
        log.info("Request create new event");
        return eventService.save(event);
    }

    @PutMapping("{id}")
    @Operation(tags = {"Event management"}, summary = "Updates existing event")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Event update(@PathVariable Integer id, @RequestBody EventUpdate updatedEvent) {
        log.info("Request update event with id {}", id);
        Event existingEvent = eventService.findById(id);
        return eventService.update(updatedEvent, existingEvent);
    }

    @DeleteMapping("{id}")
    @Operation(tags = {"Event management"}, summary = "Deletes event")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void delete(@PathVariable Integer id) {
        log.info("Request delete event with id {}", id);
        Event existingEvent = eventService.findById(id);
        eventService.delete(existingEvent.getId());
    }
}
