package com.example.EncryptDecrypt.service.impl;

import com.example.EncryptDecrypt.service.DecryptionInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

@Service
@Slf4j
public class DecryptionService implements DecryptionInterface {

    public String decryptor(String codes){
        int len = codes.length();
        int rowSize = (int) floor(sqrt(len));
        int columnSize = (int) ceil(sqrt(len));
        if (columnSize * rowSize < len) {
            if (min(columnSize, rowSize) == columnSize)
                columnSize+=1;
            else
                rowSize+=1;
        }

    String[][] cipherTextMatrix = new String[rowSize][columnSize];
    String[][] reverseMatrix = new String[rowSize][columnSize];

        for (int i=0;i<rowSize;i++)
            for (int j = 0; j < columnSize; j++)
                cipherTextMatrix[i][j]=reverseMatrix[i][j]=" ";

    int l = 0;
        String plaintext = "";
        try {
    for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
            cipherTextMatrix[i][j] = String.valueOf(codes.charAt(l));
            l++;
        }
    }


    for (int x = 0; x < rowSize; x++) {
        for (int y = 0, z = columnSize - 1; y < columnSize && z >= 0; y++, z--) {
            reverseMatrix[x][y] = cipherTextMatrix[x][z];
        }
    }

    int k = 0;
    l = 0;
    while (k < rowSize && l < columnSize) {
        for (int i = l; i < columnSize; ++i)
            plaintext += reverseMatrix[k][i];

        k++;

        for (int i = k; i < rowSize; ++i)
            plaintext += reverseMatrix[i][columnSize - 1];

        columnSize--;


        if (k < rowSize) {
            for (int i = columnSize - 1; i >= l; --i)
                plaintext += reverseMatrix[rowSize - 1][i];

            rowSize--;
        }

        if (l < columnSize) {
            for (int i = rowSize - 1; i >= k; --i)
                plaintext += reverseMatrix[i][l];

            l++;
        }
    }
}
catch (IndexOutOfBoundsException e)
{

}
        log.info(plaintext);

        return plaintext;
    }

    @Override
    public String decryptQRCodeString(String ciphertext) {
        return decryptor(ciphertext);
    }
}
