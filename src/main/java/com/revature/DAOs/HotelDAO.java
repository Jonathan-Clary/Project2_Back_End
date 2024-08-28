package com.revature.DAOs;

import com.revature.models.LocalHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelDAO extends JpaRepository<LocalHotel, UUID> {

    Optional<LocalHotel> findByHotelId(UUID hotelId);
    Optional<LocalHotel> findByApiHotelId(String apiHotelId);

}
