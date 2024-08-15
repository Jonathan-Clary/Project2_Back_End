package com.revature.DAOs;

import com.revature.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface NoteDAO extends JpaRepository<Note, Integer> {
    public List<Note> findAllByAdminAdminId(int admin_id);
}
