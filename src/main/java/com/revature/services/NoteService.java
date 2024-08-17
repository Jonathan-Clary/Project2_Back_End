package com.revature.services;

import com.revature.DAOs.NoteDAO;
import com.revature.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteDAO noteDAO;

    public NoteService(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    // Add methods as needed
}