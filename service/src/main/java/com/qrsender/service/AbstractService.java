package com.qrsender.service;

import com.qrsender.api.dal.IGenericDao;
import com.qrsender.api.service.IGenericService;
import com.qrsender.model.GenericEntity;

import java.io.Serializable;

public abstract class AbstractService<T extends GenericEntity, PK extends Serializable> implements IGenericService<T, PK> {

    @Override
    public T getById(PK id) {
        return getDao().getById(id);
    }

    @Override
    public PK save(T entity) {
        return getDao().save(entity);
    }

    @Override
    public T update(T entity) {
        return getDao().update(entity);
    }

    @Override
    public void delete(PK id) {
        getDao().delete(getById(id));
    }
    public abstract IGenericDao<T, PK> getDao();
}
