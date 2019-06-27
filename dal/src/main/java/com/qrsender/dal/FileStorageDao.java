package com.qrsender.dal;

import com.qrsender.api.dal.IFileStorageDao;
import com.qrsender.model.FileStorage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class FileStorageDao extends AbstractDao<FileStorage, Long> implements IFileStorageDao {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public FileStorageDao() {
        super(FileStorage.class);
    }

    @Override
    public List<FileStorage> getFilesOlderThenDate(LocalDate date) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FileStorage> criteriaQuery = builder.createQuery(FileStorage.class);
        Root<FileStorage> root = criteriaQuery.from(FileStorage.class);
        CriteriaQuery<FileStorage> select = criteriaQuery.select(root).where(builder.lessThan(root.get("creationDate"), date));
        return entityManager.createQuery(select).getResultList();
    }
}
