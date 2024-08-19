package com.revature.DAOs;

import com.revature.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelDAO extends JpaRepository<Hotel, Long> {

    // Correct return type to Optional<Hotel> since it is by ID
    Optional<Hotel> getHotelById(Long hotelId);

    List<Hotel> findByHotelNameContainingIgnoreCase(String hotelName);
    List<Hotel> findByAddressContainingIgnoreCase(String address);


    // add custom methods WIP
}
