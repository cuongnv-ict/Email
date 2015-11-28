/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.dailog;

import cs.handmail.file.DataUserFile;
import cs.handmail.mail.SessionEmail;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author Nguyen Van Cuong
 */
public class NewEmail extends javax.swing.JDialog {

    private SessionEmail sessionEmail;
    private String subject;
    private String addrTo;
    private String cc_addr;
    private String messageBody;
    private boolean isAttachFile = false;
    private String pathFileAttach;
    private Session session;
    private String userMail;
    private String passWord;
    private String fileName;
    private String hostMail;
    /**
     * Creates new form NewEmail
     */
    public NewEmail(java.awt.Frame parent, boolean modal,SessionEmail session) {
        super(parent, modal);
        initComponents();
        sessionEmail = session;
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 250);
        close.setVisible(false);
        path.setVisible(false);
        DataUserFile userFile = new DataUserFile();
        Vector<String> temp = userFile.readDataUser();
        userMail = temp.get(0);
        passWord = temp.get(1);
        hostMail = sessionEmail.getHost();
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
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                    JOptionPane.showMessageDialog(null,"send mail success" );
                    
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
        session = sessionEmail.sendMail();
    }
    
    /****
     * when click send mail this function called
     */
    
    
    MimeMessage setContentMail()
    {
       try{
           if(!addrTo.equals("")&&sessionEmail.checkAddressMail(addrTo))
           {
                MimeMessage message = new MimeMessage(session);
                message.setSubject(subject);
                message.setFrom(new InternetAddress(userMail));
                message.setRecipients(Message.RecipientType.TO, addrTo);
                if(isAttachFile)
                {
                    BodyPart bodymail = new MimeBodyPart();
                    bodymail.setText(messageBody);
                  //  bodymail.setContent(message,"text/html");
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(bodymail);
                    DataSource source = new FileDataSource(pathFileAttach);
                    BodyPart bodyAttach = new MimeBodyPart();
                    bodyAttach.setDataHandler(new DataHandler(source));
                    bodyAttach.setFileName(fileName);
                    multipart.addBodyPart(bodyAttach);
                    message.setContent(multipart);
                }else{
                    message.setContent(messageBody, "text/html");
                }
                return message;
           }else if(!cc_addr.equals("")&&sessionEmail.checkAddressMail(cc_addr)){
               
                MimeMessage message = new MimeMessage(session);
                message.setSubject(subject);
                message.setFrom(new InternetAddress(userMail));
                message.setRecipients(Message.RecipientType.CC, cc_addr);
                if(isAttachFile)
                {
                    BodyPart bodymail = new MimeBodyPart();
                    bodymail.setText(messageBody);
                    bodymail.setContent(message,"text/html");
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(bodymail);
                    DataSource source = new FileDataSource(pathFileAttach);
                    BodyPart bodyAttach = new MimeBodyPart();
                    bodyAttach.setDataHandler(new DataHandler(source));
                    bodyAttach.setFileName(fileName);
                    multipart.addBodyPart(bodyAttach);
                    message.setContent(multipart);
                }else{
                    message.setContent(messageBody, "text/html");
                }
                return message;
           }else{
                return null;
           }
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

        ta_message = new java.awt.TextArea();
        tf_addr = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tf_cc = new java.awt.TextField();
        jLabel3 = new javax.swing.JLabel();
        tf_subject = new java.awt.TextField();
        button1 = new java.awt.Button();
        attach = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        path = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("To:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("CC:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Subject:");

        tf_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_subjectActionPerformed(evt);
            }
        });

        button1.setBackground(java.awt.SystemColor.activeCaption);
        button1.setLabel("Send");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Attach-icon.png"))); // NOI18N
        attach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attachMouseClicked(evt);
            }
        });

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clsoe.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_addr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(button1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(attach)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(close)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(path, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_cc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ta_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_addr, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_cc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(ta_message, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(close, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(path, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_subjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_subjectActionPerformed

    private void attachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attachMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            //            String[] str = selectedFile.getAbsolutePath().split("\\");
            
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
        getTextFromUI();
        setAdapter();
        if(addrTo.equals("")||cc_addr.equals(""))
        {
            if(subject.equals(""))
            {
                int check = JOptionPane.showConfirmDialog(null, "your mail don't have dubject. Do you sure send this mail");
                if(check == JOptionPane.YES_OPTION)
                {
                    sendMailThread.start();
                     dispose();
                }
            }else{
                sendMailThread.start();
               dispose();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Your mail don't have address mail to");
        }
    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attach;
    private java.awt.Button button1;
    private javax.swing.JLabel close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel path;
    private java.awt.TextArea ta_message;
    private java.awt.TextField tf_addr;
    private java.awt.TextField tf_cc;
    private java.awt.TextField tf_subject;
    // End of variables declaration//GEN-END:variables
}
