package org.example.service;

import org.example.repository.DAO.UserTasksDAO;
import org.example.repository.entities.UserTasksEntity;
import org.example.service.interfaces.ServiceInterface;
import org.example.service.model.UserTasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTasksService implements ServiceInterface<UserTasksEntity, UserTasks> {
    private UserTasksDAO userTasksDAO = new UserTasksDAO();

    @Override
    public Integer createEntity(UserTasksEntity entity) {
        try{
            Integer newID = userTasksDAO.create(entity);
            return newID;
        }catch(SQLException e){
           //e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<UserTasksEntity> getEntityByID(Integer id) {
        try{
            Optional<UserTasksEntity> userTasks = userTasksDAO.findByUserID(id);
            if(userTasks.isEmpty()){
                throw new RuntimeException("UserTasks not found");
            }
            return userTasks;
        }catch(SQLException | RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<UserTasksEntity> getAllEntities() {
        try{
            List<UserTasksEntity> userTasksEntities = userTasksDAO.findAll();
            return userTasksEntities;
        }catch (SQLException e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserTasksEntity updateEntity(Integer id, UserTasksEntity newEntity) {
        return null;
    }

    @Override
    public void deleteEntity(Integer id) {
        //return false;
    }

    @Override
    public Optional<UserTasks> convertEntityToModel(UserTasksEntity entity) {
        try{
            UserTasks userTasks = new UserTasks();
            userTasks.setID(entity.getID());
            userTasks.setUserID(entity.getUserID());
            userTasks.setTaskID(entity.getTaskID());

            return Optional.of(userTasks);

        }catch(RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserTasks> getModelByID(Integer id) {
        Optional<UserTasksEntity> locationEntity = getEntityByID(id);
        try{
            Optional<UserTasks> userTasks = convertEntityToModel(getEntityByID(id).get());
            return userTasks;
        }catch(RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<UserTasks> getAllModels() {
        List<UserTasksEntity> userEntities = getAllEntities();
        List<UserTasks> userTasks = new ArrayList<>();
        for(UserTasksEntity userTaskEntity: userEntities){
            Optional<UserTasks> userTask = convertEntityToModel(userTaskEntity);
            if(userTask.isPresent()){
                userTasks.add(userTask.get());
            }
        }
        return userTasks;
    }
}