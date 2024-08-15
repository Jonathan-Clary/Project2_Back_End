package com.revature.DAOs;

import com.revature.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteDAO extends JpaRepository<Favorite, Integer> {
    public List<Favorite> findByUserUserId(int userId);

    public List<Favorite> findByHotelHotelId(int hotelId);

}
