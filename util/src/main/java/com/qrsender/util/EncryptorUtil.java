package com.qrsender.util;

import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncryptorUtil {

    private final TextEncryptor textEncryptor;

    @Autowired
    public EncryptorUtil(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    public String decrypt(String encryptedText){
        return textEncryptor.decrypt(encryptedText);
    }
}
