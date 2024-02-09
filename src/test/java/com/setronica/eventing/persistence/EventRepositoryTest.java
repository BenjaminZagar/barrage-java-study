package com.setronica.eventing.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventTestRepository;

    @Test
    void itShouldSaveEvent() {
        //given
        LocalDate currentDate = LocalDate.now();
        Event event = Event.builder()
                .title("test title")
                .description("test description")
                .date(currentDate)
                .build();
        //when
        eventTestRepository.save(event);
        //then
        assertThat(event.getId()).isGreaterThan(0);
    }

}