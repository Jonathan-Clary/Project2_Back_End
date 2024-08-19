package com.revature.controllers;

import com.revature.services.AmadeusHotelService;
import com.revature.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {


    private final AmadeusHotelService amadeusHotelService;

    @Autowired
    public HotelController( AmadeusHotelService amadeusHotelService) {
        this.amadeusHotelService = amadeusHotelService;
    }

    @GetMapping("/fetch-by-city")
    public ResponseEntity<String> fetchHotelsByCity(@RequestParam String cityCode) {
        amadeusHotelService.fetchAndSaveHotelsByCity(cityCode);
        return ResponseEntity.ok("Hotels fetched and saved successfully");
    }
}


