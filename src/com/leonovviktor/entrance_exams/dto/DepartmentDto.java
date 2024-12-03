package com.leonovviktor.entrance_exams.dto;

import com.leonovviktor.entrance_exams.entity.Exam;
public class DepartmentDto {
    private final Long id;
    private final String name;
    private final Exam firstExam;
    private final Exam secondExam;
    private final Exam thirdExam;

    public DepartmentDto(Long id, String name, Exam firstExam, Exam secondExam, Exam thirdExam) {
        this.id = id;
        this.name = name;
        this.firstExam = firstExam;
        this.secondExam = secondExam;
        this.thirdExam = thirdExam;
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

    @Override
    public String toString() {
        return "DepartmentDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", firstExam=" + firstExam +
               ", secondExam=" + secondExam +
               ", thirdExam=" + thirdExam +
               '}';
    }
}

