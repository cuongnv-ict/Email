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
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author Nguyen Van Cuong
 */
public class PropertiesFile {

    private String filePath = "Cache/conf.properties";
    private FileOutputStream fileOutPutStream;
    private InputStream dataInputStream;
    private Properties properties;

    public PropertiesFile() {
        File cache = new File("Cache");
        if (!cache.exists()) {
            cache.mkdir();
        }
    }

    public Vector<String> readFile() {
        Vector<String> info = new Vector<>();
        try {

            dataInputStream = new FileInputStream(filePath);
            if (dataInputStream != null) {
                properties = new Properties();
                properties.load(dataInputStream);
                info.add(properties.getProperty("HostName", "mail.handspan.com"));
                info.add(properties.getProperty("Port", "143"));
                dataInputStream.close();
                return info;
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        writeFile("mail.handspan.com", "143");
        info.clear();
        info.add("mail.handspan.com");
        info.add("143");
        return info;
    }

    public void writeFile(String host, String port) {
        try {

            fileOutPutStream = new FileOutputStream(filePath);
            properties = new Properties();
            properties.setProperty("HostName", host);
            properties.setProperty("Port", port);
            properties.store(fileOutPutStream, null);
            fileOutPutStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
