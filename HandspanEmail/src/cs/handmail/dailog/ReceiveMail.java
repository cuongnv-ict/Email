/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.handmail.dailog;

import cs.handmail.mail.SessionEmail;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.stream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
//import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

/**
 *
 * @author Venus-NS
 */
public class ReceiveMail extends javax.swing.JDialog {

    private String pathFolder;
    private Message message;
    private SessionEmail sessionEmail;
    private String subject;
    private String body;
    private String dateSend;
    private String addFrom;
    private MimeBodyPart downloadPart=null;
    private boolean isAttachFile=false;
    /**
     * Creates new form ReceiveMail
     */
    public ReceiveMail(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public ReceiveMail(java.awt.Frame parent, boolean modal, Message message, SessionEmail session){
        super(parent, modal);
        try {
            initComponents();
            this.message = message;
            this.sessionEmail = session;
            updateData();
            if(isAttachFile) attach.setVisible(true);
            else attach.setVisible(false);
            message.setFlag(Flags.Flag.SEEN, true);
        } catch (MessagingException ex) {
            Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Thread downloadFile = new Thread(new Runnable() {

        @Override
        public void run() {
                 
            try {
                downloadPart.saveFile(pathFolder+ File.separator + downloadPart.getFileName());
                JOptionPane.showMessageDialog(null, "Download Complete");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Download fail");
                Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                JOptionPane.showMessageDialog(null, "Download fail");
                Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
    
    void updateData()
    {
        try{
            subject = message.getSubject();
            dateSend = message.getSentDate().toString();
            Address[] adress = message.getFrom();
            addFrom = adress[0].toString();
            tf_From.setText(addFrom);
            tf_date.setText(dateSend);
            tf_subject.setText(subject);
            String  contentType = message.getContentType();
            String  messageContent = "";
            if(contentType.contains("multipart")){
                    Multipart multipart =(Multipart) message.getContent();
                    int num = multipart.getCount();
                    for(int j = 0; j< num ; j++){
                        MimeBodyPart temp =(MimeBodyPart) multipart.getBodyPart(j);
                        System.out.println(temp.getDisposition());
                        if(Part.ATTACHMENT.equalsIgnoreCase(temp.getDisposition())){
                            downloadPart = temp;
                            isAttachFile = true;
                            break;
                        }else if(temp.getContentType().contains("text/plain")&&temp.getContentType().contains("plain"))
                        {
                            body =(String) temp.getContent();
                            ta_message.setText(body);
                        }else if(temp.getContentType().contains("text/html")&&temp.getContentType().contains("html"))
                        {
                            body =(String) temp.getContent();
//                            String plaintext = Jsoup.parse(body).text();
                            String plaintext = new HtmlToPlainText().getPlainText(Jsoup.parse(body));
                            ta_message.setText(plaintext);
                            

                        }
                    }
                }
            else {
                if(message.getContentType()!=null&&message.getContentType().contains("text/plain"))
                {
                    body =(String) message.getContent();
                    ta_message.setText(body);
                }else if(message.getContentType()!=null&&message.getContentType().contains("text/html")){
                    body =(String) message.getContent();
                    String plaintext = new HtmlToPlainText().getPlainText(Jsoup.parse(body));
                    ta_message.setText(plaintext);
                }
                
                
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
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

        jLabel1 = new javax.swing.JLabel();
        tf_From = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tf_date = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_subject = new javax.swing.JTextField();
        attach = new javax.swing.JLabel();
        reply = new javax.swing.JButton();
        foward = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_message = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(420, 478));
        setPreferredSize(new java.awt.Dimension(480, 500));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setLabelFor(tf_From);
        jLabel1.setText("From :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 71, -1));

        tf_From.setEditable(false);
        tf_From.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        getContentPane().add(tf_From, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 11, 367, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setLabelFor(tf_date);
        jLabel2.setText("Date :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 46, 71, -1));

        tf_date.setEditable(false);
        tf_date.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        getContentPane().add(tf_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 46, 367, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setLabelFor(tf_date);
        jLabel3.setText("Subject:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 81, -1, -1));

        tf_subject.setEditable(false);
        tf_subject.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        getContentPane().add(tf_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 81, 367, 30));

        attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Attach-icon.png"))); // NOI18N
        attach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attachMouseClicked(evt);
            }
        });
        getContentPane().add(attach, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 422, 52, 56));

        reply.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reply.setText("Reply");
        reply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replyActionPerformed(evt);
            }
        });
        getContentPane().add(reply, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 441, 70, 25));

        foward.setText("Foward");
        foward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fowardActionPerformed(evt);
            }
        });
        getContentPane().add(foward, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 442, 70, 25));

        jButton3.setText("Cancle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 442, 70, 25));

        ta_message.setColumns(20);
        ta_message.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ta_message.setLineWrap(true);
        ta_message.setRows(5);
        ta_message.setWrapStyleWord(true);
        ta_message.setMaximumSize(new java.awt.Dimension(164, 2147483647));
        jScrollPane1.setViewportView(ta_message);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, 443, 299));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void attachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attachMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
           File selectedFile = fileChooser.getSelectedFile();
            pathFolder = selectedFile.getAbsolutePath();
            downloadFile.start();
            JOptionPane.showMessageDialog(null, "download complete");
        }
    }//GEN-LAST:event_attachMouseClicked

    private void replyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replyActionPerformed
        // TODO add your handling code here:
        NewEmail email = new NewEmail(null, false, sessionEmail,message,ta_message.getText(),null,true,false);
        email.show();
    }//GEN-LAST:event_replyActionPerformed

    private void fowardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fowardActionPerformed
        // TODO add your handling code here:
        NewEmail email = new NewEmail(null, false, sessionEmail,message,ta_message.getText(), downloadPart,false,true);
        email.show();
    }//GEN-LAST:event_fowardActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attach;
    private javax.swing.JButton foward;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton reply;
    private javax.swing.JTextArea ta_message;
    private javax.swing.JTextField tf_From;
    private javax.swing.JTextField tf_date;
    private javax.swing.JTextField tf_subject;
    // End of variables declaration//GEN-END:variables
}
