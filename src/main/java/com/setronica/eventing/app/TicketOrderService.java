package com.setronica.eventing.app;

import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.exceptions.NotEnoughSeats;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.Status;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.persistence.TicketOrderRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicketOrderService.class);

    private final TicketOrderRepository ticketOrderRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    public List<TicketOrder> getAll() {
        log.info("Fetching all ticket orders from database");
        return ticketOrderRepository.findAll();
    }

    public TicketOrder findById(Integer id) {
        log.info("Fetching ticket order with id {} from database", id);
        return ticketOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket order not found with id=" + id));
    }

    public TicketOrder save(TicketOrder ticketOrder, EventSchedule eventSchedule) {
        log.info("Creating ticket order in database");
        ticketOrder.setEventScheduleId(eventSchedule.getId());
        ticketOrder.setPrice(eventSchedule.getPrice());
        ticketOrder.setStatus(Status.BOOKED);
        try {
            return ticketOrderRepository.save(ticketOrder);
        } catch (Exception e) {
                throw new NotEnoughSeats("Not enough seats available");
        }
    }

    public TicketOrder update(TicketOrder existingTicketOrder, TicketOrderUpdate ticketOrderUpdate) {
        log.info("Updating ticket order with id {} in database", existingTicketOrder.getId());
        existingTicketOrder.setFirstname(ticketOrderUpdate.getFirstname());
        existingTicketOrder.setLastname(ticketOrderUpdate.getLastname());
        existingTicketOrder.setEmail(ticketOrderUpdate.getEmail());
        existingTicketOrder.setAmount(ticketOrderUpdate.getAmount());
        try {
            return ticketOrderRepository.save(existingTicketOrder);
        } catch (Exception e) {
            throw new NotEnoughSeats("Not enough seats available");
        }
    }

    public void delete(Integer id) {
        log.info("Deleting ticket order with id {} from database", id);
        ticketOrderRepository.deleteById(id);
    }

}

