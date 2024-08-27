package com.revature.DAOs;

import com.revature.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, UUID> {
}
