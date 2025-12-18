package org.example.repository.DAO;

import org.example.repository.entities.TaskEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAOInterface<T> {
    // CRUD Operations
    // CREATE
    public Integer create(T entity) throws SQLException;
    // READ BY ID
    public Optional<T> findByID(Integer ID) throws SQLException;
    // READ ALL
    public List<T> findAll() throws SQLException;
    // UPDATE
    public T updateByID(T entity) throws SQLException;
    // DELETE
    public boolean deleteByID(Integer ID) throws SQLException;
}