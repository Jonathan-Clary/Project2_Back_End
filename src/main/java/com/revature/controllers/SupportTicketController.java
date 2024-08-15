package com.revature.controllers;

import com.revature.models.SupportTicket.Type;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.services.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //Mappings

    //Endpoint ./support?id={supportTicketId}
    @GetMapping
    public ResponseEntity<?> getSupportTicketById( @RequestParam(name = "id", required = false) int id ) {

        try {

            UserOutgoingSupportTicketDTO returnSupportTicket = sts.getSupportTicketById(id);
            return ResponseEntity.ok(returnSupportTicket);

        } catch (Exception e) {

            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    //TODO::GetMapping for getAllSupportTickets

    //Case-sensitive. Query in ALL CAPS!
    @GetMapping("/get")
    public ResponseEntity<?> getSupportTicketsByType( @RequestParam(name = "type", required = false) Type type) {

        //TODO::Create Service
        return ResponseEntity.ok("TODO");
    }

    //TODO::GetMapping which calls all SupportTickets of a Type and assigned to an a particular Admin(adinId)

}
