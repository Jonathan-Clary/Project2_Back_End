package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.exceptions.HotelNotFoundException;
import com.revature.models.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class HotelService {

    Logger log = LoggerFactory.getLogger(HotelService.class);

    private final HotelDAO hotelDAO;

    @Autowired
    public HotelService(HotelDAO hotelDAO) {

        this.hotelDAO = hotelDAO;
    }

    public List<Hotel> findAllHotels() {
        log.debug("Method 'findAllHotels' invoked");
        List<Hotel>  hotelList = hotelDAO.findAll();

        //Append id's to string for logging, because printing every object is excessive
        StringBuilder sb = new StringBuilder();
        for(Hotel f: hotelList){
            sb.append(f.getHotelId()).append(", ");
        }

        log.debug("Method 'findAllHotels' returning hotel list with hotel_ids: {}", sb.toString());
        return hotelList;
    }

    public Hotel saveHotel(Hotel hotel) {
        log.debug("Method 'saveHotel' invoked with hotel: {}", hotel.toString());
        Hotel returningHotel = hotelDAO.save(hotel);
        log.debug("Method 'saveHotel' returning: {}", returningHotel);
        return returningHotel;
    }

    public void deleteHotel(UUID hotelId) {
        log.debug("Method 'deleteHotel' invoked with hotelId: {}", hotelId);
        hotelDAO.deleteById(hotelId);
        log.debug("Method 'deleteHotel' completed");
    }

    public Hotel getHotelById(UUID hotelId) throws HotelNotFoundException {
        log.debug("Method 'getHotelById' invoked with hotelId: {}", hotelId);
        Optional<Hotel> hotel = hotelDAO.findById(hotelId);
        if(hotel.isPresent()) {
            log.debug("Method 'getHotelById' returning: {}", hotel.get());
            return hotel.get();
        }
        else
            throw new HotelNotFoundException(hotelId);

        //While this is clean, re-writing to assist with logging the process
//        return hotelDAO.findById(hotelId)
//                .orElseThrow(()-> {
//                    return new HotelNotFoundException(hotelId);
//                });
    }

    // add more methods WIP
}