package com.revature.DAOs;

import com.revature.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteDAO extends JpaRepository<Favorite, Integer> {
    public List<Favorite> findByUserUser_id(int user_id);

    public List<Favorite> findByHotelHotel_id(int hotel_id);

}
