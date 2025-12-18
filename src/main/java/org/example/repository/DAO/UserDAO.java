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
        String sql = "INSERT INTO user (id, firstName, lastName, role, tasks) VALUES (?, ?, ?, ?, ?) RETURNING ID";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, entity.getID());
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setInt(4, entity.getRole());
            //stmt.setInt(5, entity.getTasks());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    @Override
    public Optional<UserEntity> findByID(Integer ID) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, ID);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    UserEntity user = new UserEntity();
                    user.setID(rs.getInt("id"));
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setRole(rs.getInt("role"));

                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> Users = new ArrayList<>();

        String sql = "SELECT * FROM User";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                UserEntity User = new UserEntity();
                User.setID(rs.getInt("id"));
                User.setFirstName(rs.getString("first_name"));
                User.setLastName(rs.getString("last_name"));
                User.setRole(rs.getInt("dept_id"));

                Users.add(User);
            }
        }
        return Users;
    }

    @Override
    public UserEntity updateByID(UserEntity entity) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteByID(Integer ID) throws SQLException {
        return false;
    }

    public List<UserEntity> findAllByUserID(Integer taskID) throws SQLException {
        List<UserEntity> Users = new ArrayList<>();

        String sql = "SELECT * FROM User WHERE dept_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, taskID);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                UserEntity User = new UserEntity();
                User.setID(rs.getInt("id"));
                User.setFirstName(rs.getString("first_name"));
                User.setLastName(rs.getString("last_name"));
                User.setRole(rs.getInt("role"));
                Users.add(User);
            }
        }
        return Users;
    }

    public List<UserEntity> findAllByTaskID(Integer taskID) throws SQLException {
        List<UserEntity> users = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                UserEntity user = new UserEntity();
                user.setID(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setLastName(rs.getInt("dept_id")); //UPDATE this to match user fields
                user.setRole(rs.getInt("roel"));

                users.add(user);
            }
        }
        return users;
    }
}
