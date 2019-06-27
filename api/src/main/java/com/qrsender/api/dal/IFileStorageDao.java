package com.qrsender.api.dal;

import com.qrsender.model.FileStorage;

import java.time.LocalDate;
import java.util.List;

public interface IFileStorageDao extends IGenericDao<FileStorage, Long> {

    List<FileStorage> getFilesOlderThenDate(LocalDate date);
}
