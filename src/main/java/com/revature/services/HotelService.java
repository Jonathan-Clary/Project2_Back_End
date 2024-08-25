package com.revature.services;

import com.revature.DAOs.HotelDAO;
import com.revature.models.LocalHotel;
import com.revature.exceptions.HotelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {

    private final HotelDAO hotelDAO;

    @Autowired
    public HotelService(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    public List<LocalHotel> findAllHotels() {
        return hotelDAO.findAll();
    }

    public LocalHotel findHotelById(UUID hotelId) {
        return hotelDAO.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + hotelId + " not found"));
    }

    public Optional<LocalHotel> findHotelByApiHotelId(String apiHotelId) {
        return hotelDAO.findByApiHotelId(apiHotelId);
    }

    public LocalHotel saveHotel(LocalHotel hotel) {
        return hotelDAO.save(hotel);
    }

    public void deleteHotel(UUID hotelId) {
        LocalHotel hotel = findHotelById(hotelId); // This will throw an exception if the hotel does not exist
        hotelDAO.delete(hotel);
    }

    public void saveOrUpdateHotel(LocalHotel hotel) {
        hotelDAO.save(hotel);
    }
}
