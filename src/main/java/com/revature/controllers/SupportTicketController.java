package com.revature.controllers;

import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.services.SupportTicketService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/support")
@CrossOrigin                    //TODO::Create extra security safety
public class SupportTicketController {

    Logger log = LoggerFactory.getLogger(SupportTicketController.class);
    //Model Variables
    private SupportTicketService sts;

    //Constructor
    @Autowired
    public SupportTicketController(SupportTicketService sts) {
        this.sts = sts;
    }


    /*
    *   ==============GET MAPPINGS=================
    */

    //Return Support Ticket with Given Id
    @GetMapping
    public ResponseEntity<?> getSupportTicketById( @RequestParam(name = "id") UUID id ) {
        log.debug("Endpoint GET ./support reached, id={}",id);

        try {

            OutgoingSupportTicketDTO returnSupportTicket = sts.getSupportTicketById(id);
            return ResponseEntity.ok(returnSupportTicket);

        } catch (SupportTicketNotFoundException e) {
            log.warn("Exception was thrown: {}", e.getMsg());
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllSupportTicketsByUserId( @PathVariable UUID userId) {
        log.debug("Endpoint GET ./support/{} reached",userId);

        try {

            List<OutgoingSupportTicketDTO> returnTickets = sts.getAllSupportTicketsByUserId(userId);
            return ResponseEntity.ok(returnTickets);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    /*
    *   ==============POST MAPPINGS=================
    */

    //Registers a Support Ticket to the DB
    @PostMapping
    public ResponseEntity<?> register(@RequestBody IncomingSupportTicketDTO incomingTicket){
        log.debug("Endpoint POST ./support reached");
        try {

            OutgoingSupportTicketDTO outgoingTicket = sts.register(incomingTicket);
            return ResponseEntity.status(201).body(outgoingTicket);

        } catch (CustomException e) {
            log.warn("Exception was thrown: {}", e.getMsg());
            return ResponseEntity.status(400).body(e.getMessage());

        }


    }

    /*
    *   ==============DELETE MAPPINGS=================
    */

    //Delete a Support Ticket from the DB
    @DeleteMapping("/{supportId}")
    public ResponseEntity<?> delete(@PathVariable UUID supportId){
        log.debug("Endpoint DELETE ./support/{} reached",supportId);

        try{
            return ResponseEntity.ok(sts.delete(supportId));

        } catch (SupportTicketNotFoundException e) {
            log.warn("Exception was thrown: {}", e.getMsg());
            return ResponseEntity.ok(e.getMessage());

        }

    }

}
