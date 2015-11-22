/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.handmail.Controller;

import cs.handmail.file.DataUserFile;
import cs.handmail.file.PropertiesFile;
import cs.handmail.mail.SessionEmail;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Venus-NS
 */
public class InboxController {
    private JTable tableInbox;
    private Store store;
    Message[] mess;
    
    public Message[] getMessage(){
        return mess;
    }
    
    public InboxController(JTable tableInbox, Store store)
    {
        this.store = store;
        this.tableInbox = tableInbox;
    }
    
    void setAdapter()
    {
        try{
            Vector<String> user = new DataUserFile().readDataUser();
            Vector<String> host = new PropertiesFile().readFile();
            SessionEmail session = new SessionEmail();
            session.connectIMAPS(user.get(0), user.get(1), host.get(0), host.get(1));
            store = session.getStore();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    Message[] getInboxData()
    {
        try{
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            int count = folder.getMessageCount();
            Message[] mess;
            if(count<30)
                mess = folder.getMessages();
            else
                mess = folder.getMessages(count, count-30);
            return mess;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void setTableData(){
        try{
          
            mess = getInboxData();
            DefaultTableModel tableModel = (DefaultTableModel) tableInbox.getModel();
            for(int i = 0; i< mess.length; i++)
            {
                Date date = mess[mess.length-1-i].getSentDate();
                String addS="";
                Address[] address = mess[mess.length-1-i].getFrom();
                for(int j=0; j< address.length; j++)
                {
                      addS+= address[j].toString();
                }
                tableModel.addRow(new Object[]{false,addS,mess[i].getSubject(),mess[i].getDescription(),date.toString()});
            }
        }catch(Exception ex)
        {
            setAdapter();
            setTableData();
            ex.printStackTrace();
        }
    }
}
