package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.app.TicketOrderService;
import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.TicketOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(tags = {"Ticket order management"}, summary = "Returns a list of all ticket orders")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<TicketOrder> all() {
        log.info("Request all ticket orders");
        return ticketOrderService.getAll();
    }

    @GetMapping("ticket_orders/{id}")
    @Operation(tags = {"Ticket order management"}, summary = "Returns ticket order by id")
    @ApiResponse(responseCode = "404", description = "Ticket order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public TicketOrder show(
            @PathVariable Integer id
    ) {
        log.info("Request ticket order with id {}", id);
        return ticketOrderService.findById(id);
    }

    @PostMapping("{id}/ticket_orders")
    @Operation(tags = {"Ticket order management"}, summary = "Creates new ticket order")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public TicketOrder create(@PathVariable Integer id, @Valid @RequestBody TicketOrder ticketOrder) {
        log.info("Request create new ticket order for event schedule with id {}", id);
        EventSchedule existingEventSchedule = eventScheduleService.findById(id);
        return ticketOrderService.save(ticketOrder, existingEventSchedule);
    }

    @PutMapping("ticket_orders/{id}")
    @Operation(tags = {"Ticket order management"}, summary = "Updates existing ticket order")
    @ApiResponse(responseCode = "404", description = "Ticket order not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public TicketOrder update(@PathVariable Integer id, @RequestBody TicketOrderUpdate ticketOrderUpdate) {
        log.info("Request update ticket order with id {}", id);
        TicketOrder existingTicketOrder = ticketOrderService.findById(id);
        return ticketOrderService.update(existingTicketOrder, ticketOrderUpdate);
    }

    @DeleteMapping("ticket_orders/{id}")
    @Operation(tags = {"Ticket order management"}, summary = "Deletes ticket order")
    @ApiResponse(responseCode = "404", description = "Ticket order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void delete(@PathVariable Integer id) {
        log.info("Request delete ticket order with id {}", id);
        TicketOrder existingTicketOrder = ticketOrderService.findById(id);
        ticketOrderService.delete(existingTicketOrder.getId());
    }
}
