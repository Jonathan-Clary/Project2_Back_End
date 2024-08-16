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

    @Column(nullable = false, name = "text")
    private String text;

    @Column(nullable = false, name = "date_created")
    private Date dateCreated;

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

    public Note(int note_id, String text, Date date_created, Admin admin, SupportTicket supportTicket) {
        this.noteId = note_id;
        this.text = text;
        this.dateCreated = date_created;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate_created() {
        return dateCreated;
    }

    public void setDate_created(Date date_created) {
        this.dateCreated = date_created;
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
                "note_id=" + noteId +
                ", text='" + text + '\'' +
                ", date_created=" + dateCreated +
                ", admin=" + admin +
                ", support_ticket=" + supportTicket +
                '}';
    }

}