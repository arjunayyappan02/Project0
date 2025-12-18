package org.example.service.interfaces;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<T, U> {

    // CRUD Operations

    Integer createEntity(T entity);
    Optional<T> getEntityByID (Integer ID);
    List<T> getAllEntities();
    T updateEntity(Integer ID, T newEntity);
    boolean deleteEntity(Integer ID);

    // Conversion
    Optional<U> convertEntityToModel(T entity);

    Optional<U> getModelByID(Integer ID);


}
