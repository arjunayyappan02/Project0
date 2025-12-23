package org.example.repository.DAO;

import org.example.repository.entities.TaskEntity;
import org.example.util.ConnectionHandler;
import org.postgresql.core.ConnectionFactory;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

public class TaskDAO implements DAOInterface<TaskEntity> {

    private Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(TaskEntity taskEntity) throws SQLException {

        String sql = "INSERT INTO tasks (name, status, dueDate, priority) VALUES (?, ?, ?, ?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, taskEntity.getName());
            stmt.setInt(2, taskEntity.getStatus());
            stmt.setObject(3, taskEntity.getDueDate());
            stmt.setInt(4, taskEntity.getPriority());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    @Override
    public Optional<TaskEntity> findByID(Integer id) throws SQLException{
        String sql = "SELECT * FROM tasks WHERE ID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    TaskEntity taskEntity = new TaskEntity();
                    taskEntity.setID(rs.getInt("id"));
                    taskEntity.setName(rs.getString("name"));
                    taskEntity.setStatus(rs.getInt("status"));
                    taskEntity.setDueDate(rs.getDate("dueDate").toLocalDate());
                    taskEntity.setPriority(rs.getInt("priority"));

                    return Optional.of(taskEntity);
                }
            }
        }
        return Optional.empty();
    }

    // READ ALL
    public List<TaskEntity> findAll() throws SQLException {
        //System.out.println("DEBUG DB URL: " + connection.getMetaData().getURL());
        //System.out.println("DEBUG DB USER: " + connection.getMetaData().getUserName());

        Statement s = connection.createStatement();
        ResultSet rsTest = s.executeQuery("SELECT current_database(), current_schema()");
        rsTest.next();
        //System.out.println("DEBUG current_database: " + rsTest.getString(1));
        //System.out.println("DEBUG current_schema: " + rsTest.getString(2));


        List<TaskEntity> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setID(rs.getInt("id"));
                taskEntity.setName(rs.getString("name"));
                taskEntity.setStatus(rs.getInt("status"));
                taskEntity.setDueDate(rs.getDate("dueDate").toLocalDate());
                taskEntity.setPriority(rs.getInt("priority"));
            }
        }
        //System.out.println("DEBUG TaskDAO tasks found: " + tasks.size());
        return tasks;
    }

    @Override
    public TaskEntity updateByID(TaskEntity entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteByID(Integer ID) throws SQLException{
        //return false;
    }

    public Optional<TaskEntity> findByTaskName(String taskName) throws SQLException{
        String sql = "SELECT * FROM tasks WHERE LOWER(name) = LOWER(?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, taskName);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    TaskEntity taskEntity = new TaskEntity();
                    taskEntity.setID(rs.getInt("id"));
                    taskEntity.setName(rs.getString("name"));
                    taskEntity.setStatus(rs.getInt("status"));
                    taskEntity.setDueDate(rs.getDate("dueDate").toLocalDate());
                    taskEntity.setPriority(rs.getInt("priority"));

                    return Optional.of(taskEntity);
                }
            }
        }
        return Optional.empty();
    }
}