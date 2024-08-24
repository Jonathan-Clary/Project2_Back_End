package com.revature.models;

import jakarta.persistence.*;

import java.util.Date;

// Refactored model class name from 'Notes' to 'Note' to align with naming conventions and industry standards.
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    @JoinColumn(name = "admin_id")
    // This causing an error ('com.revature.models.Note.admin' is not a collection), I have to fix it
    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Admin admin;

    @JoinColumn(name = "supportTicketId")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SupportTicket supportTicket;

    public Note() {
    }

    public Note(int noteId, Admin admin, SupportTicket supportTicket) {
        this.noteId = noteId;

        this.admin = admin;
        this.supportTicket = supportTicket;
    }

    // Getters and setters
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public SupportTicket getSupportTicket() {
        return supportTicket;
    }

    public void setSupportTicket(SupportTicket supportTicket) {
        this.supportTicket = supportTicket;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", admin=" + admin +
                ", supportTicket=" + supportTicket +
                '}';
    }

}