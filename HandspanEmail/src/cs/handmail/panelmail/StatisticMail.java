/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.panelmail;

import cs.handmail.file.ListAcountFile;
import cs.handmail.mail.SessionEmail;
import cs.handmail.processtable.TableListAcount;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.mail.Message;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Van Cuong
 */
public class StatisticMail extends javax.swing.JPanel {

    private ListAcountFile listAcountFile;
    private SessionEmail sessionEmail;
    private Properties properties;
    private TableListAcount tableListAcount;
    private Thread th;

    /**
     * Creates new form ListPerson
     *
     * @param smail
     */
    public StatisticMail(SessionEmail smail) {
        initComponents();
        listAcountFile = new ListAcountFile();
        sessionEmail = smail;
        tableListAcount = new TableListAcount();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        Date date = new Date();
        month.setSelectedIndex(date.getMonth());
        year.setSelectedIndex(Integer.parseInt(simpleDateFormat.format(date)) - 2010);
        th = new Thread() {
            @Override
            public void run() {
                statisticEmailAcount();
                load.setVisible(false);
            }
        };
        th.start();
        th.stop();
    }

    private void statisticEmailAcount() {
        properties = listAcountFile.readListAcount();
        if (properties != null) {
            Map<String, Integer> map = new TreeMap<>();
            for(Object key: properties.keySet()){
                String m = properties.getProperty(String.valueOf(key));
                map.put(m,1);
            }
            int mon = month.getSelectedIndex() + 1;
            int ye = year.getSelectedIndex() + 2010;
            System.err.println(mon + "*" + ye);
            Map<String, Message[]> request = sessionEmail.statisticAddressEmail(mon, ye, map, false);
            Map<String, Message[]> answer = sessionEmail.statisticAddressEmail(mon, ye, map, true);
            tableListAcount.statisticEmail(tableAcount, map, request, answer);
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
        jLabel1 = new javax.swing.JLabel();
        month = new javax.swing.JComboBox<>();
        year = new javax.swing.JComboBox<>();
        load = new javax.swing.JLabel();
        button1 = new java.awt.Button();

        tableAcount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Email", "Num. Email Requested", "Num. Email Answered", "AVG. Time (h:m)"
            }
        ));
        jScrollPane1.setViewportView(tableAcount);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setPreferredSize(new java.awt.Dimension(419, 43));

        textField1.setPreferredSize(new java.awt.Dimension(230, 32));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Time:");

        month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" }));

        load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loader-newui.gif"))); // NOI18N

        button1.setLabel("Thống kê");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(load)
                .addGap(1, 1, 1)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(load, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        runSelected();
    }//GEN-LAST:event_button1ActionPerformed

    public void runSelected() {
        System.err.println("sdfdsfsdf");
        load.setVisible(true);
        th = new Thread() {
            @Override
            public void run() {
                statisticEmailAcount();
                load.setVisible(false);
            }
        };
        th.start();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel load;
    private javax.swing.JComboBox<String> month;
    private javax.swing.JTable tableAcount;
    private java.awt.TextField textField1;
    private javax.swing.JComboBox<String> year;
    // End of variables declaration//GEN-END:variables
}
