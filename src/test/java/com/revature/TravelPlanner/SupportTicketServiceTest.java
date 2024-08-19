package com.revature.TravelPlanner;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.NoteDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.mappers.AdminOutgoingSupportTicketMapper;
import com.revature.mappers.UserOutgoingSupportTicketMapper;
import com.revature.models.SupportTicket;
import com.revature.models.User;
import com.revature.services.SupportTicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SupportTicketServiceTest {

    @Mock
    private SupportTicketDAO sDAO;

    @Mock
    private AdminDAO aDAO;

    @Mock
    private NoteDAO nDAO;

    @Mock
    private UserOutgoingSupportTicketMapper userMapper;

    @Mock
    private AdminOutgoingSupportTicketMapper adminMapper;

    @InjectMocks
    private SupportTicketService supportService;

    @Test
    public void testGetSupportTicketByID() throws Exception{
        //given
        final Date fakeDateCreated = new Date(2024, Calendar.AUGUST,15);
        final int supportTicketId = 1;
        final int userId = 1;

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setDateCreated(fakeDateCreated.getTime());  //TODO::Fix when/if set to long

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setSupportTicketId(supportTicketId);
        supportTicket.setUser(user);
        supportTicket.setStatus(TicketStatus.PENDING);
        supportTicket.setType(TicketType.GENERAL);
        supportTicket.setDescription("Description");
        supportTicket.setCreatedAt(fakeDateCreated.getTime());
        supportTicket.setResolvedAt(0);

        UserOutgoingSupportTicketDTO outgoingSupportTicketDTO = new UserOutgoingSupportTicketDTO();
        outgoingSupportTicketDTO.setSupportTicketId(supportTicket.getSupportTicketId());
        outgoingSupportTicketDTO.setDescription(supportTicket.getDescription());
        outgoingSupportTicketDTO.setUserId(user.getUserId());
        outgoingSupportTicketDTO.setFirstName(user.getFirstName());
        outgoingSupportTicketDTO.setLastName(user.getLastName());
        outgoingSupportTicketDTO.setEmail(user.getEmail());

        when(sDAO.findById(supportTicketId)).thenReturn(Optional.of(supportTicket));
        when(userMapper.toDto(supportTicket)).thenReturn(outgoingSupportTicketDTO);

        //when
        UserOutgoingSupportTicketDTO foundSupportTicketDTO = supportService.getSupportTicketById(supportTicketId);

        //then
        assertEquals(outgoingSupportTicketDTO, foundSupportTicketDTO);
        verify(sDAO, times(1)).findById(supportTicketId);
        verify(userMapper, times(1)).toDto(supportTicket);

    }

    @Test
    public void testSupportTicketNotFound() {
        //given
        final int supportTicketId = 1;

        when(sDAO.findById(supportTicketId)).thenReturn(Optional.empty());

        //when
        SupportTicketNotFoundException thrown = assertThrows(
                SupportTicketNotFoundException.class, () -> supportService.getSupportTicketById(supportTicketId)
        );

        //then
        assertTrue(thrown.getMessage().contains("Support Ticket with Id: " + supportTicketId + " Not Found."));
        verify(sDAO, times(1)).findById(supportTicketId);

    }

    @Test
    public void getAllSupportTickets() {

    }


    @Test
    public void testFindAllSupportTicketsByAdmin() {



    }

    @Test
    public void testRegisterSupportTicket() {
//        //given
//        final Date fakeDateCreated = new Date(2024,Calendar.AUGUST,26);
//        final int userId = 1;
//        final int supportTicketId = 1;
//
//        User user = new User();
//        user.setUserId(userId);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setEmail("JohnDoe@example.com");
//        user.setPassword("password");
//        user.setDateCreated(fakeDateCreated.getTime());
//
//        SupportTicket supportTicket = new SupportTicket();
//        supportTicket.setSupportTicketId(supportTicketId);
//        supportTicket.setDescription("Description");
//        supportTicket.setType(Type.GENERAL);
//        supportTicket.setCreatedAt(fakeDateCreated.getTime());
//        supportTicket.setResolvedAt(0);
//        supportTicket.setStatus(Status.PENDING);
//        supportTicket.setUser(user);
//
//        when(sDAO.save(supportTicket)).thenReturn(supportTicket);
//
//        //when
//        SupportTicket savedSupportTicket = null;
//
//        //then
//        assertEquals(supportTicket,savedSupportTicket);
//        verify(supportService, times(1)).registerSupportTicket(incomingSupportTicket);

    }

    @Test
    public void testUpdateSupportTicket() {

    }

    @Test
    public void testApproveSupportTicket() {

    }

    @Test
    public void testDenySupportTicket() {

    }
}
