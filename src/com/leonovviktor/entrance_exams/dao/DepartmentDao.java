package com.leonovviktor.entrance_exams.dao;

import com.leonovviktor.entrance_exams.entity.Department;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDao {
    private static final DepartmentDao INSTANCE = new DepartmentDao();
    private static final ExamDao examDao = ExamDao.getInstance();
    private static final String FIND_ALL_SQL = """
            SELECT id,
            name,
            first_exam_id,
            second_exam_id,
            third_exam_id
            FROM department
            """;
    private static final String FIND_BY_ID = """
            SELECT id,
            name,
            first_exam_id,
            second_exam_id,
            third_exam_id
            FROM department
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE department
            SET name = ?,
            first_exam_id = ?,
            second_exam_id = ?,
            third_exam_id = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM department
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO department(name, first_exam_id, second_exam_id, third_exam_id) 
            VALUES (?, ?, ?, ?)
            """;

    private DepartmentDao() {
    }

    public List<Department> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Department> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildDepartment(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public Optional<Department> findById(Long id){
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public Optional<Department> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Department department = null;
            if (resultSet.next()) {
                department = buildDepartment(resultSet);
            }
            return Optional.ofNullable(department);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Department department) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.setLong(2, department.getFirstExam().getId());
            preparedStatement.setLong(3, department.getSecondExam().getId());
            preparedStatement.setLong(4, department.getThirdExam().getId());
            preparedStatement.setLong(5, department.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Department save(Department department) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.setLong(2, department.getSecondExam().getId());
            preparedStatement.setLong(3, department.getSecondExam().getId());
            preparedStatement.setLong(4, department.getThirdExam().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                department.setId(generatedKeys.getLong("id"));
            }
            return department;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Department department) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, department.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public static DepartmentDao getInstance() {
        return INSTANCE;
    }

    private Department buildDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                examDao.findById(resultSet.getLong("first_exam_id"),
                        resultSet.getStatement().getConnection()).orElse(null),
                examDao.findById(resultSet.getLong("second_exam_id"),
                        resultSet.getStatement().getConnection()).orElse(null),
                examDao.findById(resultSet.getLong("third_exam_id"),
                        resultSet.getStatement().getConnection()).orElse(null)
        );
    }
}
