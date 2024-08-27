package com.revature.controllers;

import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.services.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/support")
@CrossOrigin                    //TODO::Create extra security safety
public class SupportTicketController {

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
    @GetMapping("/get")
    public ResponseEntity<?> getSupportTicketById( @RequestParam(name = "id") UUID id ) {

        try {

            OutgoingSupportTicketDTO returnSupportTicket = sts.getSupportTicketById(id);
            return ResponseEntity.ok(returnSupportTicket);

        } catch (SupportTicketNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    //Get All SupportTickets
    @GetMapping("/get/all")
    public ResponseEntity<List<OutgoingSupportTicketDTO>> getAllSupportTickets() {
        return ResponseEntity.ok(sts.getAllSupportTickets());
    }

    /*
    *   ==============POST MAPPINGS=================
    */

    //Registers a Support Ticket to the DB
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody IncomingSupportTicketDTO incomingTicket){

        try {

            OutgoingSupportTicketDTO outgoingTicket = sts.register(incomingTicket);
            return ResponseEntity.status(201).body(outgoingTicket);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }


    }

    /*
    *   ==============DELETE MAPPINGS=================
    */

    //Delete a Support Ticket from the DB
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(UUID id){

        try{
            return ResponseEntity.ok(sts.delete(id));

        } catch (SupportTicketNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());

        }

    }

}
