package com.revature.DAOs;

import com.revature.models.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayDAO extends JpaRepository<Stay, Integer> {


}
