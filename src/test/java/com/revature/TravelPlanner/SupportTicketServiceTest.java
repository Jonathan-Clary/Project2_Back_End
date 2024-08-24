package com.revature.TravelPlanner;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.admin.mappers.AdminOutgoingSupportTicketMapper;
import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.mappers.*;
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

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SupportTicketServiceTest {

    @Mock
    private SupportTicketDAO sDAO;

    @Mock
    private AdminDAO aDAO;

    @Mock
    private OutgoingSupportTicketMapper userMapper;

    @Mock
    private TypeMapper typeMapper;

    @Mock
    private StatusMapper statusMapper;

    @Mock
    private IncomingSupportTicketMapper incomingMapper;

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

        OutgoingSupportTicketDTO outgoingSupportTicketDTO = new OutgoingSupportTicketDTO();
        outgoingSupportTicketDTO.setSupportTicketId(supportTicket.getSupportTicketId());
        outgoingSupportTicketDTO.setDescription(supportTicket.getDescription());
        outgoingSupportTicketDTO.setUserId(user.getUserId());
        outgoingSupportTicketDTO.setFirstName(user.getFirstName());
        outgoingSupportTicketDTO.setLastName(user.getLastName());
        outgoingSupportTicketDTO.setEmail(user.getEmail());

        when(sDAO.findById(supportTicketId)).thenReturn(Optional.of(supportTicket));
        when(userMapper.toDto(supportTicket)).thenReturn(outgoingSupportTicketDTO);

        //when
        OutgoingSupportTicketDTO foundSupportTicketDTO = supportService.getSupportTicketById(supportTicketId);

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
        //given
        final Date fakeDateCreated = new Date(2024, Calendar.AUGUST,15);
        final int id1 = 1;
        final int id2 = 2;
        final int userId = 1;

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setDateCreated(fakeDateCreated.getTime());

        SupportTicket supportTicket1 = new SupportTicket();
        supportTicket1.setSupportTicketId(id1);
        supportTicket1.setUser(user);
        supportTicket1.setStatus(TicketStatus.PENDING);
        supportTicket1.setType(TicketType.GENERAL);
        supportTicket1.setDescription("Description");
        supportTicket1.setResolvedAt(0);

        SupportTicket supportTicket2 = new SupportTicket();
        supportTicket2.setSupportTicketId(id2);
        supportTicket2.setUser(user);
        supportTicket2.setStatus(TicketStatus.PENDING);
        supportTicket2.setType(TicketType.GENERAL);
        supportTicket2.setDescription("Description");
        supportTicket2.setCreatedAt(fakeDateCreated.getTime());
        supportTicket2.setResolvedAt(0);

        OutgoingSupportTicketDTO outgoingTicket1 = new OutgoingSupportTicketDTO();
        outgoingTicket1.setSupportTicketId(supportTicket1.getSupportTicketId());
        outgoingTicket1.setDescription(supportTicket1.getDescription());
        outgoingTicket1.setUserId(user.getUserId());
        outgoingTicket1.setFirstName(user.getFirstName());
        outgoingTicket1.setLastName(user.getLastName());
        outgoingTicket1.setEmail(user.getEmail());

        OutgoingSupportTicketDTO outgoingTicket2 = new OutgoingSupportTicketDTO();
        outgoingTicket2.setSupportTicketId(supportTicket2.getSupportTicketId());
        outgoingTicket2.setDescription(supportTicket2.getDescription());
        outgoingTicket2.setUserId(user.getUserId());
        outgoingTicket2.setFirstName(user.getFirstName());
        outgoingTicket2.setLastName(user.getLastName());
        outgoingTicket2.setEmail(user.getEmail());

        List<SupportTicket> ticketList = Arrays.asList(supportTicket1,supportTicket2);
        List<OutgoingSupportTicketDTO> outTicketList = Arrays.asList(outgoingTicket1, outgoingTicket2);

        when(sDAO.findAll()).thenReturn(ticketList);
        when(userMapper.toDto(supportTicket1)).thenReturn(outgoingTicket1);
        when(userMapper.toDto(supportTicket2)).thenReturn(outgoingTicket2);

        //when
        List<OutgoingSupportTicketDTO> supportTickets = supportService.getAllSupportTickets();

        //then
        assertEquals(outTicketList, supportTickets);
        verify(sDAO, times(1)).findAll();
        verify(userMapper, times(1)).toDto(supportTicket1);
        verify(userMapper, times(1)).toDto(supportTicket2);

    }


//    @Test
//    public void testFindAllSupportTicketsByAdmin() {
//
//
//
//    }

    @Test
    public void testRegister() throws Exception {
        //given
        final Date fakeDateCreated = new Date(2024,Calendar.AUGUST,26);
        final int userId = 1;
        final int supportTicketId = 1;

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setDateCreated(fakeDateCreated.getTime());

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setSupportTicketId(supportTicketId);
        supportTicket.setDescription("Description");
        supportTicket.setType(TicketType.GENERAL);
        supportTicket.setCreatedAt(fakeDateCreated.getTime());
        supportTicket.setResolvedAt(0);
        supportTicket.setStatus(TicketStatus.PENDING);
        supportTicket.setUser(user);

        IncomingSupportTicketDTO incomingSupportTicket = new IncomingSupportTicketDTO();
        incomingSupportTicket.setUserId(userId);
        incomingSupportTicket.setDescription(supportTicket.getDescription());
        incomingSupportTicket.setType("GENERAL");

        OutgoingSupportTicketDTO outgoingSupportTicketDTO = new OutgoingSupportTicketDTO();
        outgoingSupportTicketDTO.setSupportTicketId(supportTicket.getSupportTicketId());
        outgoingSupportTicketDTO.setDescription(supportTicket.getDescription());
        outgoingSupportTicketDTO.setUserId(user.getUserId());
        outgoingSupportTicketDTO.setFirstName(user.getFirstName());
        outgoingSupportTicketDTO.setLastName(user.getLastName());
        outgoingSupportTicketDTO.setEmail(user.getEmail());

        when(incomingMapper.toDto(incomingSupportTicket)).thenReturn(supportTicket);
        when(userMapper.toDto(supportTicket)).thenReturn(outgoingSupportTicketDTO);
        when(sDAO.save(supportTicket)).thenReturn(supportTicket);

        //when
        OutgoingSupportTicketDTO savedSupportTicket = supportService.register(incomingSupportTicket);

        //then
        assertEquals(outgoingSupportTicketDTO,savedSupportTicket);
        verify(incomingMapper, times(1)).toDto(incomingSupportTicket);
        verify(userMapper, times(1)).toDto(supportTicket);
        verify(sDAO, times(1)).save(supportTicket);

    }

//    @Test
//    public void testUpdateSupportTicket() {
//
//    }
//
//    @Test
//    public void testApproveSupportTicket() {
//
//    }
//
//    @Test
//    public void testDenySupportTicket() {
//
//    }
}
