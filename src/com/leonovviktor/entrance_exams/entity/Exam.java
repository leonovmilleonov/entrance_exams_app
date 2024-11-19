package com.leonovviktor.entrance_exams.entity;

public class Exam {
    private Long id;
    private String examType;

    public Exam(Long id, String examType) {
        this.id = id;
        this.examType = examType;
    }

    public Long getId() {
        return id;
    }

    public String getExamType() {
        return examType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @Override
    public String toString() {
        return "Exam{" +
               "id=" + id +
               ", examType=" + examType +
               '}';
    }
}
