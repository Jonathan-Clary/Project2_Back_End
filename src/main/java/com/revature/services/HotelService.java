package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.DAOs.StayDAO;
import com.revature.exceptions.CustomException;
import com.revature.models.LocalHotel;
import com.revature.exceptions.HotelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {

    private final HotelDAO hotelDAO;
    private final StayDAO stayDAO;

    @Autowired
    public HotelService(HotelDAO hotelDAO, StayDAO stayDAO) {
        this.hotelDAO = hotelDAO;
        this.stayDAO = stayDAO;
    }

    public List<LocalHotel> findAllHotels() {
        return hotelDAO.findAll();
    }

    public LocalHotel findHotelById(UUID hotelId) throws HotelNotFoundException {
        return hotelDAO.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + hotelId + " not found"));
    }

    public Optional<LocalHotel> findHotelByApiHotelId(String apiHotelId) {
        return hotelDAO.findByApiHotelId(apiHotelId);
    }

    public LocalHotel saveHotel(LocalHotel hotel) {
        return hotelDAO.save(hotel);
    }

    public void deleteHotel(UUID hotelId) throws CustomException {
        LocalHotel hotel = findHotelById(hotelId); // This will throw an exception if the hotel does not exist
        hotelDAO.delete(hotel);
    }

    public void saveOrUpdateHotel(LocalHotel hotel) {
        hotelDAO.save(hotel);
    }


    public List<LocalHotel> getHotelsByDuration(String from, String to) throws CustomException {
        Date from_date = valueOf(from);
        Date to_date = valueOf(to);
        if(to_date.before(from_date))
            throw new CustomException("Date1 cannot be after Date2.");

        return stayDAO.findByDuration(from_date, to_date);
    }

    private Date valueOf(String date) throws CustomException {
        try{
            return Date.valueOf(date);
        }catch (Exception q){
            throw new CustomException("Invalid Date: Please use the format [yyyy-mm-dd]");
        }
    }

}
