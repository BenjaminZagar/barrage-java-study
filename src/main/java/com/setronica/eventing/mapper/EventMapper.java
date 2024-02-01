package com.setronica.eventing.mapper;

import com.setronica.eventing.persistence.EventUpdate;
import com.setronica.eventing.persistence.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EventMapper {

    public abstract EventUpdate mapToDto(Event entity);

    public abstract Event mapToEvent(EventUpdate dto);
}
