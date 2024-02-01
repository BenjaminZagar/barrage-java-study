package com.setronica.eventing.app;

import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.exceptions.NotEnoughSeats;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.persistence.TicketOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderService {

    private final TicketOrderRepository ticketOrderRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    public List<TicketOrder> getAll() {
        return ticketOrderRepository.findAll();
    }

    public TicketOrder findById(Integer id) {
        return ticketOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket order not found with id=" + id));
    }

    public TicketOrder save(TicketOrder ticketOrder, EventSchedule eventSchedule) {
        List<TicketOrder> ticketOrders = eventSchedule.getTicketOrders();
        int total = 0;
        for (TicketOrder order : ticketOrders) {
            total += order.getAmount();
        }
        if (total + ticketOrder.getAmount() > eventSchedule.getAvailableSeats()) {
            throw new NotEnoughSeats("Not enough available seats. Number of available seats:  " + (eventSchedule.getAvailableSeats() - total));
        }
        ticketOrder.setEventScheduleId(eventSchedule.getId());
        return ticketOrderRepository.save(ticketOrder);
    }

    public TicketOrder update(TicketOrder existingTicketOrder, TicketOrderUpdate ticketOrderUpdate, EventSchedule relatedEventSchedule) {
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
        ticketOrderRepository.deleteById(id);
    }

}

