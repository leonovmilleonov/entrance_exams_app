package com.leonovviktor.entrance_exams.dao;

import com.leonovviktor.entrance_exams.entity.Professor;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDao {
    private static final ProfessorDao INSTANCE = new ProfessorDao();
    private static final String FIND_ALL_SQL = """
            SELECT id,
            first_name,
            last_name,
            email,
            password,
            examiner_of_exam_id
            FROM professor
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id,
            first_name,
            last_name,
            email,
            password,
            examiner_of_exam_id
            FROM professor
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE professor
            SET first_name = ?,
            last_name = ?,
            email = ?,
            password = ?,
            examiner_of_exam_id = ?
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO professor(first_name, last_name, email, password, examiner_of_exam_id)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM professor
            WHERE id = ?
            """;
    private static final ExamDao examDao = ExamDao.getInstance();

    private ProfessorDao() {
    }

    public List<Professor> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Professor> professors = new ArrayList<>();
            while (resultSet.next()) {
                professors.add(buildProfessor(resultSet));
            }
            return professors;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public Optional<Professor> findById(Long id){
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Professor> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Professor professor = null;
            if (resultSet.next()) {
                professor = buildProfessor(resultSet);
            }
            return Optional.ofNullable(professor);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Professor professor) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, professor.getFirstName());
            preparedStatement.setString(2, professor.getLastName());
            preparedStatement.setString(3, professor.getEmail());
            preparedStatement.setString(4, professor.getPassword());
            preparedStatement.setLong(5, professor.getExaminerOfExam().getId());
            preparedStatement.setLong(6, professor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Professor save(Professor professor) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, professor.getFirstName());
            preparedStatement.setString(2, professor.getLastName());
            preparedStatement.setString(3, professor.getEmail());
            preparedStatement.setString(4, professor.getPassword());
            preparedStatement.setLong(5, professor.getExaminerOfExam().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                professor.setId(generatedKeys.getLong("id"));
            }
            return professor;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Professor professor) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, professor.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static ProfessorDao getInstance() {
        return INSTANCE;
    }

    private Professor buildProfessor(ResultSet resultSet) throws SQLException {
        return new Professor(resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                examDao.findById(resultSet.getLong("examiner_of_exam_id"), resultSet.getStatement().getConnection()).orElse(null)
        );
    }
}
