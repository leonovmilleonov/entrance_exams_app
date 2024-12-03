package com.leonovviktor.entrance_exams.dto;


import java.util.Objects;

public class ResultDto {
    private final Long id;
    private final String professorName;
    private final Long applicantId;
    private final Long examId;
    private final String examType;
    private final String noteType;

    public ResultDto(Long id, String professorName, Long applicantId, Long examId, String examType, String noteType) {
        this.id = id;
        this.professorName = professorName;
        this.applicantId = applicantId;
        this.examId = examId;
        this.examType = examType;
        this.noteType = noteType;
    }

    public Long getId() {
        return id;
    }

    public String getProfessorName() {
        return professorName;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public Long getExamId() {
        return examId;
    }

    public String getExamType() {
        return examType;
    }

    public String getNoteType() {
        return noteType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDto resultDto = (ResultDto) o;
        return Objects.equals(id, resultDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
