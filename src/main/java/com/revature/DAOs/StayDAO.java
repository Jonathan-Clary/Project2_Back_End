package com.revature.DAOs;

import com.revature.models.Stay;
import com.revature.models.LocalHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface StayDAO extends JpaRepository<Stay, Integer> {

    @Query("select hotel from Stay where bookedDate = :from and endDate = :to")
    List<LocalHotel> findByDuration(Date from, Date to);

}
