package com.revature.controllers;



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
    public ResponseEntity<Stay> createStay(@RequestBody Stay stay) throws CustomException {
        User user = userService.getUserById(stay.getUserId());//Will throw if user doesn't exist
        Hotel hotel =  hotelService.getHotelById(stay.getHotelId());//Will throw if hotel doesn't exist

        Date bookedDate = stay.getBookedDate();
        Date endDate = stay.getEndDate();

        if(endDate.before(bookedDate) || endDate.equals(bookedDate))
            throw new InvalidDateRangeException(bookedDate,endDate);



        //Can use Stay object instead of DTO because of @JsonProperty and @Transient annotations in Stay model
        stay.setHotel(hotel);
        stay.setUser(user);

        return ResponseEntity.status(201).body(stayService.createStay(stay));


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStay(@PathVariable UUID id) throws CustomException {
        userService.getUserById(id);//make sure user exists, will throw if not
        stayService.deleteStayById(id);
        return ResponseEntity.ok("Stay Deleted");
    }

    //Mapping to update hotel, bookedDate, and endDate. All at once, in pairs, or individually
    @PatchMapping("/{id}")
    public ResponseEntity<Stay> updateStay(@PathVariable UUID id, @RequestBody Map<String,String> stayFieldValues) throws CustomException {
        Stay stay = stayService.updateStay(id,stayFieldValues);
        return ResponseEntity.ok(stay);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        log.warn("Exception was thrown: {}", e.getMsg());
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }
    //STAYS-HISTORY: Controller Method for
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<Stay>> getStaysByUserId(@PathVariable UUID userId) {
        List<Stay> stays = stayService.getStaysByUserId(userId);
        return ResponseEntity.ok(stays);
    }
}
