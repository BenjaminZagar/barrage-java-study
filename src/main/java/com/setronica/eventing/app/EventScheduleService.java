package com.setronica.eventing.app;

import com.setronica.eventing.dto.EventScheduleUpdate;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.EventScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventScheduleService {

    private final EventScheduleRepository eventScheduleRepository;

    public EventScheduleService(EventScheduleRepository eventRepository) {
        this.eventScheduleRepository = eventRepository;
    }

    public List<EventSchedule> getAll() {

        return eventScheduleRepository.findAll();
    }

    public EventSchedule findById(Integer id) {
        return eventScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Event schedule not found with id=" + id));
    }

    public EventSchedule save(EventSchedule eventSchedule, Event exsistingEvent) {
        eventSchedule.setEventDate(exsistingEvent.getDate());
        eventSchedule.setEventId(exsistingEvent.getId());
        return eventScheduleRepository.save(eventSchedule);
    }

    public EventSchedule update(EventSchedule existingEventSchedule, EventScheduleUpdate eventScheduleUpdate) {
        existingEventSchedule.setPrice(eventScheduleUpdate.getPrice());
        existingEventSchedule.setAvailableSeats(eventScheduleUpdate.getAvailableSeats());
        return eventScheduleRepository.save(existingEventSchedule);
    }

    public void delete(Integer id) {
        eventScheduleRepository.deleteById(id);
    }

}
