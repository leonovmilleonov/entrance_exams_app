package com.leonovviktor.entrance_exams.dto;

import java.util.Objects;

public class ExamDto {
    private final Long id;
    private final String examType;

    public ExamDto(Long id, String examType) {
        this.id = id;
        this.examType = examType;
    }

    public Long getId() {
        return id;
    }

    public String getExamType() {
        return examType;
    }

    @Override
    public String toString() {
        return "ExamDto{" +
               "id=" + id +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamDto examDto = (ExamDto) o;
        return Objects.equals(id, examDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
