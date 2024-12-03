package com.leonovviktor.entrance_exams.service;

import com.leonovviktor.entrance_exams.dao.DepartmentDao;
import com.leonovviktor.entrance_exams.dto.DepartmentDto;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {
    private static final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private static final DepartmentService INSTANCE = new DepartmentService();

    private DepartmentService() {
    }

    public List<DepartmentDto> findAll() {
        return departmentDao.findAll().stream()
                .map(department -> new DepartmentDto(
                        department.getId(),
                        department.getName(),
                        department.getFirstExam(),
                        department.getSecondExam(),
                        department.getThirdExam()))
                .collect(Collectors.toList());
    }

    public static DepartmentService getInstance() {
        return INSTANCE;
    }
}
