package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.app.EventService;
import com.setronica.eventing.dto.EventScheduleUpdate;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventSchedule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventScheduleController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventScheduleController.class);

    private final EventScheduleService eventScheduleService;
    private final EventService eventService;

    public EventScheduleController(EventScheduleService eventScheduleService, EventService eventService) {
        this.eventScheduleService = eventScheduleService;
        this.eventService = eventService;
    }

    @GetMapping("event_schedules")
    @Operation(tags = {"Event schedules management"}, summary = "Returns a list of all event schedules")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<EventSchedule> all() {
        log.info("Request all event schedules");
        return eventScheduleService.getAll();
    }

    @GetMapping("event_schedules/{id}")
    @Operation(tags = {"Event schedules management"}, summary = "Returns event schedule by id")
    @ApiResponse(responseCode = "404", description = "Event schedule not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public EventSchedule show(
            @PathVariable Integer id
    ) {
        log.info("Request event schedule with id {}", id);
        return eventScheduleService.findById(id);
    }

    @PostMapping("{id}/event_schedules")
    @Operation(tags = {"Event schedules management"}, summary = "Creates new event schedule")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "400", description = "Not enough seats available")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public EventSchedule create(@PathVariable Integer id, @Valid @RequestBody EventSchedule eventSchedule) {
        log.info("Request create new event schedule for event with id {}", id);
        Event existingEvent = eventService.findById(id);
        return eventScheduleService.save(eventSchedule, existingEvent);
    }

    @PutMapping("event_schedules/{id}")
    @Operation(tags = {"Event schedule management"}, summary = "Updates existing event schedule")
    @ApiResponse(responseCode = "404", description = "Event schedule not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "400", description = "Not enough seats available")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public EventSchedule update(@PathVariable Integer id, @RequestBody EventScheduleUpdate eventScheduleUpdate) {
        log.info("Request update event schedule with id {}", id);
        EventSchedule existingScheduleEvent = eventScheduleService.findById(id);
        return eventScheduleService.update(existingScheduleEvent, eventScheduleUpdate);
    }

    @DeleteMapping("event_schedules/{id}")
    @Operation(tags = {"Event schedule management"}, summary = "Deletes event schedule")
    @ApiResponse(responseCode = "404", description = "Event schedule not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void delete(@PathVariable Integer id) {
        log.info("Request delete event schedule with id {}", id);
        EventSchedule existingEvent = eventScheduleService.findById(id);
        eventScheduleService.delete(existingEvent.getId());
    }
}
