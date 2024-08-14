package com.revature.controllers;


import com.revature.services.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stays")
@CrossOrigin
public class StayController {

    private StayService stayService;

    @Autowired
    public StayController(StayService stayService) {
        this.stayService = stayService;
    }
}
