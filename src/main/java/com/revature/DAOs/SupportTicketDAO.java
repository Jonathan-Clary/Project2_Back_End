package com.revature.DAOs;

import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.enums.TicketType;
import com.revature.models.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketDAO extends JpaRepository<SupportTicket, Integer> {

    List<UserOutgoingSupportTicketDTO> findAllByType(TicketType type);

}
