/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.login;

import cs.handmail.file.ListAcountFile;
import cs.handmail.mail.AccuracyEmail;
import cs.handmail.processtable.TableListAcount;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Nguyen Van Cuong
 */
public class AddAcount extends javax.swing.JDialog {

    private JTable table;

    /**
     * Creates new form AddAcount
     */
    public AddAcount(java.awt.Frame parent, boolean modal, JTable table) {
        super(parent, modal);
        initComponents();
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150);
        this.table = table;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        text = new java.awt.TextField();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Acount");
        setResizable(false);

        text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textKeyPressed(evt);
            }
        });

        button1.setLabel("Thêm");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        button1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                button1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        button1.getAccessibleContext().setAccessibleName("Th");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        access();
    }//GEN-LAST:event_button1ActionPerformed

    private void textKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            access();
        }
    }//GEN-LAST:event_textKeyPressed

    private void button1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_button1KeyPressed
        // TODO add your handling code here:
        access();
    }//GEN-LAST:event_button1KeyPressed

    public void access() {
        String m = text.getText();
        m = m.trim();
        if (m.equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa điền địa chỉ email.", "Add Acount", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        AccuracyEmail accuracyEmail = new AccuracyEmail();
        if (!accuracyEmail.isEmailHandspan(m)) {
            JOptionPane.showMessageDialog(null, "Hãy nhập mail có định dạng *@handspan.com.", "Add Acount", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ListAcountFile listAcountFile = new ListAcountFile();
        if (listAcountFile.isExistsAcount(m)) {
            JOptionPane.showMessageDialog(null, "Email đã tồn tại.", "Add Acount", JOptionPane.ERROR_MESSAGE);
            return;
        }
        listAcountFile.addAcount(m);
        Properties properties = listAcountFile.readListAcount();
        TableListAcount tableListAcount = new TableListAcount();
        Map<String, Integer> map = new TreeMap<>();
        for (Object key : properties.keySet()) {
            map.put(properties.getProperty(String.valueOf(key)), 1);
        }
        tableListAcount.listAcount(table, map);
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.TextField text;
    // End of variables declaration//GEN-END:variables
}