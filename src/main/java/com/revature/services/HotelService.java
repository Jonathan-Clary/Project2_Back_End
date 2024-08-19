package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.models.Hotel;
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

    public Optional<Hotel> findHotelById(Long hotelId) {
        return hotelDAO.getHotelById(hotelId);
        //custom exception
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelDAO.save(hotel);
    }

    public void deleteHotel(Long hotelId) {
        hotelDAO.deleteById(hotelId);
    }

    public void saveOrUpdateHotel(Hotel hotel) {
        // Check if the hotel already exists by hotelId
        Optional<Hotel> existingHotel = hotelDAO.findById(hotel.getHotelId());

        if (existingHotel.isPresent()) {
            // If the hotel exists, update the existing record
            Hotel updatedHotel = existingHotel.get();
            updatedHotel.setHotelName(hotel.getHotelName());
            updatedHotel.setAddress(hotel.getAddress());
            hotelDAO.save(updatedHotel); // Save the updated hotel
        } else {
            // If the hotel does not exist, save it as a new record
            hotelDAO.save(hotel);
        }
    }
}