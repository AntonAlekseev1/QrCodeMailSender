package com.qrsender.api.dal;

import com.qrsender.model.GenericEntity;

import java.io.Serializable;

public interface IGenericDao<T extends GenericEntity, PK extends Serializable> {

    T getById(PK id);

    PK save(T entity);

    T update(T entity);

    void delete(T entity);
}
