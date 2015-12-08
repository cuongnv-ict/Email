/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.processtable;

import cs.handmail.mail.AccuracyEmail;
import java.awt.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Nguyen Van Cuong
 */
public class TableListAcount {

    private AccuracyEmail accuracyEmail;

    public TableListAcount() {
        accuracyEmail = new AccuracyEmail();
    }

    public void listAcount(JTable table, Map<String, Integer> map) {
        ArrayList<Object[]> data = new ArrayList();
        int count = 1;
        for (String key : map.keySet()) {
            Object[] str = new Object[4];
            str[0] = false;
            str[1] = count;
            str[2] = key;
            str[3] = "Đang kiểm tra ...";
            data.add(str);
            count++;
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

    public void statisticEmail(JTable table, Map<String, Integer> map, Map<String, Message[]> request, Map<String, Message[]> answer) {
        ArrayList<Object[]> data = new ArrayList();
        int count = 1;
        for (String key : map.keySet()) {
            Object[] str = new Object[6];
            str[0] = count;
            str[1] = key;
            str[2] = request.get(key).length;
            str[3] = answer.get(key).length;
            int[] time = avgTime(request.get(key), answer.get(key));
            str[4] = time[0];
            str[5] = String.valueOf(time[1] / 60 + ":" + time[1] % 60);
            data.add(str);
            count++;
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

    public void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public void statisticEmail(JTable table, Map<String, Message[]> msgs, String mail, int id) {
        Object[] str = new Object[6];
        str[0] = id;
        str[1] = mail;
        str[2] = msgs.get("inbox") == null ? 0 : msgs.get("inbox").length;
        str[3] = msgs.get("sent") == null ? 0 : msgs.get("sent").length;
        int[] time = avgTime(msgs);
        str[4] = time[0];
        str[5] = String.valueOf(time[1] / 60 + ":" + time[1] % 60);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(str);
    }

    public int[] avgTime(Map<String, Message[]> msgs) {
        int time[] = new int[2];
        time[0] = 0;
        time[1] = 0;
        ArrayList<Message> re = new ArrayList<>();
        ArrayList<Message> an = new ArrayList<>();
        if (msgs.get("inbox") != null) {
            re.addAll(Arrays.asList(msgs.get("inbox")));
        }
        if (msgs.get("sent") != null) {
            an.addAll(Arrays.asList(msgs.get("sent")));
        }
        int total = 0;
        for (Message reMSG : re) {
            try {
                MimeMessage msg1 = (MimeMessage) reMSG;
                String idMSG = msg1.getMessageID();
                Message m = null;
                Date d1 = null;
                Date d2 = null;
                int value = 0;
                boolean isF = true;
                d1 = reMSG.getReceivedDate();
                for (Message anMSG : an) {
                    MimeMessage msg2 = (MimeMessage) anMSG;
                    String rep = msg2.getHeader("In-Reply-To", "In-Reply-To");
                    if (rep.equals(idMSG)) {
                        d2 = anMSG.getSentDate();
                        if (d1 == null) {
                            d1 = new Date();
                            d2 = new Date();
                        }
                        if (d2 == null) {
                            d2 = new Date();
                        }
                        value = subTime(d2, d1);
                        isF = false;
                        m = anMSG;
                        break;
                    }
                }
                if (isF) {
                    if (d1 == null) {
                        d1 = new Date();
                        d2 = new Date();
                    }
                    if (d2 == null) {
                        d2 = new Date();
                    }
                    value = subTime(d2, d1);
                    if (value < (24 * 60)) {
                        time[0]++;
                    }
                } else {
                    an.remove(m);
                }
                total += value;
            } catch (MessagingException ex) {
                Logger.getLogger(TableListAcount.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        time[1] = re.isEmpty() ? 0 : total / re.size();
        return time;
    }

    public int[] avgTime(Message[] request, Message[] answer) {
        ArrayList<Message> re = new ArrayList<>();
        ArrayList<Message> an = new ArrayList<>();
        int time[] = new int[2];
        time[0] = 0;
        time[1] = 0;
        re.addAll(Arrays.asList(request));
        an.addAll(Arrays.asList(answer));
        Collections.sort(re, (Message msg1, Message msg2) -> {
            try {
                return msg1.getSentDate().compareTo(msg2.getSentDate());
            } catch (MessagingException ex) {
                Logger.getLogger(TableListAcount.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        });
        Collections.sort(an, (Message msg1, Message msg2) -> {
            try {
                return msg1.getReceivedDate().compareTo(msg2.getReceivedDate());
            } catch (MessagingException ex) {
                Logger.getLogger(TableListAcount.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
        });
        int total = 0;

        for (Message msg : re) {
            try {
                String address = "";
                Object content = msg.getContent();
                Multipart mp = (Multipart) content;
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart bodyPart = mp.getBodyPart(i);
                    if (bodyPart.isMimeType("text/plain")) {
                        String s = (String) bodyPart.getContent();
                        s = s.trim();
                        if (!s.contains("Forwarded message from")) {
                            continue;
                        }
                        String[] ds = s.split("\n");
                        ds = ds[0].split(" ");
                        address = ds[ds.length - 2];
                        break;
                    }
                }

                boolean flags = false;
                Message m = null;
                for (Message ms : an) {
                    for (Address a : ms.getAllRecipients()) {
                        if (address.equals(accuracyEmail.extraEmail(a.toString()))) {
                            int value = subTime(ms.getReceivedDate(), msg.getSentDate());
                            if (value < (24 * 60)) {
                                time[0]++;
                            }
                            total += value;
                            m = ms;
                            flags = true;
                            break;
                        }
                        if (flags) {
                            break;
                        }
                    }
                }
                if (!flags) {
                    total += subTime(new Date(), msg.getSentDate());
                } else {
                    an.remove(m);
                }
            } catch (IOException | MessagingException ex) {
                Logger.getLogger(TableListAcount.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        time[1] = re.isEmpty() ? 0 : total / re.size();
        return time;
    }

    public int subTime(Date t1, Date t2) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-HH-mm-ss");
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
