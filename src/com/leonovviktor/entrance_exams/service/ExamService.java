package com.leonovviktor.entrance_exams.service;

import com.leonovviktor.entrance_exams.dao.ExamDao;
import com.leonovviktor.entrance_exams.dto.ExamDto;
import com.leonovviktor.entrance_exams.entity.Exam;

import java.util.Optional;

public class ExamService {
    private static final ExamDao examDao = ExamDao.getInstance();
    private static final ExamService INSTANCE = new ExamService();

    private ExamService() {
    }

    public ExamDto findById(Long id){
        Optional<Exam> temp = examDao.findById(id);
        ExamDto result = null;
        if(temp.isPresent()){
            Exam exam = temp.get();
            result = new ExamDto(exam.getId(), exam.getExamType());
        }
        return result;
    }

    public static ExamService getInstance(){
        return INSTANCE;
    }
}
