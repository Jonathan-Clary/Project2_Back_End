package com.revature.DAOs;

import com.revature.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface NoteDAO extends JpaRepository<Note, Integer> {
    public List<Note> findAllByAdminAdminId(int admin_id);
}
