package com.leonovviktor.entrance_exams.entity;

public class ReceivedApplicant {
    private Applicant applicant;
    private Double averageScore;

    public ReceivedApplicant(Applicant applicant, Double averageScore) {
        this.applicant = applicant;
        this.averageScore = averageScore;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        return "ReceivedApplicant{" +
               "applicant=" + applicant +
               ", averageScore=" + averageScore +
               '}';
    }
}
