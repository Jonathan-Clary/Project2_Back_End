package com.revature.DAOs;

import com.revature.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelDAO extends JpaRepository<Hotel, Long> {


    Optional<Hotel> findByHotelId(Long hotelId);

    List<Hotel> findByHotelNameContainingIgnoreCase(String hotelName);

    List<Hotel> findByAddressContainingIgnoreCase(String address);

    // Add any custom methods as needed
}
