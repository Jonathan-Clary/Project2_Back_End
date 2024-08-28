package com.revature.TravelPlanner;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.exceptions.InvalidDescriptionException;
import com.revature.exceptions.InvalidTypeException;
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
    private UserDAO uDao;

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
    public void testGetSupportTicketById() throws Exception{
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
    public void testSupportTicketNotFoundGetById() {
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
    public void testGetAllSupportTicketsByUserId() throws Exception {
        //given
        final Date fakeDateCreated = new Date(2024,Calendar.AUGUST,26);
        final UUID userId = UUID.randomUUID();
        final UUID supportTicketId1 = UUID.randomUUID();
        final UUID supportTicketId2 = UUID.randomUUID();

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("password");
        user.setCreatedAt(fakeDateCreated);

        SupportTicket supportTicket1 = new SupportTicket();
        supportTicket1.setSupportTicketId(supportTicketId1);
        supportTicket1.setDescription("Description");
        supportTicket1.setType(TicketType.GENERAL);
        supportTicket1.setCreatedAt(fakeDateCreated);
        supportTicket1.setResolvedAt(null);
        supportTicket1.setStatus(TicketStatus.PENDING);
        supportTicket1.setUser(user);

        SupportTicket supportTicket2 = new SupportTicket();
        supportTicket2.setSupportTicketId(supportTicketId2);
        supportTicket2.setDescription("Description");
        supportTicket2.setType(TicketType.GENERAL);
        supportTicket2.setCreatedAt(fakeDateCreated);
        supportTicket2.setResolvedAt(null);
        supportTicket2.setStatus(TicketStatus.PENDING);
        supportTicket2.setUser(user);

        OutgoingSupportTicketDTO outgoingTicket1 = new OutgoingSupportTicketDTO();
        outgoingTicket1.setSupportTicketId(supportTicketId1);
        outgoingTicket1.setUserId(userId);
        outgoingTicket1.setFirstName(user.getFirstName());
        outgoingTicket1.setLastName(user.getLastName());
        outgoingTicket1.setEmail(user.getEmail());
        outgoingTicket1.setDescription(supportTicket1.getDescription());
        outgoingTicket1.setStatus(supportTicket1.getStatus());
        outgoingTicket1.setType(supportTicket1.getType());
        outgoingTicket1.setCreatedAt(supportTicket1.getCreatedAt());

        OutgoingSupportTicketDTO outgoingTicket2 = new OutgoingSupportTicketDTO();
        outgoingTicket2.setSupportTicketId(supportTicketId1);
        outgoingTicket2.setUserId(userId);
        outgoingTicket2.setFirstName(user.getFirstName());
        outgoingTicket2.setLastName(user.getLastName());
        outgoingTicket2.setEmail(user.getEmail());
        outgoingTicket2.setDescription(supportTicket2.getDescription());
        outgoingTicket2.setStatus(supportTicket2.getStatus());
        outgoingTicket2.setType(supportTicket2.getType());
        outgoingTicket2.setCreatedAt(supportTicket2.getCreatedAt());

        List<SupportTicket> supportTickets = new ArrayList<>();
        supportTickets.add(supportTicket1);
        supportTickets.add(supportTicket2);

        List<OutgoingSupportTicketDTO> outgoingList = new ArrayList<OutgoingSupportTicketDTO>();
        outgoingList.add(outgoingTicket1);
        outgoingList.add(outgoingTicket2);

        when(uDao.findById(userId)).thenReturn(Optional.of(user));
        when(sDAO.findAllByUserUserId(userId)).thenReturn(supportTickets);
        when(userMapper.toDto(supportTicket1)).thenReturn(outgoingTicket1);
        when(userMapper.toDto(supportTicket2)).thenReturn(outgoingTicket2);

        //when
        List<OutgoingSupportTicketDTO> returnedList = supportService.getAllSupportTicketsByUserId(userId);

        //then
        assertEquals(outgoingList, returnedList);
        verify(uDao, times(1)).findById(userId);
        verify(sDAO, times(1)).findAllByUserUserId(userId);
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

    @Test
    public void testInvalidType() throws Exception {
        //given
        final UUID userId = UUID.randomUUID();
        final String type = "invalid";

        IncomingSupportTicketDTO incomingTicket = new IncomingSupportTicketDTO(userId, "Description", type);

        when(incomingMapper.toDto(incomingTicket)).thenThrow(new InvalidTypeException(type));

        //when
        InvalidTypeException thrown = assertThrows(
                InvalidTypeException.class, () -> supportService.register(incomingTicket)
        );

        //then
        assertTrue(thrown.getMessage().contains(type + " is an Invalid Type."));
    }

    @Test
    public void testDelete() throws Exception {
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
        OutgoingSupportTicketDTO deletedTicket = supportService.delete(supportTicketId);

        //then
        assertEquals(outgoingSupportTicketDTO,deletedTicket);
        verify(sDAO, times(1)).findById(supportTicketId);
        verify(userMapper, times(2)).toDto(supportTicket);

    }

    @Test
    public void testSupportTicketNotFoundDelete(){
        //given
        final UUID id = UUID.randomUUID();

        when(sDAO.findById(id)).thenReturn(Optional.empty());

        //when
        SupportTicketNotFoundException thrown = assertThrows(
                SupportTicketNotFoundException.class, () -> supportService.delete(id)
        );

        //then
        assertTrue(thrown.getMessage().contains("Support Ticket with Id: " + id + " Not Found."));
        verify(sDAO, times(1)).findById(id);

    }

}//End of SupportServiceTest
