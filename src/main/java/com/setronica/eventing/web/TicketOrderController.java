package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.app.TicketOrderService;
import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.TicketOrder;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events/event_schedules")
public class TicketOrderController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicketOrderController.class);
    private final TicketOrderService ticketOrderService;
    private final EventScheduleService eventScheduleService;

    public TicketOrderController(TicketOrderService ticketOrderService, EventScheduleService eventScheduleService) {
        this.ticketOrderService = ticketOrderService;
        this.eventScheduleService = eventScheduleService;
    }

    @GetMapping("ticket_orders")
    public List<TicketOrder> all() {
        log.info("Request all ticket orders");
        return ticketOrderService.getAll();
    }

    @GetMapping("ticket_orders/{id}")
    public TicketOrder show(
            @PathVariable Integer id
    ) {
        log.info("Request ticket order with id {}", id);
        return ticketOrderService.findById(id);
    }

    @PostMapping("{id}/ticket_orders")
    public TicketOrder create(@PathVariable Integer id, @Valid @RequestBody TicketOrder ticketOrder) {
        log.info("Request create new ticket order for event schedule with id {}", id);
        EventSchedule existingEventSchedule = eventScheduleService.findById(id);
        return ticketOrderService.save(ticketOrder, existingEventSchedule);
    }

    @PutMapping("ticket_orders/{id}")
    public TicketOrder update(@PathVariable Integer id, @RequestBody TicketOrderUpdate ticketOrderUpdate) {
        log.info("Request update ticket order with id {}", id);
        TicketOrder existingTicketOrder = ticketOrderService.findById(id);
        EventSchedule relatedEventSchedule = eventScheduleService.findById(existingTicketOrder.getEventScheduleId());
        return ticketOrderService.update(existingTicketOrder, ticketOrderUpdate, relatedEventSchedule);
    }

    @DeleteMapping("ticket_orders/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Request delete ticket order with id {}", id);
        TicketOrder existingTicketOrder = ticketOrderService.findById(id);
        ticketOrderService.delete(existingTicketOrder.getId());
    }
}
