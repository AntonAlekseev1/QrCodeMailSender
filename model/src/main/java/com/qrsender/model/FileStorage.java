package com.qrsender.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileStorage extends GenericEntity {

    private byte [] file;
}
