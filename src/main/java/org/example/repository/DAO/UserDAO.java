package org.example.repository.DAO;

import org.example.repository.entities.UserEntity;
import org.example.util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAOInterface<UserEntity> {
    private Connection connection = ConnectionHandler.getConnection();
    @Override
    public Integer create(UserEntity entity) throws SQLException {
        String sql = "INSERT INTO users (firstName, lastName, role) VALUES (?, ?, ?) RETURNING ID";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setInt(3, entity.getRole());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    @Override
    public Optional<UserEntity> findByID(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    UserEntity user = new UserEntity();
                    user.setID(rs.getInt("id"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setRole(rs.getInt("role"));

                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                UserEntity user = new UserEntity();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setRole(rs.getInt("role"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public UserEntity updateByID(UserEntity entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteByID (Integer id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();

        }
    }

    public List<UserEntity> findAllByUserID(Integer taskID) throws SQLException {
        List<UserEntity> users = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, taskID);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                UserEntity user = new UserEntity();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getInt("role"));
                users.add(user);
            }
        }
        return users;
    }

    public List<UserEntity> findAllByTaskID(int taskID) throws SQLException {
        List<UserEntity> users = new ArrayList<>();

        //String sql = "SELECT * FROM users WHERE task_id = ?";
        String sql = "SELECT u.* FROM users u JOIN userTasks ut ON u.id = ut.userID WHERE ut.taskID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, taskID);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                UserEntity user = new UserEntity();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setRole(rs.getInt("role"));

                users.add(user);
            }
        }
        return users;
    }

    //@Override
    public String returnFullName (Integer id) throws SQLException {
        String sql = "SELECT first_name, last_name FROM users WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getString("first_name") + rs.getString("last_name");
                }
            }
        }
        return "No name found";
    }
}
