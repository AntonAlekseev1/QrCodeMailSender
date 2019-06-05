package com.qrsender.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "emails", schema = "qr")
public class Email extends GenericEntity {

    @Column(name = "recipient_address")
    private String recipientAddress;
    @Column(name = "file_id")
    private Long fileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="message_id")
    private Message message;
}
