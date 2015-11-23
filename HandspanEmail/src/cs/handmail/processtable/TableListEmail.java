/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.processtable;

import cs.handmail.mail.AccuracyEmail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

            Object[] nameColumn = {"", "STT", "Date", "Form", "Subject"};
            ArrayList<Object[]> data = new ArrayList<>();
            int count = 1;
            for (int i = message.length - 1; i >= 0; i--) {
                Message msg = message[i];
                try {
                    Object[] str = new Object[5];
                    str[0] = false;
                    str[1] = count;
                    str[2] = df1.format(msg.getSentDate());
                    int z = 0;
                    for (Address a : msg.getFrom()) {
                        String m = accuracyEmail.extraEmail(a.toString());
                        if (z == 0) {
                            str[3] = m;
                            z++;
                        } else {
                            str[3] = String.valueOf(str[3]).concat(";"+m);
                        }
                        str[4] = msg.getSubject();
                        data.add(str);
                        count++;
                    }
                } catch (MessagingException ex) {
                    Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Object[][] rowColumn = new Object[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                rowColumn[i] = data.get(i);
            }
            DefaultTableModel model = new DefaultTableModel(rowColumn, nameColumn) {
                Class[] types = new Class[]{
                    java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
                boolean[] canEdit = new boolean[]{
                    true, false, false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            table.setModel(model);
        }
    }
}
