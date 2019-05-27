package com.qrsender.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Email extends GenericEntity {

    private String recipientAddress;
    private Long fileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="messageId")
    private Message message;
}
