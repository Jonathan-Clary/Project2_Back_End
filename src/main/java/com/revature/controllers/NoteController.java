package com.revature.controllers;

import com.revature.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@CrossOrigin
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Handle more HTTP requests
}