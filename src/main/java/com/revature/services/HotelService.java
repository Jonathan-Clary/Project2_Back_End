package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.models.Hotel;
import com.revature.exceptions.HotelNotFoundException; // Custom exception for not found hotels
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Hotel findHotelById(Long hotelId) {
        return hotelDAO.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + hotelId + " not found"));
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelDAO.save(hotel);
    }

    public void deleteHotel(Long hotelId) {
        if (!hotelDAO.existsById(hotelId)) {
            throw new HotelNotFoundException("Hotel with ID " + hotelId + " not found");
        }
        hotelDAO.deleteById(hotelId);
    }

    public void saveOrUpdateHotel(Hotel hotel) {
        // Check if the hotel already exists by hotelId and save or update
        hotelDAO.save(hotel);
    }
}
