package com.setronica.eventing.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository <Event, Integer>{
}
