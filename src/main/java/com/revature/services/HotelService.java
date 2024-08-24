package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.exceptions.HotelNotFoundException;
import com.revature.models.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class HotelService {

    private final HotelDAO hotelDAO;

    @Autowired
    public HotelService(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    public List<Hotel> findAllHotels() {
        return hotelDAO.findAll();
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelDAO.save(hotel);
    }

    public void deleteHotel(Integer hotelId) {
        hotelDAO.deleteById(hotelId);
    }

    public Hotel getHotelById(int hotelId) throws HotelNotFoundException {
        return hotelDAO.findById(hotelId)
                .orElseThrow(()-> {
                    return new HotelNotFoundException(hotelId);
                });
    }

    // add more methods WIP
}