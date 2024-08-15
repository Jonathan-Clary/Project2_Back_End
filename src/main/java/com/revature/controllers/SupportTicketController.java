package com.revature.controllers;

import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.SupportTicket.Type;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.services.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Mappings

    //Endpoint ./support?id={supportTicketId}
    @GetMapping
    public ResponseEntity<?> getSupportTicketById( @RequestParam(name = "id") int id ) {

        try {

            UserOutgoingSupportTicketDTO returnSupportTicket = sts.getSupportTicketById(id);
            return ResponseEntity.ok(returnSupportTicket);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

    //Case-sensitive. Query in ALL CAPS!
    @GetMapping("/get")
    public ResponseEntity<?> getSupportTicketsByType( @RequestParam(name = "type", required = false) Type type) {

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

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        } catch (Exception e) { //TODO::Change Generic Exception into SupportTicketNotFoundException
            return ResponseEntity.status(400).body(e.getMessage());

        }

    }

}
