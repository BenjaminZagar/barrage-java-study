package com.setronica.eventing.web;

import com.setronica.eventing.app.PaymentRecordService;
import com.setronica.eventing.app.TicketOrderService;
import com.setronica.eventing.persistence.TicketOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event/api/v1/events/event_schedules/ticket_orders")
public class PaymentRecordController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRecordController.class);
    private final PaymentRecordService paymentRecordService;
    private final TicketOrderService ticketOrderService;

    public PaymentRecordController(TicketOrderService ticketOrderService, PaymentRecordService paymentRecordService) {
        this.ticketOrderService = ticketOrderService;
        this.paymentRecordService = paymentRecordService;
    }

    @PostMapping("{id}/pay")
    @Operation(tags = {"Payment record management"}, summary = "Saves payment record and executes payment")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void pay(@PathVariable Integer id) {
        log.info("Request to pay ticket order");
        TicketOrder existingTicketOrder = ticketOrderService.findById(id);
        paymentRecordService.pay(existingTicketOrder);
    }

}
