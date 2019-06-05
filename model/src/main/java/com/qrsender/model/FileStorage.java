package com.qrsender.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "file_storage", schema = "qr")
public class FileStorage extends GenericEntity {

    private byte [] file;
}
