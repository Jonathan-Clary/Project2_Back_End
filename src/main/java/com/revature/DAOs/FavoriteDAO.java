package com.revature.DAOs;

import com.revature.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoriteDAO extends JpaRepository<Favorite, UUID> {
    public List<Favorite> findByUserUserId(UUID userId);

    public List<Favorite> findByHotelHotelId(UUID hotelId);

}
