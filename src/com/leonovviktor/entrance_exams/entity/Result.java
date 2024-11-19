package com.leonovviktor.entrance_exams.entity;

public class Result {
    private Long id;
    private Professor professor;
    private Applicant applicant;
    private Exam exam;
    private Note note;

    public Result(Long id, Professor professor, Applicant applicant, Exam exam, Note note) {
        this.id = id;
        this.professor = professor;
        this.applicant = applicant;
        this.exam = exam;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Exam getExam() {
        return exam;
    }

    public Note getNote() {
        return note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Result{" +
               "id=" + id +
               ", professor=" + professor +
               ", applicant=" + applicant +
               ", exam=" + exam +
               ", note=" + note +
               '}';
    }
}
