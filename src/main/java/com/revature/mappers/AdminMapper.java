package com.revature.mappers;

import com.revature.models.Admin;
import com.revature.models.Note;
import com.revature.DTOs.OutgoingAdminDTO;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AdminMapper {
    public OutgoingAdminDTO toDto(Admin admin) {
        String lastName = admin.getLastName();
        String email = admin.getEmail();

        List<String> notes = admin.getNotes().stream().map(Note::getText).collect(toList());

        return new OutgoingAdminDTO(lastName, email, notes);
    }

}
