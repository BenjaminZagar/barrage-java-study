package com.setronica.eventing.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Component
public class EventRepository {

    private final ObjectMapper objectMapper;

    public EventRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Event> findAll() {
        try (InputStream inputStream = EventRepository.class.getResourceAsStream("/static/events.json")) {
            if (inputStream != null) {
                return objectMapper.readValue(inputStream, EventList.class);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
