package com.leonovviktor.entrance_exams.dao;

import com.leonovviktor.entrance_exams.entity.ReceivedApplicant;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceivedApplicantDao {
    private static final ReceivedApplicantDao INSTANCE = new ReceivedApplicantDao();
    private static final String FIND_ALL_SQL = """
            SELECT applicant_id,
            average_score
            FROM received_applicant
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT applicant_id,
            average_score
            FROM received_applicant
            WHERE applicant_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE received_applicant
            SET average_score = ?
            WHERE applicant_id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO received_applicant(applicant_id, average_score) 
            VALUES (?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM received_applicant
            WHERE applicant_id = ?
            """;
    private static final ApplicantDao applicantDao = ApplicantDao.getInstance();

    private ReceivedApplicantDao() {
    }

    public List<ReceivedApplicant> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ReceivedApplicant> receivedApplicants = new ArrayList<>();
            while (resultSet.next()) {
                receivedApplicants.add(buildReceivedApplicant(resultSet));
            }
            return receivedApplicants;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<ReceivedApplicant> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ReceivedApplicant result = null;
            if (resultSet.next()) {
                result = buildReceivedApplicant(resultSet);
            }
            return Optional.ofNullable(result);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(ReceivedApplicant receivedApplicant) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setDouble(1, receivedApplicant.getAverageScore());
            preparedStatement.setLong(2, receivedApplicant.getApplicant().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public ReceivedApplicant save(ReceivedApplicant receivedApplicant) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setLong(1, receivedApplicant.getApplicant().getId());
            preparedStatement.setDouble(2, receivedApplicant.getAverageScore());
            preparedStatement.executeUpdate();
            return receivedApplicant;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(ReceivedApplicant receivedApplicant) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, receivedApplicant.getApplicant().getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static ReceivedApplicantDao getInstance() {
        return INSTANCE;
    }

    private ReceivedApplicant buildReceivedApplicant(ResultSet resultSet) {
        return null;
    }
}
