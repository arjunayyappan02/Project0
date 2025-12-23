import org.example.repository.DAO.UserDAO;
import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserEntity;
import org.example.repository.entities.UserTasksEntity;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.service.UserTasksService;
import org.example.service.model.Task;
import org.example.service.model.User;
import org.example.service.model.UserTasks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    void createEntity_success() throws SQLException {
        UserEntity entity = new UserEntity();
        when(userDAO.create(entity)).thenReturn(1);

        Integer result = userService.createEntity(entity);

        assertEquals(1, result);
        verify(userDAO).create(entity);
    }

    @Test
    void getEntityByID_success() throws SQLException {
        UserEntity entity = new UserEntity();
        entity.setID(1);

        when(userDAO.findByID(1)).thenReturn(Optional.of(entity));

        Optional<UserEntity> result = userService.getEntityByID(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getID());
    }

    @Test
    void returnFullName_success() throws SQLException {
        when(userDAO.returnFullName(1)).thenReturn("John Doe");

        String result = userService.returnFullName(1);

        assertEquals("John Doe", result);
    }

    @Test
    void deleteEntity_success() throws SQLException {
        doNothing().when(userDAO).deleteByID(1);

        userService.deleteEntity(1);

        verify(userDAO).deleteByID(1);
    }

    @Test
    void createEntity_sqlException_returnsMinusOne() throws SQLException {
        UserEntity entity = new UserEntity();
        when(userDAO.create(entity)).thenThrow(SQLException.class);

        Integer result = userService.createEntity(entity);

        assertEquals(-1, result);
    }

    @Test
    void getEntityByID_notFound_returnsEmpty() throws SQLException {
        when(userDAO.findByID(99)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.getEntityByID(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void returnFullName_sqlException_returnsNull() throws SQLException {
        when(userDAO.returnFullName(1)).thenThrow(SQLException.class);

        String result = userService.returnFullName(1);

        assertNull(result);
    }

    @Test
    void getAllEntities_sqlException_returnsNull() throws SQLException {
        when(userDAO.findAll()).thenThrow(SQLException.class);

        List<UserEntity> result = userService.getAllEntities();

        assertNull(result);
    }

    @Test
    void convertEntityToModel_success() {
        UserEntity entity = new UserEntity();
        entity.setID(1);
        entity.setFirstName("Jane");
        entity.setLastName("Doe");
        entity.setRole(1);

        Optional<User> result = userService.convertEntityToModel(entity);

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    void getAllModels_convertsAllEntities() throws SQLException {
        // Arrange
        UserEntity u1 = new UserEntity();
        u1.setID(1);
        u1.setFirstName("John");
        u1.setLastName("Doe");
        u1.setRole(1);

        UserEntity u2 = new UserEntity();
        u2.setID(2);
        u2.setFirstName("Jane");
        u2.setLastName("Smith");
        u2.setRole(0);

        List<UserEntity> entities = List.of(u1, u2);

        when(userDAO.findAll()).thenReturn(entities);

        // Act
        List<User> result = userService.getAllModels();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());

        verify(userDAO).findAll();
    }

}




