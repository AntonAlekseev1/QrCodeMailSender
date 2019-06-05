package com.qrsender.api.service;

import com.qrsender.model.GenericEntity;

import java.io.Serializable;

public interface IGenericService<T extends GenericEntity, PK extends Serializable> {

    T getById(PK id);

    PK save(T entity);

    T update(T entity);

    void delete(PK id);
}
