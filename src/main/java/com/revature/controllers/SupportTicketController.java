package com.revature.controllers;

import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.enums.TicketType;
import com.revature.exceptions.AdminNotFoundException;
import com.revature.exceptions.InvalidStatusException;
import com.revature.exceptions.InvalidTypeException;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.services.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //Endpoint ./support?id={supportTicketId}
    @GetMapping
    public ResponseEntity<?> getSupportTicketById( @RequestParam(name = "id") int id ) {

        try {

            UserOutgoingSupportTicketDTO returnSupportTicket = sts.getSupportTicketById(id);
            return ResponseEntity.ok(returnSupportTicket);

        } catch (SupportTicketNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    //Case-sensitive. Query in ALL CAPS!
    @GetMapping("/get")
    public ResponseEntity<?> getSupportTicketsByType( @RequestParam(name = "type") TicketType type) {

        //TODO::Create Service
        return ResponseEntity.ok("TODO");
    }

    //Get All SupportTickets
    @GetMapping("/get/all")
    public ResponseEntity<List<UserOutgoingSupportTicketDTO>> getAllSupportTickets() {
        return ResponseEntity.ok(sts.getAllSupportTickets());
    }

    //Returns all support tickets assigned to admin
    @GetMapping("/get/admin")
    public ResponseEntity<?> getAllSupportTicketsForAdmin( @RequestParam(name = "adminId", required = false) Integer id) {

        if (id == null) {
            return ResponseEntity.ok(sts.getAlSupportTicketsAdmin());
        }

        try {

            List<AdminOutgoingSupportTicketDTO> returnList = sts.getAllToAdminId(id);
            return ResponseEntity.ok(returnList);

        } catch (AdminNotFoundException | SupportTicketNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    /*
    *   ==============POST MAPPINGS=================
    */

    //Registers a Support Ticket to the DB
    @PostMapping("/register")
    public ResponseEntity<?> register(IncomingSupportTicketDTO incomingTicket){

        try {

            UserOutgoingSupportTicketDTO outgoingTicket = sts.register(incomingTicket);
            return ResponseEntity.status(201).body(outgoingTicket);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }


    }

    /*
    *   ==============PATCH MAPPINGS=================
    */

    //Update a Support Ticket's Type and/or Description
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestParam(name = "description", required = false) String description,
                                    @RequestParam(name = "type", required = false) String type){

        if (!(description.isEmpty()) && !(type.isEmpty())) {

            try {

                sts.updateDescription(id, description);
                UserOutgoingSupportTicketDTO outgoingTicket = sts.updateType(id, type);
                return ResponseEntity.ok(outgoingTicket);

            } catch (SupportTicketNotFoundException | InvalidTypeException e) {
                return ResponseEntity.status(e.getStatus()).body(e.getMessage());

            }

        } else if (!(type.isEmpty())) {

            try {

                UserOutgoingSupportTicketDTO outgoingTicket = sts.updateType(id, type);
                return ResponseEntity.ok(outgoingTicket);

            } catch (SupportTicketNotFoundException | InvalidTypeException e) {
                return ResponseEntity.status(e.getStatus()).body(e.getMessage());

            }

        } else {

            try {

                UserOutgoingSupportTicketDTO outgoingTicket = sts.updateDescription(id, description);
                return ResponseEntity.ok(outgoingTicket);

            } catch (SupportTicketNotFoundException e) {
                return ResponseEntity.status(e.getStatus()).body(e.getMessage());

            }

        }

    }

    //Resolve a Support Ticket
    @PatchMapping("/resolve/{id}")
    public ResponseEntity<?> resolve(@PathVariable int id, @RequestBody String type){

        try {

            UserOutgoingSupportTicketDTO resolvedTicket = sts.updateStatus(id, type);
            return ResponseEntity.ok(resolvedTicket);

        } catch (SupportTicketNotFoundException | InvalidStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());

        }

    }

    /*
    *   ==============DELETE MAPPINGS=================
    */

    //Delete a Support Ticket from the DB
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(int id){

        try{
            return ResponseEntity.ok(sts.delete(id));

        } catch (SupportTicketNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());

        }

    }

}
