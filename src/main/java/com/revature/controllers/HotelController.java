package com.revature.controllers;

import com.revature.exceptions.CustomException;
import com.revature.models.LocalHotel;
import com.revature.services.AmadeusHotelService;
import com.revature.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {


    private final AmadeusHotelService amadeusHotelService;
    private final HotelService hotelService;

    @Autowired
    public HotelController( AmadeusHotelService amadeusHotelService, HotelService hotelService) {
        this.amadeusHotelService = amadeusHotelService;
        this.hotelService = hotelService;
    }

    @GetMapping("/fetch-by-city")
    public ResponseEntity<List<LocalHotel>> fetchHotelsByCity(@RequestParam String cityCode) {
        List<LocalHotel> hotels = amadeusHotelService.fetchAndSaveHotelsByCity(cityCode);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/duration")
    public ResponseEntity<Object> getHotelsByDuration(@RequestParam String from, @RequestParam String to) throws CustomException {
        return ResponseEntity.ok(hotelService.getHotelsByDuration(from, to));
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }

}


