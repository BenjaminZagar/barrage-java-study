package com.setronica.eventing.app;

import com.setronica.eventing.dto.EventScheduleUpdate;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.EventScheduleRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventScheduleService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventScheduleService.class);

    private final EventScheduleRepository eventScheduleRepository;

    public EventScheduleService(EventScheduleRepository eventRepository) {
        this.eventScheduleRepository = eventRepository;
    }

    public List<EventSchedule> getAll() {
        log.info("Fetching all event schedules from database");
        return eventScheduleRepository.findAll();
    }

    public EventSchedule findById(Integer id) {
        log.info("Fetching event schedule with id {} from database", id);
        return eventScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Event schedule not found with id=" + id));
    }

    public EventSchedule save(EventSchedule eventSchedule, Event exsistingEvent) {
        log.info("Creating event schedule in database");
        eventSchedule.setEventDate(exsistingEvent.getDate());
        eventSchedule.setEventId(exsistingEvent.getId());
        return eventScheduleRepository.save(eventSchedule);
    }

    public EventSchedule update(EventSchedule existingEventSchedule, EventScheduleUpdate eventScheduleUpdate) {
        log.info("Updating event schedule with id {} in database", existingEventSchedule.getId());
        existingEventSchedule.setPrice(eventScheduleUpdate.getPrice());
        existingEventSchedule.setAvailableSeats(eventScheduleUpdate.getAvailableSeats());
        return eventScheduleRepository.save(existingEventSchedule);
    }

    public void delete(Integer id) {
        log.info("Deleting event schedule with id {} in database", id);
        eventScheduleRepository.deleteById(id);
    }

}
