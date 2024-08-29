package com.revature.services;

import com.revature.DAOs.StayDAO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidIDException;
import com.revature.exceptions.StayNotFoundException;
import com.revature.models.User;
import com.revature.models.Hotel;
import com.revature.models.Stay;
import com.revature.utils.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StayService {
    Logger log = LoggerFactory.getLogger(StayService.class);

    private StayDAO stayDAO;
    private HotelService hotelService;
    private UserService userService;

    @Autowired
    public StayService(StayDAO stayDAO, HotelService hotelService) {
        this.stayDAO = stayDAO;
        this.hotelService = hotelService;
    }

    public Stay createStay(Stay stay) throws CustomException {
        log.debug("Method 'createStay' invoked with stay: {}",stay);

        if (stay.getBookedDate().equals(stay.getEndDate()) || stay.getBookedDate().after(stay.getEndDate())) {
            throw new CustomException("Booked date must be before the end date!");
        }

        Stay returningStay = stayDAO.save(stay);
        log.debug("Method 'createStay' returning: {}",returningStay);
        return returningStay;
    }

    public void deleteStayById(UUID id){
        log.debug("Method 'deleteStayById' invoked with stay: {}",id);
        stayDAO.deleteById(id);
        log.debug("Method 'deleteStayById' completed");
    }
    public Stay getStayById(UUID id) throws CustomException {
        log.debug("Method 'getStayById' invoked with id: {}",id);
        if(id == null)
            throw new InvalidIDException();
        Optional<Stay> stay = stayDAO.findById(id);
        if(stay.isEmpty())
            throw new StayNotFoundException(id);
        log.debug("Method 'getStayById' returning: {}",stay.get());
        return stay.get();

    }
    public Stay updateStay(UUID stayId, Map<String, String> stayFieldValues) throws CustomException {
        log.debug("Method 'updateStay' invoked with stayId: {}, stayFieldValues: {}",stayId,stayFieldValues);
        Stay stay = getStayById(stayId);

        if(stayFieldValues.containsKey("hotelId")){
            UUID id = UUID.fromString(stayFieldValues.get("hotelId"));
            Hotel hotel = hotelService.getHotelById(id);
            stay.setHotel(hotel);
        }

        if(stayFieldValues.containsKey("bookedDate"))
            //stay.setBookedDate(stayFieldValues.get("bookedDate"));
            stay.setBookedDate(DateUtility.parse(stayFieldValues.get("bookedDate")));
        if(stayFieldValues.containsKey("endDate"))
            //stay.setBookedDate(stayFieldValues.get("endDate"));
            stay.setBookedDate(DateUtility.parse(stayFieldValues.get("endDate")));


        Stay returningStay = stayDAO.save(stay);
        log.debug("Method 'updateStay' returning: {}",returningStay);
        return returningStay;
    }

    //STAYS-HISTORY: Service for Getting Stay History
    public List<Stay> getStaysByUserId(UUID userId) {
        log.debug("Method 'getStaysByUserId' invoked with userId: {}",userId);
        List<Stay> stayList = stayDAO.findByUserUserId(userId);

        //Append id's to string for logging, because printing every object is excessive
        StringBuilder sb = new StringBuilder();
        for(Stay s: stayList){
            sb.append(s.getStayId()).append(", ");
        }

        log.debug("Method 'etStaysByUserId' returning stay list with stay_ids: {}", sb.toString());

        return stayList;
    }
}
