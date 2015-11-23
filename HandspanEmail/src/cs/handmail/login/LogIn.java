/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.login;

import cs.handmail.file.PropertiesFile;
import cs.handmail.file.DataUserFile;
import cs.handmail.mail.SessionEmail;
import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.util.Properties;
import javax.swing.JFrame;
import java.util.Vector;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Van Cuong
 */
public class LogIn extends javax.swing.JDialog {

    private PropertiesFile propertiesFile;
    private DataUserFile dataUserFile;
    private Vector<String> user;
    private SessionEmail sessionEmail;

    /**
     * Creates new form LogIn
     */
    public LogIn(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        propertiesFile = new PropertiesFile();
        dataUserFile = new DataUserFile();
        user = dataUserFile.readDataUser();
        sessionEmail = new SessionEmail();
        mail.setText(user.get(0));
        if ("1".equals(user.get(2))) {
            pass.setText(user.get(1));
        } else {
            pass.setText("");
        }
        remeber.setSelected("1".equals(user.get(2)));
         this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 250, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 180);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pass = new java.awt.TextField();
        mail = new java.awt.TextField();
        log = new java.awt.Button();
        cancel = new java.awt.Button();
        remeber = new javax.swing.JCheckBox();
        Email = new javax.swing.JLabel();
        Email1 = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(500, 356));
        setMinimumSize(new java.awt.Dimension(500, 356));
        setPreferredSize(new java.awt.Dimension(500, 356));
        setResizable(false);
        getContentPane().setLayout(null);

        pass.setBackground(java.awt.SystemColor.controlLtHighlight);
        pass.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass.setEchoChar('*');
        pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passKeyPressed(evt);
            }
        });
        getContentPane().add(pass);
        pass.setBounds(230, 160, 230, 25);

        mail.setBackground(java.awt.SystemColor.controlLtHighlight);
        mail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        mail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mailKeyPressed(evt);
            }
        });
        getContentPane().add(mail);
        mail.setBounds(230, 120, 230, 25);

        log.setLabel("Log In");
        log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logActionPerformed(evt);
            }
        });
        log.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                logKeyPressed(evt);
            }
        });
        getContentPane().add(log);
        log.setBounds(380, 260, 80, 24);

        cancel.setLabel("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        cancel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cancelKeyPressed(evt);
            }
        });
        getContentPane().add(cancel);
        cancel.setBounds(280, 260, 80, 24);

        remeber.setForeground(java.awt.Color.cyan);
        remeber.setText("Lưu mật khẩu");
        getContentPane().add(remeber);
        remeber.setBounds(230, 200, 150, 23);

        Email.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Email.setForeground(java.awt.Color.white);
        Email.setText("Password :");
        Email.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(Email);
        Email.setBounds(140, 160, 80, 25);

        Email1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Email1.setForeground(java.awt.Color.white);
        Email1.setText("Email :");
        Email1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(Email1);
        Email1.setBounds(140, 120, 70, 25);

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/50832074_1.jpg"))); // NOI18N
        bg.setEnabled(false);
        getContentPane().add(bg);
        bg.setBounds(0, 0, 500, 330);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_cancelActionPerformed

    private void cancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cancelKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.exit(0);
        }
    }//GEN-LAST:event_cancelKeyPressed

    private void mailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mailKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LogInEmail();
        }
    }//GEN-LAST:event_mailKeyPressed

    private void passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LogInEmail();
        }
    }//GEN-LAST:event_passKeyPressed

    private void logKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_logKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LogInEmail();
        }
    }//GEN-LAST:event_logKeyPressed

    private void logActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logActionPerformed
        // TODO add your handling code here:
        LogInEmail();
    }//GEN-LAST:event_logActionPerformed

    public void LogInEmail() {

        if ("".equals(mail.getText())) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập địa chỉ mail", "LogIn", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("".equals(pass.getText())) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập password", "LogIn", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (getMail(mail.getText(), pass.getText())) {
            this.dispose();
            dataUserFile.writeDataUser(mail.getText(), pass.getText(), remeber.isSelected() ? 1 : 0);
            CenterMail centerMail = new CenterMail(sessionEmail);
            centerMail.setExtendedState(centerMail.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            centerMail.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Email, Password chưa chính xác", "LogIn", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    public boolean getMail(String mail, String pass) {

        Vector<String> info = propertiesFile.readFile();
        String host = info.get(0);
        String port = info.get(1);
        return sessionEmail.connectIMAPS(mail, pass, host, port);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Email;
    private javax.swing.JLabel Email1;
    private javax.swing.JLabel bg;
    private java.awt.Button cancel;
    private java.awt.Button log;
    private java.awt.TextField mail;
    private java.awt.TextField pass;
    private javax.swing.JCheckBox remeber;
    // End of variables declaration//GEN-END:variables
}
