package com.leonovviktor.entrance_exams.service;

import com.leonovviktor.entrance_exams.dao.ResultDao;
import com.leonovviktor.entrance_exams.dto.ExamDto;
import com.leonovviktor.entrance_exams.dto.ResultDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ResultService {
    private static final ResultDao resultDao = ResultDao.getInstance();
    private static final ResultService INSTANCE = new ResultService();

    ResultService() {
    }

    public List<ResultDto> findAll() {
        return resultDao.findAll().stream().map(
                result -> new ResultDto(
                        result.getId(),
                        result.getProfessor().getFirstName() + " " + result.getProfessor().getLastName(),
                        result.getApplicant().getId(),
                        result.getExam().getId(),
                        result.getExam().getExamType(),
                        result.getNote().getNoteType()
                )
        ).collect(Collectors.toList());
    }

    public List<ResultDto> findByExamId(Long examId) {
        return findAll().stream().filter(result -> Objects.equals(result.getExamId(), examId))
                .collect(Collectors.toList());
    }

    public static ResultService getInstance() {
        return INSTANCE;
    }
}
