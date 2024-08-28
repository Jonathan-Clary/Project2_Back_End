package com.revature.TravelPlanner;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.exceptions.InvalidDescriptionException;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.exceptions.UserNotFoundException;
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
        final UUID userId = UUID.randomUUID();
        final UUID supportTicketId = UUID.randomUUID();

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setCreatedAt(fakeDateCreated);

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setSupportTicketId(supportTicketId);
        supportTicket.setUser(user);
        supportTicket.setStatus(TicketStatus.PENDING);
        supportTicket.setType(TicketType.GENERAL);
        supportTicket.setDescription("Description");
        supportTicket.setCreatedAt(fakeDateCreated);
        supportTicket.setResolvedAt(null);

        OutgoingSupportTicketDTO outgoingSupportTicketDTO = new OutgoingSupportTicketDTO();
        outgoingSupportTicketDTO.setSupportTicketId(supportTicketId);
        outgoingSupportTicketDTO.setUserId(userId);
        outgoingSupportTicketDTO.setFirstName(user.getFirstName());
        outgoingSupportTicketDTO.setLastName(user.getLastName());
        outgoingSupportTicketDTO.setEmail(user.getEmail());
        outgoingSupportTicketDTO.setDescription(supportTicket.getDescription());
        outgoingSupportTicketDTO.setStatus(supportTicket.getStatus());
        outgoingSupportTicketDTO.setType(supportTicket.getType());
        outgoingSupportTicketDTO.setCreatedAt(supportTicket.getCreatedAt());

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
        final UUID supportTicketId = UUID.randomUUID();

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
        final UUID id1 = UUID.randomUUID();
        final UUID id2 = UUID.randomUUID();
        final UUID userId = UUID.randomUUID();

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setCreatedAt(fakeDateCreated);

        SupportTicket supportTicket1 = new SupportTicket();
        supportTicket1.setSupportTicketId(id1);
        supportTicket1.setUser(user);
        supportTicket1.setStatus(TicketStatus.PENDING);
        supportTicket1.setType(TicketType.GENERAL);
        supportTicket1.setDescription("Description");
        supportTicket1.setResolvedAt(null);

        SupportTicket supportTicket2 = new SupportTicket();
        supportTicket2.setSupportTicketId(id2);
        supportTicket2.setUser(user);
        supportTicket2.setStatus(TicketStatus.PENDING);
        supportTicket2.setType(TicketType.GENERAL);
        supportTicket2.setDescription("Description");
        supportTicket2.setCreatedAt(fakeDateCreated);
        supportTicket2.setResolvedAt(null);

        OutgoingSupportTicketDTO outgoingTicketDTO1 = new OutgoingSupportTicketDTO();
        outgoingTicketDTO1.setSupportTicketId(id1);
        outgoingTicketDTO1.setUserId(userId);
        outgoingTicketDTO1.setFirstName(user.getFirstName());
        outgoingTicketDTO1.setLastName(user.getLastName());
        outgoingTicketDTO1.setEmail(user.getEmail());
        outgoingTicketDTO1.setDescription(supportTicket1.getDescription());
        outgoingTicketDTO1.setStatus(supportTicket1.getStatus());
        outgoingTicketDTO1.setType(supportTicket1.getType());
        outgoingTicketDTO1.setCreatedAt(supportTicket1.getCreatedAt());

        OutgoingSupportTicketDTO outgoingTicketDTO2 = new OutgoingSupportTicketDTO();
        outgoingTicketDTO2.setSupportTicketId(id2);
        outgoingTicketDTO2.setUserId(userId);
        outgoingTicketDTO2.setFirstName(user.getFirstName());
        outgoingTicketDTO2.setLastName(user.getLastName());
        outgoingTicketDTO2.setEmail(user.getEmail());
        outgoingTicketDTO2.setDescription(supportTicket2.getDescription());
        outgoingTicketDTO2.setStatus(supportTicket2.getStatus());
        outgoingTicketDTO2.setType(supportTicket2.getType());
        outgoingTicketDTO2.setCreatedAt(supportTicket2.getCreatedAt());

        List<SupportTicket> ticketList = Arrays.asList(supportTicket1,supportTicket2);
        List<OutgoingSupportTicketDTO> outTicketList = Arrays.asList(outgoingTicketDTO1, outgoingTicketDTO2);

        when(sDAO.findAll()).thenReturn(ticketList);
        when(userMapper.toDto(supportTicket1)).thenReturn(outgoingTicketDTO1);
        when(userMapper.toDto(supportTicket2)).thenReturn(outgoingTicketDTO2);

        //when
        List<OutgoingSupportTicketDTO> supportTickets = supportService.getAllSupportTickets();

        //then
        assertEquals(outTicketList, supportTickets);
        verify(sDAO, times(1)).findAll();
        verify(userMapper, times(1)).toDto(supportTicket1);
        verify(userMapper, times(1)).toDto(supportTicket2);

    }

    @Test
    public void testRegister() throws Exception {
        //given
        final Date fakeDateCreated = new Date(2024,Calendar.AUGUST,26);
        final UUID userId = UUID.randomUUID();
        final UUID supportTicketId = UUID.randomUUID();

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setCreatedAt(fakeDateCreated);

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setSupportTicketId(supportTicketId);
        supportTicket.setDescription("Description");
        supportTicket.setType(TicketType.GENERAL);
        supportTicket.setCreatedAt(fakeDateCreated);
        supportTicket.setResolvedAt(null);
        supportTicket.setStatus(TicketStatus.PENDING);
        supportTicket.setUser(user);

        IncomingSupportTicketDTO incomingSupportTicket = new IncomingSupportTicketDTO();
        incomingSupportTicket.setUserId(userId);
        incomingSupportTicket.setDescription(supportTicket.getDescription());
        incomingSupportTicket.setType("GENERAL");

        OutgoingSupportTicketDTO outgoingSupportTicketDTO = new OutgoingSupportTicketDTO();
        outgoingSupportTicketDTO.setSupportTicketId(supportTicketId);
        outgoingSupportTicketDTO.setUserId(userId);
        outgoingSupportTicketDTO.setFirstName(user.getFirstName());
        outgoingSupportTicketDTO.setLastName(user.getLastName());
        outgoingSupportTicketDTO.setEmail(user.getEmail());
        outgoingSupportTicketDTO.setDescription(supportTicket.getDescription());
        outgoingSupportTicketDTO.setStatus(supportTicket.getStatus());
        outgoingSupportTicketDTO.setType(supportTicket.getType());
        outgoingSupportTicketDTO.setCreatedAt(supportTicket.getCreatedAt());

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

    @Test
    public void testInvalidDescription() {
        //given
        final UUID userId = UUID.randomUUID();

        IncomingSupportTicketDTO incomingTicket = new IncomingSupportTicketDTO(userId, "", "GENERAL");

        //when
        InvalidDescriptionException thrown = assertThrows(
                InvalidDescriptionException.class, () -> supportService.register(incomingTicket)
        );

        //then
        assertTrue(thrown.getMessage().contains("Please Input an Nonempty Description"));

    }

    @Test
    public void testUserNotFound() {
        //given
        IncomingSupportTicketDTO incomingTicket = new IncomingSupportTicketDTO(null, "Description", "General");

        //when
        UserNotFoundException thrown = assertThrows(
                UserNotFoundException.class, () -> supportService.register(incomingTicket)
        );

        //then
        assertTrue(thrown.getMessage().contains("User with ID:"+ null +" Not Found."));

    }

}//End of SupportServiceTest
