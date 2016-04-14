/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.processtable;

import cs.handmail.mail.AccuracyEmail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
    private int flags;

    public TableListEmail() {
        accuracyEmail = new AccuracyEmail();
        df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        flags = 0;
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

    public void InfoElement(JTable table, Map<Message, Message> info, int status) {
        int count = 1;
        ArrayList<Object[]> data = new ArrayList();
        for (Message msg : info.keySet()) {
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Object[] str = new Object[8];
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
                if (status == 4) {
                    flags = 4;
                }
                if (status == 1 || status == 2) {
                    Date date = new Date();
                    int time;
                    try {
                        time = subTime(date, msg.getReceivedDate());
                        if (time <= 1440) {
                            flags = 1;
                        } else {
                            flags = 2;
                        }
                    } catch (MessagingException ex) {
                        Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                str[4] = "";
                str[5] = "";
                str[6] = "";
                str[7] = "";
            } else {
                try {
                    String m = "";
                    for (Address a : an.getAllRecipients()) {
                        if (m.equals("")) {
                            m = accuracyEmail.extraEmail(a.toString());
                        } else if (!m.contains(accuracyEmail.extraEmail(a.toString()))) {
                            m = m + "/";
                            m = m + accuracyEmail.extraEmail(a.toString());
                        }
                    }
                    if (status == 3) {
                        flags = 3;
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
                try {
                    int time = subTime(an.getSentDate(), msg.getReceivedDate());
                    str[7] = String.valueOf(time / 60 + ":" + time % 60);
                } catch (MessagingException ex) {
                    Logger.getLogger(TableListEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            switch (status) {
                case 0:
                    count++;
                    data.add(str);
                    break;
                case 1:
                    if (flags == 1) {
                        count++;
                        data.add(str);
                    }
                    break;
                case 2:
                    if (flags == 2) {
                        count++;
                        data.add(str);
                    }
                    break;
                case 3:
                    if (flags == 3) {
                        count++;
                        data.add(str);
                    }
                    break;
                case 4:
                    if (flags == 4) {
                        count++;
                        data.add(str);
                    }
                    break;
            }
            flags = 0;
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

    public int subTime(Date t1, Date t2) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-HH-mm-ss", Locale.ENGLISH);
        String s1[] = dt.format(t1).split("-");
        String s2[] = dt.format(t2).split("-");
        int day1, day2, h1, h2, min1, min2;
        day1 = Integer.parseInt(s1[0]);
        day2 = Integer.parseInt(s2[0]);
        h1 = Integer.parseInt(s1[1]);
        h2 = Integer.parseInt(s2[1]);
        min1 = Integer.parseInt(s1[2]);
        min2 = Integer.parseInt(s2[2]);
        return ((day1 - day2) * 24 + h1 - h2) * 60 + (min1 - min2);
    }
}
