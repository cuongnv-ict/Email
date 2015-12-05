/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Van Cuong
 */
public class ListAcountFile {

    private String filePath = "Cache/acounts.properties";
    private DataUserFile dataUserFile;

    public ListAcountFile() {
        File cache = new File("Cache");
        dataUserFile = new DataUserFile();
        if (!cache.exists()) {
            cache.mkdir();
        }
    }

    public Properties readListAcount() {
        File user = new File(filePath);
        if (user.exists()) {
            try {
                InputStream dataInputStream = new FileInputStream(filePath);
                if (dataInputStream != null) {
                    Properties properties = new Properties();
                    properties.load(dataInputStream);
                    if (properties.isEmpty()) {
                        return null;
                    } else {
                        return properties;
                    }
                }
                dataInputStream.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        return null;
    }

    public void addAcount(String mail, String pass) {
        try {
            int id = 1;

            Properties properties = readListAcount();
            FileOutputStream fileOutPutStream = new FileOutputStream(filePath);
            if (properties == null) {
                properties = new Properties();
            }
            properties.setProperty(mail, dataUserFile.encryptPass(pass));
            properties.store(fileOutPutStream, null);
            fileOutPutStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void deleteAcount(ArrayList<String> mails) {
        try {
            Properties properties = readListAcount();
            FileOutputStream fileOutPutStream = new FileOutputStream(filePath);
            if (properties != null) {
                for (String mail : mails) {
                    properties.remove(mail,properties.get(mail));
                }
                properties.store(fileOutPutStream, null);
                fileOutPutStream.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListAcountFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListAcountFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isExistsAcount(String mail) {
        Properties properties = readListAcount();
        if (properties != null) {
            if(properties.get(mail)!= null){
                return true;
            }
        }
        return false;
    }

}
