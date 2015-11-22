/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Venus-NS
 */
public class DataUserFile {

    private String filePath = "Cache/user.properties";
    private FileOutputStream fileOutPutStream;
    private InputStream dataInputStream;
    private Properties properties;
    private String initialVector = "0123456789123456";
    private String secretKey = "cshandspanmail";

    public DataUserFile() {
        File cache = new File("Cache");
        if (!cache.exists()) {
            cache.mkdir();
        }
    }

    public void writeDataUser(String mail, String pass, int autoLog) {
        try {
            fileOutPutStream = new FileOutputStream(filePath);
            properties = new Properties();
            properties.setProperty("Email", mail);
            properties.setProperty("Password", encryptPass(pass));
            properties.setProperty("AutoLogIn", String.valueOf(autoLog));
            properties.store(fileOutPutStream, null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public Vector<String> readDataUser() {
        Vector<String> info = new Vector<>();
        File user = new File(filePath);
        if (user.exists()) {
            try {
                dataInputStream = new FileInputStream(filePath);
                if (dataInputStream != null) {
                    properties = new Properties();
                    properties.load(dataInputStream);
                    info.add(properties.getProperty("Email"));
                    info.add(decryptPass(properties.getProperty("Password")));
                    info.add(properties.getProperty("AutoLogIn"));
                    return info;
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        info.clear();
        info.add("");
        info.add("");
        info.add("0");
        return info;
    }

    public String encryptPass(String dataToEncrypt) {
        String encryptedData = null;
        try {
            // Initialize the cipher
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, initialVector, secretKey);
            // Encrypt the data
            final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt.getBytes());
            // Encode using Base64
            encryptedData = (new BASE64Encoder()).encode(encryptedByteArray);
        } catch (Exception e) {
            System.err.println("Problem encrypting the data");
            e.printStackTrace();
        }
        return encryptedData;
    }

    public String decryptPass(String encryptedData) {
        String decryptedData = null;
        try {
            // Initialize the cipher
            final Cipher cipher = initCipher(Cipher.DECRYPT_MODE, initialVector, secretKey);
            // Decode using Base64
            final byte[] encryptedByteArray = (new BASE64Decoder()).decodeBuffer(encryptedData);
            // Decrypt the data
            final byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
            decryptedData = new String(decryptedByteArray, "UTF8");
        } catch (Exception e) {
            System.err.println("Problem decrypting the data");
            e.printStackTrace();
        }
        return decryptedData;
    }

    private Cipher initCipher(int mode, String initialVectorString, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        final SecretKeySpec skeySpec = new SecretKeySpec(md5(secretKey).getBytes(), "AES");
        final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
        final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(mode, skeySpec, initialVector);
        return cipher;
    }

    private static String md5(String input) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] messageDigest = md.digest(input.getBytes());
        final BigInteger number = new BigInteger(1, messageDigest);
        return String.format("%032x", number);
    }
}
