package com.revature.admin.DAOs;

import com.revature.admin.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDAO extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String email);

}
