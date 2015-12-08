/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.dailog;

import cs.handmail.file.DataUserFile;
import cs.handmail.mail.SessionEmail;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MimeType;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sound.midi.Patch;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Part;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
/**
 *
 * @author Nguyen Van Cuong
 */
public class NewEmail extends javax.swing.JDialog {

    private SessionEmail sessionEmail;
    private String subject;
    private String addrTo;
    private String cc_addr="";
    private String messageBody;
    private boolean isAttachFile = false;
    private String pathFileAttach;
    private Session session;
    private String userMail;
    private String passWord;
    private String fileName;
    private String hostMail;
    private Message message;
    private boolean isReply;
    private boolean isfoward;
    private Folder inbox;
    private Folder sent;
    private String messInfo;
    private MimeBodyPart downloadPart;
    private boolean isSended=false;
    /**
     * Creates new form NewEmail
     */
    public NewEmail(java.awt.Frame parent, boolean modal,SessionEmail session) {
        super(parent, modal);
        initComponents();
        wait.setVisible(false);
        sessionEmail = session;
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 250);
        close.setVisible(false);
        path.setVisible(false);
        DataUserFile userFile = new DataUserFile();
        Vector<String> temp = userFile.readDataUser();
        userMail = temp.get(0);
        passWord = temp.get(1);
        hostMail = sessionEmail.getHost();
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        
    }

    public NewEmail(java.awt.Frame parent, boolean modal,SessionEmail session,Message mess,String messInfo,MimeBodyPart downloadPart,boolean reply,boolean foward) {
        super(parent, modal);
        initComponents();
        wait.setVisible(false);
        sessionEmail = session;
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 250);
        close.setVisible(false);
        path.setVisible(false);
        DataUserFile userFile = new DataUserFile();
        Vector<String> temp = userFile.readDataUser();
        userMail = temp.get(0);
        passWord = temp.get(1);
        hostMail = sessionEmail.getHost();
        this.message = mess;
        isReply = reply;
        isfoward = foward;
        this.messInfo = messInfo;
        if(reply)
        {
            setDataForReply();
            
        }
        
        else if (foward)
        {
            setDataForFoward();
            ta_message.setEditable(false);
            attach.setVisible(false);
            this.downloadPart = downloadPart;
        }
        
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        
    }
    /**
     * set data for mail rep
     */
    void setDataForReply() 
    {
        try{
            tf_subject.setText(message.getSubject());
            tf_addr.setText(message.getReplyTo()[0].toString());
            ta_message.setText(processDataMessReplyFoward());
///            ta_message.setCaretPosition( ta_message.getCaretPosition() + 1 );
        }catch(MessagingException ex)
        {
            JOptionPane.showMessageDialog(null, "mail eror");
            ex.printStackTrace();
        }
    }
    
    void setDataForFoward()
    {
        try{
            tf_subject.setText(message.getSubject());
            ta_message.setText(messInfo);
            ta_message.setText(processDataMessReplyFoward());
//            ta_message.setCaretPosition( ta_message.getCaretPosition() + 1 );
        }catch(MessagingException ex)
        {
            JOptionPane.showMessageDialog(null, "mail eror");
            ex.printStackTrace();
        }
    }
    
    
    /****
     * thread for send mail
     */
    
    Thread sendMailThread = new Thread(new Runnable() {

        @Override
        public void run() {
             try{
                    Transport transport = session.getTransport("smtp");
                    transport.connect(hostMail,25,userMail,passWord);
                    Message message = setContentMail();
                    message.setFlag(Flags.Flag.SEEN, true);
                    sent.appendMessages(new Message[]{message});
                    transport.sendMessage(message, message.getAllRecipients());
                    JOptionPane.showMessageDialog(null,"send mail success" );
                    transport.close();
                    isSended = true;
                    wait.setVisible(false);
                    ta_message.setVisible(true);
                    
            }catch(RuntimeException ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "can't send mail, check your network");
            }catch(MessagingException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "can't send mail, check your network");
            }
        }
    });
    

    
    /***
     * reply
     */
    Thread replyMailThread = new Thread(new Runnable() {

        @Override
        public void run() {
                try{
                    Message replyMessage = new MimeMessage(session);
                    replyMessage = (MimeMessage) message.reply(false);
                    replyMessage.setFrom(new InternetAddress(userMail));
                    if(isAttachFile)
                    {
                        byte[] attachFileData;
                        Path path = Paths.get(pathFileAttach);
                        attachFileData = Files.readAllBytes(path);
                        String[] fileHandle = fileName.split(Pattern.quote("."));
                        MimeTypeJavaMail mime = new MimeTypeJavaMail();
                        String nameContent =mime.getMimteString(fileHandle[1]);
                        Multipart multipart = new MimeMultipart("mixed");
                        // set text parts
                        MimeBodyPart textPlainPart = new MimeBodyPart();
                        textPlainPart.setContent(messageBody,mime.getMimteString("text"));
                        multipart.addBodyPart(textPlainPart);
                        // set body parts
                        MimeBodyPart attachFilePart = new MimeBodyPart();
                        DataSource source = new FileDataSource(pathFileAttach);
                        attachFilePart.setDataHandler(new DataHandler(source));
                        attachFilePart.setFileName(fileName);
                        attachFilePart.setDisposition("attachment");
                        multipart.addBodyPart(attachFilePart);
                        replyMessage.setContent(multipart);
                    }else{
                        replyMessage.setText(ta_message.getText());
                    }
                    replyMessage.setReplyTo(message.getReplyTo());
                    if(!cc_addr.equals(""))
                    replyMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc_addr));
                    replyMessage.setFlag(Flags.Flag.SEEN, true);
                    replyMessage.setSentDate(new Date());            
                    sent.appendMessages(new Message[]{replyMessage});
                    Transport transport = session.getTransport("smtp");
                    transport.connect(hostMail,25,userMail,passWord);
                    transport.sendMessage(replyMessage, replyMessage.getAllRecipients());
                    transport.close();
                    JOptionPane.showMessageDialog(null,"send mail success" );
                    wait.setVisible(false);
                    ta_message.setVisible(true);
                    isSended = true;
                }catch(RuntimeException ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "can't send mail, check your network");
                }catch(MessagingException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "can't send mail, check your network");
                }catch(IOException ex){
                    ex.printStackTrace();
                }
        }
    });
    /***
     * Thread for foward
     */
    Thread fowardMailThread = new Thread(new Runnable() {

        @Override
        public void run() {
                try{
                    String from = userMail;
                    Message messFoward = new MimeMessage(session);
                    messFoward.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(addrTo));
                    messFoward.setSubject("Fwd: " + message.getSubject());
                    messFoward.setFrom(new InternetAddress(from));
                    messFoward.setSentDate(new Date());
                    if(downloadPart!=null)
                    {
                        MimeTypeJavaMail mime = new MimeTypeJavaMail();
                        Multipart multipart = new MimeMultipart("mixed");
                        // set text parts
                        MimeBodyPart textPlainPart = new MimeBodyPart();
                        textPlainPart.setContent(messageBody,mime.getMimteString("text"));
                        multipart.addBodyPart(textPlainPart);
                        // set body parts
                        MimeBodyPart attachFilePart = new MimeBodyPart();
                        //DataSource source = new FileDataSource();
//                        InputStream input = downloadPart.getInputStream();
                        DataSource datasource = downloadPart.getDataHandler().getDataSource();
                        attachFilePart.setDataHandler(new DataHandler(datasource));
                        attachFilePart.setFileName(downloadPart.getFileName());
                        attachFilePart.setDisposition("attachment");
                        multipart.addBodyPart(attachFilePart);
                        messFoward.setContent(multipart);
                    }else{
                        messFoward.setText(messageBody);
                    }
                    messFoward.saveChanges();
                    //sent.appendMessages(new Message[]{messFoward});
                    Transport transport = session.getTransport("smtp");
                    transport.connect(hostMail,25,userMail,passWord);
                    transport.sendMessage(messFoward, messFoward.getAllRecipients());
                    transport.close();
                    JOptionPane.showMessageDialog(null,"send mail success" );
                    wait.setVisible(false);
                    ta_message.setVisible(true);
                    isSended = true;
                }catch(RuntimeException ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "can't send mail, check your network");
                }catch(MessagingException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "can't send mail, check your network");
                }
        }
    });
    
    
    /***
     * get data from text field and text area
     */
    void getTextFromUI()
    {
        subject = tf_subject.getText()+"";
        addrTo  = tf_addr.getText()+"";
        cc_addr = tf_cc.getText() + "";
        messageBody = ta_message.getText() + "";
    }
    
    /****
     * setAdapter for sendMail
     */
    void setAdapter()
    {
        session = sessionEmail.getsmtpSession();
        if(session==null)
        session = sessionEmail.sendMail();
        inbox = sessionEmail.getInboxFolder();
        try {
            if(!inbox.isOpen())
            {
                inbox = sessionEmail.getStore().getFolder("Inbox");
                inbox.open(Folder.READ_ONLY);
                sent = inbox.getFolder("sent-mail");
                sent.open(Folder.READ_WRITE);
            }else{
                sent = inbox.getFolder("sent-mail");
                sent.open(Folder.READ_WRITE);
            }
        } catch (MessagingException ex) {
            Logger.getLogger(NewEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /****
     * when click send mail this function called
     */
    
    
    MimeMessage setContentMail()
    {
       try{
           if(!addrTo.equals("")&&sessionEmail.checkAddressMail(addrTo)&&cc_addr.equals(""))
           {
                MimeMessage message = new MimeMessage(session);
                message.setSubject(subject);
                message.setFrom(new InternetAddress(userMail));
                message.setRecipients(Message.RecipientType.TO, addrTo);
                message.setSentDate(new Date());
                if(isAttachFile)
                {
                    byte[] attachFileData;
                    Path path = Paths.get(pathFileAttach);
                    attachFileData = Files.readAllBytes(path);
                    String[] fileHandle = fileName.split(Pattern.quote("."));
                    MimeTypeJavaMail mime = new MimeTypeJavaMail();
                    String nameContent =mime.getMimteString(fileHandle[1]);
                    Multipart multipart = new MimeMultipart("mixed");
                    // set text parts
                    MimeBodyPart textPlainPart = new MimeBodyPart();
                    textPlainPart.setContent(messageBody,mime.getMimteString("text"));
                    multipart.addBodyPart(textPlainPart);
                    // set body parts
                    MimeBodyPart attachFilePart = new MimeBodyPart();
                    DataSource source = new FileDataSource(pathFileAttach);
                    attachFilePart.setDataHandler(new DataHandler(source));
                    attachFilePart.setFileName(fileName);
                    attachFilePart.setDisposition("attachment");
                    multipart.addBodyPart(attachFilePart);
                    message.setContent(multipart);
                }else{
                    message.setText(messageBody);
                }
                message.saveChanges();
                return message;
           }else if(!cc_addr.equals("")&&sessionEmail.checkAddressMail(cc_addr)&&addrTo.equals("")){
               
                MimeMessage message = new MimeMessage(session);
                message.setSubject(subject);
                message.setFrom(new InternetAddress(userMail));
                message.setRecipients(Message.RecipientType.CC, cc_addr);
                message.setSentDate(new Date());
                if(isAttachFile)
                {
                    byte[] attachFileData;
                    Path path = Paths.get(pathFileAttach);
                    attachFileData = Files.readAllBytes(path);
                    String[] fileHandle = fileName.split(Pattern.quote("."));
                    MimeTypeJavaMail mime = new MimeTypeJavaMail();
                    String nameContent =mime.getMimteString(fileHandle[1]);
                    Multipart multipart = new MimeMultipart("mixed");
                    // set text parts
                    MimeBodyPart textPlainPart = new MimeBodyPart();
                    textPlainPart.setContent(messageBody,mime.getMimteString("text"));
                    multipart.addBodyPart(textPlainPart);
                    // set body parts
                    MimeBodyPart attachFilePart = new MimeBodyPart();
                    DataSource source = new FileDataSource(pathFileAttach);
                    attachFilePart.setDataHandler(new DataHandler(source));
                    attachFilePart.setFileName(fileName);
                    attachFilePart.setDisposition("attachment");
                    multipart.addBodyPart(attachFilePart);
                    message.setContent(multipart);
                }else{
                    message.setText(messageBody);
                }
                message.saveChanges();
                return message;
           }else if(sessionEmail.checkAddressMail(addrTo)&&sessionEmail.checkAddressMail(cc_addr)){
              
               
                MimeMessage message = new MimeMessage(session);
                message.setSubject(subject);
                message.setFrom(new InternetAddress(userMail));
//                message.setRecipients(Message.RecipientType.CC, cc_addr);
//                message.setRecipients(Message.RecipientType.TO, addrTo);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(addrTo) );
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc_addr));
                
                message.setSentDate(new Date());
                if(isAttachFile)
                {
                    byte[] attachFileData;
                    Path path = Paths.get(pathFileAttach);
                    attachFileData = Files.readAllBytes(path);
                    String[] fileHandle = fileName.split(Pattern.quote("."));
                    MimeTypeJavaMail mime = new MimeTypeJavaMail();
                    String nameContent =mime.getMimteString(fileHandle[1]);
                    Multipart multipart = new MimeMultipart("mixed");
                    // set text parts
                    MimeBodyPart textPlainPart = new MimeBodyPart();
                    textPlainPart.setContent(messageBody,mime.getMimteString("text"));
                    multipart.addBodyPart(textPlainPart);
                    // set body parts
                    MimeBodyPart attachFilePart = new MimeBodyPart();
                    DataSource source = new FileDataSource(pathFileAttach);
                    attachFilePart.setDataHandler(new DataHandler(source));
                    attachFilePart.setFileName(fileName);
                    attachFilePart.setDisposition("attachment");
                    multipart.addBodyPart(attachFilePart);
                    message.setContent(multipart);
                }else{
                    message.setText(messageBody);
                }
                message.saveChanges();
                return message;
               
               
           }
           return null;
       }catch(Exception ex)
       {
           ex.printStackTrace();
           return null;
       }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wait = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        button1 = new java.awt.Button();
        attach = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        path = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_message = new javax.swing.JTextArea();
        tf_subject = new javax.swing.JTextField();
        tf_cc = new javax.swing.JTextField();
        tf_addr = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(460, 465));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        wait.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/wait1.gif"))); // NOI18N
        getContentPane().add(wait, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 390, 270));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("To:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 66, 32));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("CC:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 51, 66, 32));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Subject:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 32));

        button1.setBackground(java.awt.SystemColor.activeCaption);
        button1.setLabel("Send");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        getContentPane().add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 67, 25));

        attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Attach-icon.png"))); // NOI18N
        attach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attachMouseClicked(evt);
            }
        });
        getContentPane().add(attach, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, -1, -1));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clsoe.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, -1, 25));
        getContentPane().add(path, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 395, 304, 25));

        ta_message.setColumns(20);
        ta_message.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ta_message.setLineWrap(true);
        ta_message.setRows(5);
        jScrollPane1.setViewportView(ta_message);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 450, 300));

        tf_subject.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        getContentPane().add(tf_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 92, 370, 32));

        tf_cc.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tf_cc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_ccActionPerformed(evt);
            }
        });
        getContentPane().add(tf_cc, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 53, 370, 31));

        tf_addr.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        getContentPane().add(tf_addr, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 13, 370, 32));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void attachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attachMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path.setText(selectedFile.getName());
            pathFileAttach = selectedFile.getAbsolutePath();
            fileName = selectedFile.getName();
            close.setVisible(true);
            path.setVisible(true);
            isAttachFile = true;
            
        }
    }//GEN-LAST:event_attachMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        // TODO add your handling code here:
        close.setVisible(false);
        path.setVisible(false);
        isAttachFile = false;
    }//GEN-LAST:event_closeMouseClicked

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
//        sessionEmail.sendMail("nvcuongbkhnict@gmail.com",null,"Send Mail", "Success");
        if(isfoward)
        {
            getTextFromUI();
            if(sessionEmail.checkAddressMail(addrTo))
            {
                setAdapter();
                fowardMailThread.start();
//                dispose();
              //  ta_message.setVisible(false);
                wait.setVisible(true);
            }
            
        }
        else if(isReply)
        {
            getTextFromUI();
            setAdapter();
            replyMailThread.start();
           // ta_message.setVisible(false);
            wait.setVisible(true);
//            dispose();
        }
        else{
            getTextFromUI();
            setAdapter();
            if(!(addrTo.equals("")&&cc_addr.equals(""))||!(addrTo==null&&cc_addr==null))
            {
                if(subject.equals(""))
                {
                    int check = JOptionPane.showConfirmDialog(null, "your mail don't have dubject. Do you sure send this mail");
                    if(check == JOptionPane.YES_OPTION)
                    {
                        sendMailThread.start();
//                         dispose();
                     //   ta_message.setVisible(false);
                        wait.setVisible(true);
                    }
                }else{
                    sendMailThread.start();
                  //  dispose();
                //    ta_message.setVisible(false);
                    wait.setVisible(true);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Your mail don't have address mail to");
            }
        }
    }//GEN-LAST:event_button1ActionPerformed

    private void tf_ccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_ccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_ccActionPerformed

    
    String processDataMessReplyFoward(){
        try {
            if(isReply)
            {
                    String mess = "";
//                    String mess= "Quoting " + message.getReplyTo()[0].toString() + ":" +System.getProperty("line.separator")+ "  " + System.getProperty("line.separator");
//                    messInfo = messInfo.replace(System.getProperty("line.separator"),System.getProperty("line.separator") + "    " + ">");
//                    mess = mess  + "    " + ">" + messInfo;
                    return mess;
            }
            else if(isfoward)
            {
                    String mess = "----- Forwarded message from " + message.getFrom()[0].toString() + "-----";
                    mess += System.getProperty("line.separator");
                    mess += System.getProperty("line.separator") + "Date:" +" "+ message.getSentDate();
                    mess += System.getProperty("line.separator") + "From:" +" "+ message.getFrom()[0].toString();
                    mess += System.getProperty("line.separator") + "Reply-To:" +" "+ message.getReplyTo()[0].toString();
                    mess += System.getProperty("line.separator") + "Subject:" +" "+ message.getSubject();
                    mess += System.getProperty("line.separator") + "To:" +" "+ userMail;
                    mess += System.getProperty("line.separator") + " " +System.getProperty("line.separator");
                    mess += messInfo;
                    mess += System.getProperty("line.separator");
                    mess += "----- End forwarded message -----";
                    return mess;
            }  
        } catch (MessagingException ex) {
                Logger.getLogger(NewEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attach;
    private java.awt.Button button1;
    private javax.swing.JLabel close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel path;
    private javax.swing.JTextArea ta_message;
    private javax.swing.JTextField tf_addr;
    private javax.swing.JTextField tf_cc;
    private javax.swing.JTextField tf_subject;
    private javax.swing.JLabel wait;
    // End of variables declaration//GEN-END:variables
}
