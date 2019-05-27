package com.qrsender.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Message extends GenericEntity {

    private String message;
    private LocalDate creationDate;
}
