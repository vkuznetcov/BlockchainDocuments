package ru.ssau.blockchaindocuments.utils;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

public class HexUtil {
    public static String applySha256(byte[] file, String otherInfo){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write(file);
            outputStream.write(otherInfo.getBytes());
            byte[] result = outputStream.toByteArray();

            byte[] hash = digest.digest(result);
            StringBuilder hexString = new StringBuilder(); // This will contain hash as hexidecimal
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
