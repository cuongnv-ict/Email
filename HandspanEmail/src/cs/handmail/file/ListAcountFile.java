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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author Nguyen Van Cuong
 */
public class ListAcountFile {

    private String filePath = "Cache/acounts.properties";

    public ListAcountFile() {
        File cache = new File("Cache");
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

    public void addAcount(String mail) {
        try {
            int id = 1;

            Properties properties = readListAcount();
            FileOutputStream fileOutPutStream = new FileOutputStream(filePath);
            if (properties == null) {
                properties = new Properties();
            } else {              
                id = properties.size() + 1;
            }
            properties.setProperty(String.valueOf(id), mail);
            properties.store(fileOutPutStream, null);
            fileOutPutStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void deleteAcount(int id) {

    }

    public void deleteAcount(String mail) {
    }

    public boolean isExistsAcount(String mail) {
        return false;
    }
}
