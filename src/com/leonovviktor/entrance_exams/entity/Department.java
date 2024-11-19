package com.leonovviktor.entrance_exams.entity;

public class Department {
    private Long id;
    private String name;
    private Exam firstExam;
    private Exam secondExam;
    private Exam thirdExam;

    public Department(Long id, String name, Exam firstExamId, Exam secondExamId, Exam thirdExamId) {
        this.id = id;
        this.name = name;
        this.firstExam = firstExamId;
        this.secondExam = secondExamId;
        this.thirdExam = thirdExamId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Exam getFirstExam() {
        return firstExam;
    }

    public Exam getSecondExam() {
        return secondExam;
    }

    public Exam getThirdExam() {
        return thirdExam;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstExam(Exam firstExam) {
        this.firstExam = firstExam;
    }

    public void setSecondExam(Exam secondExam) {
        this.secondExam = secondExam;
    }

    public void setThirdExam(Exam thirdExam) {
        this.thirdExam = thirdExam;
    }

    @Override
    public String toString() {
        return "Department{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", firstExam=" + firstExam +
               ", secondExam=" + secondExam +
               ", thirdExam=" + thirdExam +
               '}';
    }
}
