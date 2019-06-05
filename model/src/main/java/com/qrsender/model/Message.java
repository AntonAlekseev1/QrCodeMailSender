package com.qrsender.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "messages", schema = "qr")
public class Message extends GenericEntity {

    private String message;
}
