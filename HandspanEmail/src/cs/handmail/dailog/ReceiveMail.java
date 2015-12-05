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
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.htmlcleaner.TagNode;
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
        initComponents();
        this.message = message;
        this.sessionEmail = session;
        updateData();
        if(isAttachFile) attach.setVisible(true);
        else attach.setVisible(false);
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
        ta_message = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setLabelFor(tf_From);
        jLabel1.setText("From :");

        tf_From.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setLabelFor(tf_date);
        jLabel2.setText("Date :");

        tf_date.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setLabelFor(tf_date);
        jLabel3.setText("Subject:");

        tf_subject.setEditable(false);

        attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Attach-icon.png"))); // NOI18N
        attach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attachMouseClicked(evt);
            }
        });

        reply.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reply.setText("Reply");
        reply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replyActionPerformed(evt);
            }
        });

        foward.setText("Foward");
        foward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fowardActionPerformed(evt);
            }
        });

        jButton3.setText("Cancle");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(5, 5, 5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tf_From, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                                .addComponent(tf_date)
                                .addComponent(tf_subject))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(attach)
                            .addGap(190, 190, 190)
                            .addComponent(reply, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                            .addGap(5, 5, 5)
                            .addComponent(foward, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 7, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ta_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf_From, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tf_subject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ta_message, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reply, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foward, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(attach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0))))
        );

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
    private javax.swing.JButton reply;
    private java.awt.TextArea ta_message;
    private javax.swing.JTextField tf_From;
    private javax.swing.JTextField tf_date;
    private javax.swing.JTextField tf_subject;
    // End of variables declaration//GEN-END:variables
}
