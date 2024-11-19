package com.leonovviktor.entrance_exams.dao;

import com.leonovviktor.entrance_exams.entity.Applicant;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicantDao {
    private static final ApplicantDao INSTANCE = new ApplicantDao();
    private static final String FIND_ALL_SQL = """
            SELECT id,
            first_name,
            last_name,
            department_id,
            email,
            password
            FROM applicant
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id,
            first_name,
            last_name,
            department_id,
            email,
            password
            FROM applicant
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE applicant
            SET first_name = ?,
            last_name = ?,
            department_id = ?,
            email = ?,
            password = ?
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO applicant(first_name, last_name, department_id, email, password) 
            VALUES (?, ?, ?, ?, ?) 
            """;
    private static final String DELETE_SQL = """
            DELETE FROM applicant
            WHERE id = ?
            """;
    private static final DepartmentDao departmentDao = DepartmentDao.getInstance();

    private ApplicantDao() {
    }

    public List<Applicant> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Applicant> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildApplicant(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Applicant> findById(Long id){
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Applicant> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            Applicant applicant = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                applicant = buildApplicant(resultSet);
            }
            return Optional.ofNullable(applicant);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Applicant applicant) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, applicant.getFirstName());
            preparedStatement.setString(2, applicant.getLastName());
            preparedStatement.setLong(3, applicant.getDepartment().getId());
            preparedStatement.setString(4, applicant.getEmail());
            preparedStatement.setString(5, applicant.getPassword());
            preparedStatement.setLong(6, applicant.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Applicant save(Applicant applicant) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, applicant.getFirstName());
            preparedStatement.setString(2, applicant.getLastName());
            preparedStatement.setLong(3, applicant.getDepartment().getId());
            preparedStatement.setString(4, applicant.getEmail());
            preparedStatement.setString(5, applicant.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                applicant.setId(generatedKeys.getLong("id"));
            }
            return applicant;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Applicant applicant) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, applicant.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static ApplicantDao getInstance() {
        return INSTANCE;
    }

    private Applicant buildApplicant(ResultSet resultSet) throws SQLException {
        return new Applicant(resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                departmentDao.findById(resultSet.getLong("department_id"), resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
