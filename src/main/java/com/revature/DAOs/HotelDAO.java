package com.revature.DAOs;

import com.revature.models.Favorite;
import com.revature.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelDAO extends JpaRepository<Hotel, UUID> {

    public Optional<Hotel> findByPlaceId(String placeId);
    // add custom methods WIP



}
