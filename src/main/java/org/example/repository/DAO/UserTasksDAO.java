package org.example.repository.DAO;

import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserTasksEntity;
import org.example.util.ConnectionHandler;

import javax.xml.stream.UserTasks;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTasksDAO implements DAOInterface<UserTasksEntity> {

    private Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(UserTasksEntity entity) throws SQLException {
        String sql = "INSERT INTO UserTasks (UserTasks) VALUES (?) RETURNING ID";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, entity.getUserTasks());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    @Override
    public Optional<UserTasksEntity> findByID(Integer ID) throws SQLException {
        String sql = "SELECT * FROM UserTasks WHERE ID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, ID);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    UserTasksEntity UserTasks = new UserTasksEntity();
                    UserTasks.setID(rs.getInt("ID"));
                    UserTasks.setUserTasks(rs.getString("UserTasks"));

                    return Optional.of(UserTasks);
                }
            }
        }
        return Optional.empty();    }

    @Override
    public List<UserTasksEntity> findAll() throws SQLException {
        List<UserTasksEntity> UserTasks = new ArrayList<>();

        String sql = "SELECT * FROM UserTasks";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                UserTasksEntity UserTasks = new UserTasksEntity();
                UserTasks.setID(rs.getInt("ID"));
                UserTasks.setUserTasks(rs.getString("UserTasks"));
                UserTaskss.add(UserTasks);
            }
        }
        return UserTaskss;    }

    @Override
    public UserTasksEntity updateByID(UserTasksEntity entity) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteByID(Integer ID) throws SQLException {
        return false;
    }

    public Optional<UserTasksEntity> findByUserTasksName(String UserTasksName) throws SQLException{
        String sql = "SELECT * FROM UserTasks WHERE UserTasks = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, UserTasksName);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    UserTasksEntity UserTasks = new UserTasksEntity();
                    UserTasks.setID(rs.getInt("ID"));
                    UserTasks.setUserTasks(rs.getString("UserTasks"));

                    return Optional.of(UserTasks);
                }
            }
        }
        return Optional.empty();
    }
}