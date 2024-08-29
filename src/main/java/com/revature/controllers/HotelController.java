package com.revature.controllers;

import com.revature.DTOs.HotelDTO;
import com.revature.services.HotelAPIService;
import com.revature.services.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin
public class HotelController {
    Logger log = LoggerFactory.getLogger(HotelController.class);
    private HotelService hotelService;
    private HotelAPIService hotelAPI;

    @Autowired
    public HotelController(HotelService hotelService, HotelAPIService hotelAPI) {
        this.hotelService = hotelService;
        this.hotelAPI = hotelAPI;
    }

    @GetMapping("/{cityState}")
    public ResponseEntity<List<HotelDTO>> getStaysByUserId(@PathVariable String cityState) {
       try {
           List<HotelDTO> hotels = hotelAPI.findHotelsByCityAndState(cityState);
           return ResponseEntity.ok(hotels);
       }catch(Exception e){
           log.warn("Exception was thrown", e);
           return ResponseEntity.status(404).body(null);
       }
    }




}



