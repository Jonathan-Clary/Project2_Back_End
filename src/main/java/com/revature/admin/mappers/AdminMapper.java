package com.revature.admin.mappers;

import com.revature.admin.models.Admin;
import com.revature.admin.models.Note;
import com.revature.admin.DTOs.OutgoingAdminDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class AdminMapper {

    public OutgoingAdminDTO toDto(Admin admin) {
        String lastName = admin.getLastName();
        String email = admin.getEmail();

        List<String> notes = admin.getNotes().stream().map(Note::getText).collect(toList());

        return new OutgoingAdminDTO(lastName, email, notes);
    }

}
