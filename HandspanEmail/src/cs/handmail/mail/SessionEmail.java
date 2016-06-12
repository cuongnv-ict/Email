/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.mail;

import cs.handmail.file.AdminFile;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.AddressStringTerm;
import javax.swing.JOptionPane;

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
    private Folder inbox;
    private Folder sent;
    private Session smtpSession = null;
    private AdminFile adminFile;
    private boolean isAdmin;
    private Map<String, Map<String, Folder>> mapsession;

    public SessionEmail() {
        accuracyEmail = new AccuracyEmail();
        adminFile = new AdminFile();
        mapsession = new HashMap<>();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void checkAdmin() {
        isAdmin = email.equals(adminFile.readDataUser());
    }

    public Session getsmtpSession() {
        return smtpSession;
    }

    public Folder getInboxFolder() {
        return inbox;
    }

    public Store getStore() {
        return store;
    }

    public String getHost() {
        return hostmail;
    }

    public String getEmail() {
        return email;
    }

    public boolean connectIMAPS(String mail, String pass, String host, String port) {
        try {
            System.err.println(pass + ":" + mail);
            Properties pro = new Properties();
            pro.put("mail.imap.host", host);
            pro.put("mail.imap.port", port);
            pro.put("mail.store.protocol", "imap");
            pro.put("mail.imap.auth", "true");
            Session session = Session.getInstance(pro, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail, pass);
                }
            });
            store = session.getStore();
            store.connect(host, mail, pass);
            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            sent = inbox.getFolder("sent-mail");
            sent.open(Folder.READ_ONLY);
            email = mail;
            password = pass;
            hostmail = host;
            portmail = port;
            isAdmin = email.equals(adminFile.readDataUser());
            return true;
        } catch (NoSuchProviderException ex) {

            return false;
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Bạn chưa tại folder sent-mail", "LogIn", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean checkMail(String mail, String pass) {
        try {
            Properties prop = new Properties();
            prop.put("mail.imap.host", hostmail);
            prop.put("mail.imap.port", portmail);
            prop.put("mail.store.protocol", "imap");
            prop.put("mail.imap.auth", "true");
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail, pass);
                }
            });
            Store ss = session.getStore();
            ss.connect(hostmail, mail, pass);
            ss.close();
            return true;
        } catch (NoSuchProviderException ex) {
            // Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            // Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * *
     *
     * @return false is address not validate
     */
    public boolean checkAddressMail(String addressMail) {
        try {
            boolean isvali;
            InternetAddress internetAddress = new InternetAddress(addressMail);
            internetAddress.validate();
            return true;
        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, "email not validate");
            return false;
        }
    }

    public Session sendMail() {
        String mailPort = "25";
        Properties mProperties = System.getProperties();
        mProperties.put("mail.imap.host", hostmail);
        mProperties.put("mail.smtp.port", mailPort);
        mProperties.put("mail.smtp.auth", "true");
        mProperties.put("mail.store.protocol", "smtp");
        mProperties.put("mail.smtp.starttls.enable", "true");
        smtpSession = Session.getInstance(mProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        return smtpSession;
    }

    public void TimeOut() {
        for (String key : mapsession.keySet()) {
            Map m = mapsession.get(key);
            Folder in = (Folder) m.get("inbox");
            Folder se = (Folder) m.get("sent");
            if (in.isOpen() && se.isOpen()) {
                try {
                    in.getMessageCount();
                    se.getMessageCount();
                } catch (MessagingException ex) {
                    Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Map<String, Integer> addressEmail() {
        Map<String, Integer> mails = new TreeMap<>();
        try {
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
            messages = sent.getMessages();
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
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mails;
    }

    public Map<String, Message[]> statisticAddressEmail(Date d1, Date d2, String mail, String pass) {
        Map<String, Message[]> map = new HashMap<>();
        try {
            Folder in = null, se = null;
            Map<String, Folder> m = mapsession.get(mail);
            if (mapsession.get(mail) != null) {
                in = m.get("inbox");
                se = m.get("sent");
            }
            if (in != null && se != null && in.isOpen() && se.isOpen()) {

            } else {
                Properties prop = new Properties();
                prop.put("mail.imap.host", hostmail);
                prop.put("mail.imap.port", portmail);
                prop.put("mail.store.protocol", "imap");
                prop.put("mail.imap.auth", "true");
                Session session = Session.getInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail, pass);
                    }
                });
                Store ss = session.getStore();
                ss.connect(hostmail, mail, pass);
                in = ss.getFolder("Inbox");
                in.open(Folder.READ_ONLY);
                se = in.getFolder("sent-mail");
                se.open(Folder.READ_ONLY);
                m = new HashMap<>();
                m.put("inbox", in);
                m.put("sent", se);
                mapsession.put(mail, m);
            }

            SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yy");           
            String mindt = df1.format(d1);
            String maxdt = df1.format(d2);
            Date minDate = df1.parse(mindt);
            Date maxDate = df1.parse(maxdt);
            SearchTerm olderThan = new ReceivedDateTerm(ComparisonTerm.GT, minDate);
            SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.LT, maxDate);
            AddressStringTerm addressTerm = new AddressStringTerm("Inbox") {
                @Override
                public boolean match(Message msg) {
                    try {
                        boolean f1 = false;
                        boolean f2 = false;
                        for (Address a : msg.getFrom()) {
                            String m = accuracyEmail.extraEmail(a.toString());
                            if (email.equals(m)) {
                                f1 = true;
                            }
                            if (!accuracyEmail.isEmailHandspan(m)) {
                                f2 = true;
                            }
                        }
                        if (f2) {
                            return true;
                        }
                        if (f1) {
                            String sub = msg.getSubject().substring(0, 3).toUpperCase();
                            if (sub != null && sub.startsWith("FW")) {
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
            SearchTerm andTerm = new AndTerm(delete, olderThan);
            andTerm = new AndTerm(andTerm, newerThan);
            andTerm = new AndTerm(andTerm, addressTerm);
            Message messages[] = in.search(andTerm);
            map.put("inbox", messages);
            addressTerm = new AddressStringTerm("sent-mail") {
                @Override
                public boolean match(Message msg) {
                    try {
                        MimeMessage m = (MimeMessage) msg;
                        if (m.getHeader("In-Reply-To", "In-Reply-To") == null) {
                            return false;
                        }
                        boolean f1 = false;
                        boolean f2 = false;
                        for (Address a : msg.getRecipients(Message.RecipientType.TO)) {
                            String add = accuracyEmail.extraEmail(a.toString());
                            if (email.equals(add)) {
                                f1 = true;
                            }
                            if (!accuracyEmail.isEmailHandspan(add)) {
                                f2 = true;
                            }

                        }
                        if (f2) {
                            return true;
                        }
                        if (f1) {
                            if (msg.getRecipients(Message.RecipientType.CC) == null) {
                                return false;
                            }
                            for (Address a : msg.getRecipients(Message.RecipientType.CC)) {
                                String add = accuracyEmail.extraEmail(a.toString());
                                if (!accuracyEmail.isEmailHandspan(add)) {
                                    return true;
                                }
                            }
                        }
                    } catch (MessagingException ex) {
                        Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return false;
                }
            };
            andTerm = new AndTerm(delete, olderThan);
            andTerm = new AndTerm(andTerm, newerThan);
            andTerm = new AndTerm(andTerm, addressTerm);
            messages = se.search(andTerm);
            map.put("sent", messages);
        } catch (ParseException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public Message[] getMessageCustomer() {
        try {
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
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageStaff() {
        try {
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
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageSent() {
        try {
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), false);
            Message msg[] = sent.search(delete);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageDeleteInbox() {
        try {
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), true);
            Message msg[] = inbox.search(delete);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Message[] getMessageDeleteSent() {
        try {
            FlagTerm delete = new FlagTerm(new Flags(Flags.Flag.DELETED), true);
            Message msg[] = sent.search(delete);
            return msg;
        } catch (MessagingException ex) {
            Logger.getLogger(SessionEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
