package com.revature.DAOs;

import com.revature.models.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTicketDAO extends JpaRepository<SupportTicket, Integer> {
}
