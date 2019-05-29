package com.qrsender.dal;

import com.qrsender.api.dal.IGenericDao;
import com.qrsender.model.GenericEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.Serializable;

public abstract class AbstractDao<T extends GenericEntity, PK extends Serializable> implements IGenericDao<T, PK> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    private Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getById(PK id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public PK save(T entity) {
       return (PK) this.getSession().save(entity);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
