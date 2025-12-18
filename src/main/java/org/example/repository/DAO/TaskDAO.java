package org.example.repository.DAO;

import org.example.repository.entities.TaskEntity;
import org.example.util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAO implements DAOInterface<TaskEntity> {

    private Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(TaskEntity taskEntity) throws SQLException {

        String sql = "INSERT INTO task (task) VALUES (?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, taskEntity.getName());

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
        String sql = "SELECT * FROM Task WHERE ID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    TaskEntity taskEntity = new TaskEntity();
                    taskEntity.setID(rs.getInt("id"));
                    taskEntity.setName(rs.getString("name"));
                    taskEntity.setStatus(rs.getInt("status"));
                    taskEntity.setDueDate(rs.getDate("dueDate"));
                    taskEntity.setPriority(rs.getInt("priority"));

                    return Optional.of(taskEntity);
                }
            }
        }
        return Optional.empty();
    }

    // READ ALL
    public List<TaskEntity> findAll() throws SQLException {
        List<TaskEntity> tasks = new ArrayList<>();

        String sql = "SELECT * FROM task";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setID(rs.getInt("id"));
                taskEntity.setName(rs.getString("taskName"));
                taskEntity.setStatus(rs.getInt("status"));
                taskEntity.setDueDate(rs.getDate("dueDate"));
                taskEntity.setPriority(rs.getInt("priority"));
            }
        }
        return tasks;
    }

    @Override
    public TaskEntity updateByID(TaskEntity entity) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteByID(Integer ID) throws SQLException{
        return false;
    }

    public Optional<TaskEntity> findByTaskName(String TaskName) throws SQLException{
        String sql = "SELECT * FROM Task WHERE Task = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, TaskName);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    TaskEntity TaskEntity = new TaskEntity();
                    TaskEntity.setID(rs.getInt("ID"));
                    TaskEntity.setName(rs.getString("Task"));

                    return Optional.of(TaskEntity);
                }
            }
        }
        return Optional.empty();
    }
}