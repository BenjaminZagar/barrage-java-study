package com.setronica.eventing.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdate {
    private int id;
    private String title;
    private String description;
    private LocalDate date;
}
