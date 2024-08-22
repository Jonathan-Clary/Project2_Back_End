package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.DAOs.StayDAO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidIDException;
import com.revature.exceptions.StayNotFoundException;
import com.revature.models.Hotel;
import com.revature.models.Stay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StayService {

    private StayDAO stayDAO;
    private HotelService hotelService;

    @Autowired
    public StayService(StayDAO stayDAO, HotelService hotelService) {
        this.stayDAO = stayDAO;
        this.hotelService = hotelService;
    }

    public Stay createStay(Stay stay) {
        return stayDAO.save(stay);
    }
    public void deleteStayById(int id){
        stayDAO.deleteById(id);
    }
    public Stay getStayById(int id) throws CustomException {
        if(id <= 0)
            throw new InvalidIDException();
        Optional<Stay> stay = stayDAO.findById(id);
        if(stay.isEmpty())
            throw new StayNotFoundException(id);
        return stay.get();

    }
    public Stay updateStay(int stayId, Map<String, String> stayFieldValues) throws CustomException {

        Stay stay = getStayById(stayId);

        if(stayFieldValues.containsKey("hotelId")){
            int id = Integer.parseInt(stayFieldValues.get("hotelId"));
            Hotel hotel = hotelService.getHotelById(id);
            stay.setHotel(hotel);
        }
        if(stayFieldValues.containsKey("bookedDate"))
            stay.setBookedDate(stayFieldValues.get("bookedDate"));
        if(stayFieldValues.containsKey("endDate"))
            stay.setBookedDate(stayFieldValues.get("endDate"));

        return stayDAO.save(stay);
    }

    //STAYS-HISTORY: Service for Getting Stay History
    public List<Stay> getStaysByUserId(int userId) {
        return stayDAO.findByUserId(userId);
    }
}
