package com.leonovviktor.entrance_exams.dao;

import com.leonovviktor.entrance_exams.entity.Result;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultDao {
    private static final ResultDao INSTANCE = new ResultDao();
    private static final String FIND_ALL_SQL = """
            SELECT id,
            professor_id,
            applicant_id,
            exam_id,
            note_id
            FROM result;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id,
            professor_id,
            applicant_id,
            exam_id,
            note_id
            FROM result
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE result
            SET 
            professor_id = ?,
            applicant_id = ?,
            exam_id = ?,
            note_id = ?
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO result(professor_id, applicant_id, exam_id, note_id)
            VALUES (?, ?, ?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM result
            WHERE id = ?
            """;
    private static final ProfessorDao professorDao = ProfessorDao.getInstance();
    private static final ApplicantDao applicantDao = ApplicantDao.getInstance();
    private static final ExamDao examDao = ExamDao.getInstance();
    private static final NoteDao noteDao = NoteDao.getInstance();

    private ResultDao() {
    }

    public List<Result> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Result> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(buildResult(resultSet));
            }
            return results;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Result> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Result result = null;
            if (resultSet.next()) {
                result = buildResult(resultSet);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Result result) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, result.getProfessor().getId());
            preparedStatement.setLong(2, result.getApplicant().getId());
            preparedStatement.setLong(3, result.getExam().getId());
            preparedStatement.setLong(4, result.getNote().getId());
            preparedStatement.setLong(5, result.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Result save(Result result) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, result.getProfessor().getId());
            preparedStatement.setLong(2, result.getApplicant().getId());
            preparedStatement.setLong(3, result.getExam().getId());
            preparedStatement.setLong(4, result.getNote().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                result.setId(generatedKeys.getLong("id"));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Result result) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, result.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static ResultDao getInstance() {
        return INSTANCE;

    }

    private Result buildResult(ResultSet resultSet) throws SQLException {
        return new Result(resultSet.getLong("id"),
                professorDao.findById(resultSet.getLong("professor_id"), resultSet.getStatement().getConnection()).orElse(null),
                applicantDao.findById(resultSet.getLong("applicant_id"), resultSet.getStatement().getConnection()).orElse(null),
                examDao.findById(resultSet.getLong("exam_id"), resultSet.getStatement().getConnection()).orElse(null),
                noteDao.findById(resultSet.getLong("note_id"), resultSet.getStatement().getConnection()).orElse(null)
        );
    }
}
