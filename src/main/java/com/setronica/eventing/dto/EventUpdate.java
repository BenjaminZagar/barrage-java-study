package com.setronica.eventing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class EventUpdate {
    private int id;
    private String title;
    private String description;
    private LocalDate date;
}
