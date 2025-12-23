package org.example.repository.DAO;

import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserEntity;
import org.example.repository.entities.UserTasksEntity;
import org.example.util.ConnectionHandler;

//import javax.xml.stream.UserTasks;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTasksDAO {

    private Connection connection = ConnectionHandler.getConnection();

    private static final String ASSIGN_TASK = "INSERT INTO userTasks (userID, taskID) VALUES (?, ?)";

    public void assignTaskToUser(int userID, int taskID) throws SQLException {

        try (PreparedStatement stmt = connection.prepareStatement(ASSIGN_TASK)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, taskID);
            stmt.executeUpdate();
        }
    }

    public List<TaskEntity> findTasksByUserID(int userID) throws SQLException {
        String sql = "SELECT t.* FROM tasks t JOIN userTasks ut ON t.id = ut.taskID WHERE ut.userID = ?";

        List<TaskEntity> tasks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TaskEntity task = new TaskEntity();
                task.setID(rs.getInt("id"));
                task.setName(rs.getString("name"));
                task.setStatus(rs.getInt("status"));
                task.setDueDate(rs.getDate("dueDate").toLocalDate());
                task.setPriority(rs.getInt("priority"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    // Remove a task assignment for a user
    public boolean removeTaskFromUser(int userID, int taskID) throws SQLException {
        String sql = "DELETE FROM userTasks WHERE userID = ? AND taskID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, taskID);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<UserEntity> findUsersByTaskID(int taskID) throws SQLException {
        String sql = "SELECT u.id, u.firstName, u.lastName, u.role FROM users u JOIN userTasks ut ON u.id = ut.userID WHERE ut.taskID = ?";

        List<UserEntity> users = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, taskID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserEntity user = new UserEntity();
                    user.setID(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setRole(rs.getInt("role"));
                    users.add(user);
                }
            }
        }
        return users;
    }

    public Integer create(UserTasksEntity entity) throws SQLException {
        String sql = "INSERT INTO userTasks (userID, taskID) VALUES (?, ?) RETURNING id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, entity.getUserID());
            stmt.setInt(2, entity.getTaskID());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    public Optional<UserTasksEntity> findByUserID (Integer id) throws SQLException {
        String sql = "SELECT taskID FROM userTasks WHERE userID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserTasksEntity userTasks = new UserTasksEntity();
                    userTasks.setID(rs.getInt("id"));
                    userTasks.setUserID(rs.getInt("firstName"));
                    userTasks.setTaskID(rs.getInt("lastName"));

                    return Optional.of(userTasks);
                }
            }
        }
        return Optional.empty();
    }


    //@Override
    public List<UserTasksEntity> findAll() throws SQLException {
        List<UserTasksEntity> userTasks = new ArrayList<>();

        String sql = "SELECT userID, taskID FROM userTasks ORDER BY taskID";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                UserTasksEntity userTask = new UserTasksEntity();
                userTask.setID(rs.getInt("id"));
                userTask.setUserID(rs.getInt("userID"));
                userTask.setTaskID(rs.getInt("taskID"));

                userTasks.add(userTask);
            }
        }
        return userTasks;
    }
}