/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.processtable;

import cs.handmail.mail.AccuracyEmail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nguyen Van Cuong
 */
public class TableListEmail {

    private AccuracyEmail accuracyEmail;
    private SimpleDateFormat df1;

    public TableListEmail() {
        accuracyEmail = new AccuracyEmail();
        df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    public void displayEmail(JTable table, Message[] message) {
        if (message != null) {
            ArrayList<Object[]> data = new ArrayList<>();
            int count = 1;
            for (int i = message.length - 1; i >= 0; i--) {
                Message msg = message[i];
                try {
                    Object[] str = new Object[5];
                    str[0] = false;
                    str[1] = count;
                    if (msg.getSentDate() == null) {
                        str[2] = "";
                    } else {
                        str[2] = df1.format(msg.getSentDate());
                    }

                    int z = 0;
                    for (Address a : msg.getFrom()) {
                        String m = accuracyEmail.extraEmail(a.toString());
                        if (z == 0) {
                            str[3] = m;
                            z++;
                        } else {
                            str[3] = String.valueOf(str[3]).concat(";" + m);
                        }
                        str[4] = msg.getSubject();
                        data.add(str);
                        count++;
                    }
                } catch (MessagingException ex) {
                    Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            for (int i = 0; i < data.size(); i++) {
                model.addRow(data.get(i));
            }
        }
    }

    public void InfoElement(JTable table, Map<Message, Message> info) {

        int count = 1;
        ArrayList<Object[]> data = new ArrayList();
        for (Message msg : info.keySet()) {
            SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
            Object[] str = new Object[7];
            str[0] = count;
            try {
                for (Address a : msg.getFrom()) {
                    str[1] = accuracyEmail.extraEmail(a.toString());
                }
            } catch (MessagingException ex) {
                Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                str[2] = dt.format(msg.getReceivedDate());
            } catch (MessagingException ex) {
                Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                str[3] = msg.getSubject();
            } catch (MessagingException ex) {
                Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message an = info.get(msg);
            if (an == null) {
                str[4] = "";
                str[5] = "";
                str[6] = "";
            } else {
                try {
                    String m = "";
                    for (Address a : an.getAllRecipients()) {
                        if (m.equals("")) {
                            m = accuracyEmail.extraEmail(a.toString());
                        } else {
                            m = m + "/";
                            m = m + accuracyEmail.extraEmail(a.toString());
                        }
                    }
                    str[4] = m;
                } catch (MessagingException ex) {
                    Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    str[5] = dt.format(an.getSentDate());
                } catch (MessagingException ex) {
                    Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    str[6] = an.getSubject();
                } catch (MessagingException ex) {
                    Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            count++;
            data.add(str);
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for (int i = 0; i < data.size(); i++) {
            model.addRow(data.get(i));
        }
    }
}
