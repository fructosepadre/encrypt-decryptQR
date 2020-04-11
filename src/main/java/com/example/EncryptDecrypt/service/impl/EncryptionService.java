package com.example.EncryptDecrypt.service.impl;

import com.example.EncryptDecrypt.service.EncryptionInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

@Service
@Slf4j
public class EncryptionService implements EncryptionInterface {

    public String encryptor(String codes){
        int len = codes.length();
        int rowSize = (int) floor(sqrt(len));
        int columnSize = (int) ceil(sqrt(len));
        if (columnSize * rowSize < len) {
            if (min(columnSize, rowSize) == columnSize)
                columnSize+=1;
            else
                rowSize+=1;
        }

        String[][] clockwiseMatrix = new String[rowSize][columnSize];
        String[][] reverseMatrix = new String[rowSize][columnSize];

        for (int i=0;i<rowSize;i++)
            for (int j = 0; j < columnSize; j++)
                clockwiseMatrix[i][j]=" ";


        int k=0,l=0,codeLength=0;

        int rowEnd=rowSize,rowStart=columnSize;
        try {
            while (k<rowEnd && l<rowStart && codeLength<=len) {
                for (int i=l;i<rowStart;++i) {
                    clockwiseMatrix[k][i] = String.valueOf(codes.charAt(codeLength));
                    codeLength++;
                }
                k++;

                for (int i=k;i<rowEnd;++i) {
                    clockwiseMatrix[i][rowStart - 1] = String.valueOf(codes.charAt(codeLength));
                    codeLength++;
                }
                rowStart--;

                if (k<rowEnd) {
                    for (int i=rowStart-1;i>=l;--i) {
                        clockwiseMatrix[rowEnd - 1][i] = String.valueOf(codes.charAt(codeLength));
                        codeLength++;
                    }
                    rowEnd--;
                }

                if (l < rowStart) {
                    for (int i=rowEnd-1;i>=k;--i) {
                        clockwiseMatrix[i][l] = String.valueOf(codes.charAt(codeLength));
                        codeLength++;
                    }
                    l++;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
        }

        for (int x=0;x<rowSize;x++) {
            for (int y=0, z=columnSize-1;y<columnSize && z>=0;y++,z--){
                reverseMatrix[x][z] = clockwiseMatrix[x][y];
            }
        }
        String ciphertext="";
        for (int i=0;i<rowSize;i++)
            for (int j = 0; j < columnSize; j++)
                    ciphertext+=reverseMatrix[i][j];

        log.info(ciphertext);

        return ciphertext;
    }

    @Override
    public String encryptQRCodeString(String plaintext){
        List<String> PT=Arrays.asList(plaintext.split("\\|"));
        String facultyID=encryptor(PT.get(0));
        String subjectCode=encryptor(PT.get(1));
        String date=encryptor(PT.get(2));
        String time=encryptor(PT.get(3));

        String cipherText=facultyID+"|"+subjectCode+"|"+date+"|"+time;

        return cipherText;
    }

}
