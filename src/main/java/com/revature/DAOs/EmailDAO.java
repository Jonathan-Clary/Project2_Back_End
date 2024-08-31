package com.revature.DAOs;

import com.revature.models.Email;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailDAO extends JpaRepository<Email, UUID> {

    Optional<Email> findByEmail(String email);

}
