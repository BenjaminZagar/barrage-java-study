package com.setronica.eventing.app;

import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.exceptions.NotEnoughSeats;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.exceptions.OrderAlreadyProcessed;
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
        List<TicketOrder> ticketOrders = eventSchedule.getTicketOrders();
        int total = 0;
        for (TicketOrder order : ticketOrders) {
            total += order.getAmount();
        }
        if (total + ticketOrder.getAmount() > eventSchedule.getAvailableSeats()) {
            throw new NotEnoughSeats("Not enough available seats. Number of available seats:  " + (eventSchedule.getAvailableSeats() - total));
        }
        ticketOrder.setEventScheduleId(eventSchedule.getId());
        ticketOrder.setPrice(eventSchedule.getPrice());
        ticketOrder.setStatus(Status.BOOKED);
        return ticketOrderRepository.save(ticketOrder);
    }

    public TicketOrder update(TicketOrder existingTicketOrder, TicketOrderUpdate ticketOrderUpdate, EventSchedule relatedEventSchedule) {
        log.info("Updating ticket order with id {} in database", existingTicketOrder.getId());
        if (existingTicketOrder.getStatus() != Status.BOOKED) {
            throw new OrderAlreadyProcessed("Order already processed");
        }
        List<TicketOrder> ticketOrders = relatedEventSchedule.getTicketOrders();
        int total = 0;
        for (TicketOrder order : ticketOrders) {
            total += order.getAmount();
        }
        if (total - existingTicketOrder.getAmount() + ticketOrderUpdate.getAmount() > relatedEventSchedule.getAvailableSeats()) {
            throw new NotEnoughSeats("Not enough available seats. Number of available seats:  " + (relatedEventSchedule.getAvailableSeats() - total + existingTicketOrder.getAmount()));
        }
        existingTicketOrder.setFirstname(ticketOrderUpdate.getFirstname());
        existingTicketOrder.setLastname(ticketOrderUpdate.getLastname());
        existingTicketOrder.setEmail(ticketOrderUpdate.getEmail());
        existingTicketOrder.setAmount(ticketOrderUpdate.getAmount());
        return ticketOrderRepository.save(existingTicketOrder);
    }

    public void delete(Integer id) {
        log.info("Deleting ticket order with id {} from database", id);
        ticketOrderRepository.deleteById(id);
    }

}

