package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.app.EventService;
import com.setronica.eventing.dto.EventScheduleUpdate;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventSchedule;
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
    public List<EventSchedule> all() {
        log.info("Request all event schedules");
        return eventScheduleService.getAll();
    }

    @GetMapping("event_schedules/{id}")
    public EventSchedule show(
            @PathVariable Integer id
    ) {
        log.info("Request event schedule with id {}", id);
        return eventScheduleService.findById(id);
    }

    @PostMapping("{id}/event_schedules")
    public EventSchedule create(@PathVariable Integer id, @Valid @RequestBody EventSchedule eventSchedule) {
        log.info("Request create new event schedule for event with id {}", id);
        Event existingEvent = eventService.findById(id);
        return eventScheduleService.save(eventSchedule, existingEvent);
    }

    @PutMapping("event_schedules/{id}")
    public EventSchedule update(@PathVariable Integer id, @RequestBody EventScheduleUpdate eventScheduleUpdate) {
        log.info("Request update event schedule with id {}", id);
        EventSchedule existingScheduleEvent = eventScheduleService.findById(id);
        return eventScheduleService.update(existingScheduleEvent, eventScheduleUpdate);
    }

    @DeleteMapping("event_schedules/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Request delete event schedule with id {}", id);
        EventSchedule existingEvent = eventScheduleService.findById(id);
        eventScheduleService.delete(existingEvent.getId());
    }
}
