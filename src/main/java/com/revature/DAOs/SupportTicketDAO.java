package com.revature.DAOs;

import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.enums.TicketType;
import com.revature.models.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupportTicketDAO extends JpaRepository<SupportTicket, UUID> {
}
