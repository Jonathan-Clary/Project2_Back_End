package com.revature.controllers;



import com.revature.DTOs.IncomingStayDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidDateRangeException;
import com.revature.models.Hotel;
import com.revature.models.Stay;
import com.revature.models.User;
import com.revature.services.HotelService;
import com.revature.services.StayService;
import com.revature.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/stays")
@CrossOrigin
public class StayController {

    Logger log = LoggerFactory.getLogger(StayController.class);
    private StayService stayService;
    private UserService userService;
    private HotelService hotelService;

    public StayController(){

    }

    @Autowired
    public StayController(StayService stayService, UserService userService, HotelService hotelService) {
        this.stayService = stayService;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<Stay> createStay(@RequestBody IncomingStayDTO stay) throws CustomException {
        log.debug("Endpoint POST ./stays reached");
        User user = userService.getUserById(stay.getUserId());//Will throw if user doesn't exist
        Hotel hotel =  hotelService.getHotelById(hotelService.saveHotel(stay.getHotel()).getHotelId());//Will throw if hotel doesn't exist

        Date bookedDate = stay.getBookedDate();
        Date endDate = stay.getEndDate();

        if(endDate.before(bookedDate) || endDate.equals(bookedDate))
            throw new InvalidDateRangeException(bookedDate,endDate);


        //Can use Stay object instead of DTO because of @JsonProperty and @Transient annotations in Stay model

        Stay newStay =new Stay();
        newStay.setUser(user);
        newStay.setHotel(hotel);
        newStay.setBookedDate(bookedDate);
        newStay.setEndDate(endDate);

        return ResponseEntity.status(201).body(stayService.createStay(newStay));


    }
    @DeleteMapping("/{stayId}")
    public ResponseEntity<Object> deleteStay(@PathVariable UUID stayId) throws CustomException {
        log.debug("Endpoint DELETE ./stays/{} reached",stayId);
        userService.getUserById(stayId);//make sure user exists, will throw if not
        stayService.deleteStayById(stayId);
        return ResponseEntity.ok("Stay Deleted");
    }

    //Mapping to update hotel, bookedDate, and endDate. All at once, in pairs, or individually
    @PatchMapping("/{stayId}")
    public ResponseEntity<Stay> updateStay(@PathVariable UUID stayId, @RequestBody Map<String,String> stayFieldValues) throws CustomException {
        log.debug("Endpoint PATCH ./stays/{} reached",stayId);
        Stay stay = stayService.updateStay(stayId,stayFieldValues);
        return ResponseEntity.ok(stay);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        log.warn("Exception was thrown: {}", e.getMsg());
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }
    //STAYS-HISTORY: Controller Method for
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Stay>> getStaysByUserId(@PathVariable UUID userId) {
        log.debug("Endpoint GET ./user/{} reached",userId);
        List<Stay> stays = stayService.getStaysByUserId(userId);
        return ResponseEntity.ok(stays);
    }
}
