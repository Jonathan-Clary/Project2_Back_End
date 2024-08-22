package com.revature.DAOs;

import com.revature.models.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayDAO extends JpaRepository<Stay, Integer> {
    //STAYS-HISTORY: DAO method for getting list of stays by userId
    List<Stay> findByUserUserId(int userId);
}
