package com.revature.admin.services;

import com.revature.admin.DAOs.NoteDAO;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private NoteDAO noteDAO;

    public NoteService(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    // Add methods as needed
}