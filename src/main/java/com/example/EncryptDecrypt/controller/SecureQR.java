package com.example.EncryptDecrypt.controller;

import com.example.EncryptDecrypt.service.impl.DecryptionService;
import com.example.EncryptDecrypt.service.impl.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/secureQR")
public class SecureQR {

    @Autowired
    private DecryptionService decryptionService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping(value = "/encryption/{plaintext}")
    public String encryption(@PathVariable("plaintext") String plaintext) {
        String ciphertext=encryptionService.encryptQRCodeString(plaintext);
        return ciphertext;
    }

    @GetMapping(value = "/decryption/{ciphertext}")
    public String decryption(@PathVariable("ciphertext") String ciphertext){
        String plaintext=decryptionService.decryptQRCodeString(ciphertext);
        return plaintext;
    }

}
