package com.leonovviktor.entrance_exams.entity;

public class Note {
    private Long id;
    private String noteType;

    public Note(Long id, String noteType) {
        this.id = id;
        this.noteType = noteType;
    }

    public Long getId() {
        return id;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    @Override
    public String toString() {
        return "Note{" +
               "id=" + id +
               ", noteType='" + noteType + '\'' +
               '}';
    }
}
