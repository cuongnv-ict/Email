/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.panelmail;

import cs.handmail.file.ListAcountFile;
import cs.handmail.mail.SessionEmail;
import cs.handmail.processtable.TableListAcount;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import sun.reflect.generics.tree.Tree;
import cs.handmail.login.AddAcount;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 *
 * @author Nguyen Van Cuong
 */
public class ListPerson extends javax.swing.JPanel {

    private ListAcountFile listAcountFile;
    private SessionEmail sessionEmail;
    private Properties properties;
    private TableListAcount tableListAcount;

    /**
     * Creates new form ListPerson
     *
     * @param smail
     */
    public ListPerson(SessionEmail smail) {
        initComponents();
        listAcountFile = new ListAcountFile();
        sessionEmail = smail;
        tableListAcount = new TableListAcount();
        load.setVisible(false);
        Thread d = new Thread() {
            @Override
            public void run() {
                updateAcount();
            }
        };
        d.start();
    }

    public void updateAcount() {
        properties = listAcountFile.readListAcount();
        if (properties == null) {
            int state = JOptionPane.showConfirmDialog(null, "Danh sách tài khoản chưa tồn tại hoặc chưa cập nhật.\n Bạn có muốn cập nhật.", "Update Acount", JOptionPane.YES_NO_OPTION);
            if (state == JOptionPane.YES_OPTION) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        load.setVisible(true);
                        Map<String, Integer> map = sessionEmail.addressEmail();
                        sessionEmail.closeSend();
                        sessionEmail.closeInbox();
                        tableListAcount.listAcount(tableAcount, map);
                        for (String key : map.keySet()) {
                            listAcountFile.addAcount(key);
                        }
                        load.setVisible(false);
                    }
                };
                th.start();
            }
        } else {
            Map<String, Integer> map = new TreeMap<>();
            for (Object key : properties.keySet()) {
                map.put(properties.getProperty(String.valueOf(key)), 1);
            }
            tableListAcount.listAcount(tableAcount, map);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tableAcount = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        textField1 = new java.awt.TextField();
        load = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        tableAcount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "", "STT", "Email"
            }
        ));
        jScrollPane1.setViewportView(tableAcount);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setPreferredSize(new java.awt.Dimension(419, 43));

        textField1.setPreferredSize(new java.awt.Dimension(230, 32));

        load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loader-newui.gif"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_male_add.png"))); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(32, 32));
        jLabel2.setMinimumSize(new java.awt.Dimension(32, 32));
        jLabel2.setPreferredSize(new java.awt.Dimension(32, 32));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_male_edit.png"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(32, 32));
        jLabel3.setMinimumSize(new java.awt.Dimension(32, 32));
        jLabel3.setPreferredSize(new java.awt.Dimension(32, 32));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_male_2_delete.png"))); // NOI18N
        jLabel4.setMaximumSize(new java.awt.Dimension(32, 32));
        jLabel4.setMinimumSize(new java.awt.Dimension(32, 32));
        jLabel4.setPreferredSize(new java.awt.Dimension(32, 32));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(load)
                .addGap(2, 2, 2)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(load, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        AddAcount addAcount = new AddAcount(null, true, tableAcount);
        addAcount.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        TableModel tableModel = tableAcount.getModel();
        ArrayList<String> mails = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0) == true) {
                mails.add(String.valueOf(tableModel.getValueAt(i, 2)));
            }
        }
        if (mails.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn tài khoản để xóa", "Delete Acount", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            listAcountFile.deleteAcount(mails);
            properties = listAcountFile.readListAcount();
            tableListAcount = new TableListAcount();
            Map<String, Integer> map = new TreeMap<>();
            for (Object key : properties.keySet()) {
                map.put(properties.getProperty(String.valueOf(key)), 1);
            }
            tableListAcount.listAcount(tableAcount, map);
        }
    }//GEN-LAST:event_jLabel4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel load;
    private javax.swing.JTable tableAcount;
    private java.awt.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
