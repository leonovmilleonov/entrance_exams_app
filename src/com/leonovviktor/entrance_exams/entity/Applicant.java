package com.leonovviktor.entrance_exams.entity;

public class Applicant {
    private Long id;
    private String firstName;
    private String lastName;
    private Department department;
    private String email;
    private String password;

    public Applicant(Long id, String firstName, String lastName, Department departmentId, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = departmentId;
        this.email = email;
        this.password = password;
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

    public Department getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Applicant{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", department=" + department +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
