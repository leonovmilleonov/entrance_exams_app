package com.leonovviktor.entrance_exams.dao;


import com.leonovviktor.entrance_exams.entity.Exam;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamDao {
    private static final ExamDao INSTANCE = new ExamDao();
    private static final String FIND_ALL_SQL = """
            SELECT id,
            exam_type
            FROM exam;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id,
            exam_type
            FROM exam
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE exam
            SET exam_type = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM exam
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO exam(exam_type)
            VALUES (?);
            """;

    private ExamDao() {
    }

    public List<Exam> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Exam> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildExam(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Exam> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Exam> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Exam exam = null;
            if (resultSet.next()) {
                exam = buildExam(resultSet);
            }
            return Optional.ofNullable(exam);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Exam exam) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, exam.getExamType());
            preparedStatement.setLong(2, exam.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Exam save(Exam exam) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, exam.getExamType());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                exam.setId(generatedKeys.getLong("id"));
            }
            return exam;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Exam exam) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, exam.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static ExamDao getInstance() {
        return INSTANCE;
    }

    private Exam buildExam(ResultSet resultSet) throws SQLException {
        return new Exam(resultSet.getLong("id"),
                resultSet.getString("exam_type"));
    }

}
