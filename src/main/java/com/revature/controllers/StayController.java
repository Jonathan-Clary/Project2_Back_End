package com.revature.controllers;


import com.revature.models.Stay;
import com.revature.services.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stays")
@CrossOrigin
public class StayController {

    private StayService stayService;

    @Autowired
    public StayController(StayService stayService) {
        this.stayService = stayService;
    }
    //STAYS-HISTORY: Controller Method for
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<Stay>> getStaysByUserId(@PathVariable int userId) {
        List<Stay> stays = stayService.getStaysByUserId(userId);
        return ResponseEntity.ok(stays);
    }
}
