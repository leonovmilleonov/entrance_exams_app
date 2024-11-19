package com.leonovviktor.entrance_exams.entity;

public class Professor {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Exam examinerOfExam;

    public Professor(Long id, String firstName, String lastName, String email, String password, Exam examinerOfExamId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.examinerOfExam = examinerOfExamId;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Exam getExaminerOfExam() {
        return examinerOfExam;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExaminerOfExam(Exam examinerOfExam) {
        this.examinerOfExam = examinerOfExam;
    }

    @Override
    public String toString() {
        return "Professor{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", examinerOfExam=" + examinerOfExam +
               '}';
    }
}
