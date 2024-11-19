package com.leonovviktor.entrance_exams.dao;

import com.leonovviktor.entrance_exams.entity.Note;
import com.leonovviktor.entrance_exams.exception.DaoException;
import com.leonovviktor.entrance_exams.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteDao {
    private static final NoteDao INSTANCE = new NoteDao();
    private static final String FIND_ALL_SQL = """
            SELECT id,
            note_type
            FROM note;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id,
            note_type
            FROM note
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE note 
            SET note_type = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM note
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO note(note_type)
            VALUES (?)
            """;

    private NoteDao() {
    }

    public List<Note> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Note> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildNote(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Note> findById(Long id){
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Note> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Note note = null;
            if(resultSet.next()){
                note = buildNote(resultSet);
            }
            return Optional.ofNullable(note);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Note note) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, note.getNoteType());
            preparedStatement.setLong(2, note.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Note save(Note note) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, note.getNoteType());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                note.setId(generatedKeys.getLong("id"));
            }
            return note;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Note note) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, note.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static NoteDao getInstance() {
        return INSTANCE;
    }

    private Note buildNote(ResultSet resultSet) throws SQLException {
        return new Note(
                resultSet.getLong("id"),
                resultSet.getString("note_type")
        );
    }
}
