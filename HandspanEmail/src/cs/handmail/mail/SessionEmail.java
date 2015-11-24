/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.mail;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import cs.handmail.mail.AccuracyEmail;
import cs.handmail.panelmail.InBox;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.mail.Flags;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.AddressStringTerm;
import javax.mail.search.AddressTerm;

/**
 *
 * @author Nguyen Van Cuong
 */
public class SessionEmail {

    private String email;
    private String password;
    private String hostmail;
    private String portmail;
    private Store store;
    private AccuracyEmail accuracyEmail;

    public SessionEmail() {
        accuracyEmail = new AccuracyEmail();
    }

    public Store getStore() {
        return store;
    }

    public boolean connectIMAPS(String mail, String pass, String host, String port) {
        try {
            Properties pro = System.getProperties();
            pro.put("mail.imap.host", host);
            pro.put("mail.imap.port", port);
            pro.put("mail.store.protocol", "imap");
            pro.put("mail.imap.auth", "true");
            Session session = Session.getDefaultInstance(pro, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail, pass);
                }
            });
            store = session.getStore();
            store.connect(host, mail, pass);
            email = mail;
            password = pass;
            hostmail = host;
            portmail = port;
            return true;
        } catch (NoSuchProviderException ex) {
            return false;
        } catch (MessagingException ex) {
            return false;
        }
    }

    public Map<String, Integer> addressEmail() {
        Map<String, Integer> mails = new TreeMap<String, Integer>();
        try {
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            Message messages[] = inbox.getMessages();
            for (Message message : messages) {
                for (Address a : message.getFrom()) {
                    String m = accuracyEmail.extraEmail(a.toString());
                    if (accuracyEmail.isEmailHandspan(m)) {
                        if (!email.equals(m)) {
                            mails.put(m, 1);
                        }
                    }
                }
            }
            inbox = inbox.getFolder("sent-mail");
            inbox.open(Folder.READ_ONLY);
            messages = inbox.getMessages();
            for (Message message : messages) {
                for (Address a : message.getFrom()) {
                    String m = accuracyEmail.extraEmail(a.toString());
                    if (accuracyEmail.isEmailHandspan(m)) {
                        if (!email.equals(m)) {
                            mails.put(m, 1);
                        }
                    }
                }
            }
//            inbox.close(true);
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mails;
    }

    public Map<String, Message[]> statisticAddressEmail(int month, int year, Map<String, Integer> mails, boolean isInbox) {
        Map<String, Message[]> map = new HashMap<>();
        try {
            SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yy");
            String mindt = String.valueOf(month + "/01/" + year);
            month++;
            String maxdt = String.valueOf(month + "/01/" + year);
            Date minDate = df1.parse(mindt);
            Date maxDate = df1.parse(maxdt);
            SearchTerm olderThan = new ReceivedDateTerm(ComparisonTerm.GT, minDate);
            SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.LT, maxDate);
            Folder inbox = store.getFolder("Inbox");
            Folder sent = null;
            if (!isInbox) {
                sent = inbox;
                inbox = inbox.getFolder("sent-mail");
            }
            inbox.open(Folder.READ_ONLY);
            for (String key : mails.keySet()) {

                AddressStringTerm addressTerm = new AddressStringTerm("Inbox") {
                    @Override
                    public boolean match(Message msg) {
                        if (isInbox) {
                            try {
                                for (Address a : msg.getFrom()) {
                                    String m = accuracyEmail.extraEmail(a.toString());
                                    if (m.equals(key)) {
                                        return true;
                                    }
                                }
                            } catch (MessagingException ex) {
                                Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                Object content = msg.getContent();
                                if (content instanceof String) {
                                    return false;
                                }
                                for (Address a : msg.getAllRecipients()) {
                                    String m = accuracyEmail.extraEmail(a.toString());
                                    if (m.equals(key)) {
                                        return true;
                                    }
                                }
                            } catch (MessagingException ex) {
                                Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        return false;
                    }
                };
                FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), false);
                SearchTerm andTerm = new AndTerm(delete, olderThan);
                andTerm = new AndTerm(andTerm, newerThan);
                andTerm = new AndTerm(andTerm, addressTerm);
                Message messages[] = inbox.search(andTerm);
                map.put(key, messages);
            }
            inbox.close(true);
            if(!isInbox){
                sent.close(true);
            }
            return map;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public Message[] getMessageCustomer() {
        try {
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            AddressStringTerm addressTerm = new AddressStringTerm("Inbox") {
                @Override
                public boolean match(Message msg) {
                    try {
                        for (Address a : msg.getFrom()) {
                            String m = accuracyEmail.extraEmail(a.toString());
                            if (accuracyEmail.isEmailHandspan(m)) {
                                return false;
                            }

                        }
                    } catch (MessagingException ex) {
                        Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return true;
                }
            };
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), false);
            SearchTerm andTerm = new AndTerm(delete, addressTerm);
            Message msg[] = inbox.search(andTerm);
            inbox.close(true);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageStaff() {
        try {
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            AddressStringTerm addressTerm = new AddressStringTerm("Inbox") {
                @Override
                public boolean match(Message msg) {
                    try {
                        for (Address a : msg.getFrom()) {
                            String m = accuracyEmail.extraEmail(a.toString());
                            if (accuracyEmail.isEmailHandspan(m)) {
                                return true;
                            }

                        }
                    } catch (MessagingException ex) {
                        Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return false;
                }
            };
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), false);
            SearchTerm andTerm = new AndTerm(delete, addressTerm);
            Message msg[] = inbox.search(andTerm);
            inbox.close(true);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageSent() {
        try {
            Folder inbox = store.getFolder("Inbox");
            Folder sent = inbox.getFolder("sent-mail");
            inbox.open(Folder.READ_ONLY);
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), false);
            Message msg[] = sent.search(delete);
            inbox.close(true);
            sent.close(true);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageDeleteInbox() {
        try {
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), true);
            Message msg[] = inbox.search(delete);
            inbox.close(true);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageDeleteSent() {
        try {
            Folder inbox = store.getFolder("Inbox");
            Folder sent = inbox.getFolder("sent-mail");
            inbox.open(Folder.READ_ONLY);
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), true);
            Message msg[] = sent.search(delete);
            inbox.close(true);
            sent.close(true);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
