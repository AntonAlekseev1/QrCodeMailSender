package com.qrsender.dal;

import com.qrsender.api.dal.IFileStorageDao;
import com.qrsender.model.FileStorage;
import org.springframework.stereotype.Repository;

@Repository
public class FileStorageDao extends AbstractDao<FileStorage, Long> implements IFileStorageDao {

    public FileStorageDao() {
        super(FileStorage.class);
    }
}
