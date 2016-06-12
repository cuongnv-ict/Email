/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.panelmail;

import cs.handmail.dailog.DLElement;
import cs.handmail.dailog.EditAcount;
import cs.handmail.file.DataUserFile;
import cs.handmail.file.DateLabelFormatter;
import cs.handmail.file.ListAcountFile;
import cs.handmail.mail.SessionEmail;
import cs.handmail.processtable.ExportExcel;
import cs.handmail.processtable.TableListAcount;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
    private boolean flags;
    private DataUserFile dataUserFile;
    private JDatePickerImpl datePicker1;
    private JDatePickerImpl datePicker2;
    private Date selectedDate1;
    private Date selectedDate2;

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
        dataUserFile = new DataUserFile();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        Date date = new Date();
        selectedDate1 = new Date();
        selectedDate2 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String[] str = sdf.format(date).split("-");
            int y, m, d;
            y = Integer.parseInt(str[2]);
            m = Integer.parseInt(str[1]);
            d = Integer.parseInt(str[0]);
            selectedDate1 = sdf.parse("1-" + m + "-" + y);
        } catch (ParseException ex) {
            Logger.getLogger(StatisticMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        createDatePicker();       
        th = new Thread() {
            @Override
            public void run() {
                statisticEmailAcount();
                load.setVisible(false);
                flags = true;
            }
        };
        flags = false;
        flags = true;
        th.start();
    }

    public void createDatePicker() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String[] str = simpleDateFormat.format(date).split("-");
        int y, m, d;
        y = Integer.parseInt(str[0]);
        m = Integer.parseInt(str[1]);
        d = Integer.parseInt(str[2]);
        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());
        UtilDateModel model1 = new UtilDateModel();
        model1.setDate(y, m - 1, 1);
        model1.setSelected(true);
        UtilDateModel model2 = new UtilDateModel();
        model2.setDate(y, m - 1, d);
        model2.setSelected(true);
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
        datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        jPanel1.add(datePicker1);
        jPanel2.add(datePicker2);
    }

    private void statisticEmailAcount() {
        properties = listAcountFile.readListAcount();
        if (properties != null) {
            int id = 1;
            tableListAcount.clearTable(tableAcount);
            ArrayList<Thread> arrThread = new ArrayList<>();
            Map<String, Map<String, Message[]>> map = new TreeMap();
            properties.keySet().stream().map((key) -> new Thread() {
                @Override
                public void run() {
                    Map<String, Message[]> msgs = sessionEmail.statisticAddressEmail(selectedDate1, selectedDate2, String.valueOf(key), dataUserFile.decryptPass(String.valueOf(properties.get(key))));
                    // tableListAcount.statisticEmail(tableAcount, msgs, String.valueOf(key), 0);
                    map.put(String.valueOf(key), msgs);
                }
            }).forEach((t) -> {
                t.start();
                arrThread.add(t);
            });
            while (!arrThread.isEmpty()) {
                ArrayList<Thread> arr = new ArrayList<>();
                for (Thread x : arrThread) {
                    if (!x.isAlive()) {
                        arr.add(x);
                    }
                }
                arrThread.removeAll(arr);
            }
            int count = 1;
            for (String key : map.keySet()) {
                tableListAcount.statisticEmail(tableAcount, map.get(key), String.valueOf(key), count);
                count++;
            }
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
        tableAcount = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){

                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(row % 2 == 1 ? Color.LIGHT_GRAY : Color.WHITE);
                c.setForeground(Color.BLACK);
                return c;
            }
        };
        Banner = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        load = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        tableAcount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "", "STT", "Email", "Số email được yêu cầu trả lời", "Số email nhân viên đã trả lời", "Số email chưa trả lời trong 24h", "Thời gian trả lời trung bình mỗi email (hh::mm)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableAcount);
        if (tableAcount.getColumnModel().getColumnCount() > 0) {
            tableAcount.getColumnModel().getColumn(0).setResizable(false);
            tableAcount.getColumnModel().getColumn(0).setPreferredWidth(15);
            tableAcount.getColumnModel().getColumn(1).setMinWidth(50);
            tableAcount.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableAcount.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        Banner.setBackground(java.awt.SystemColor.activeCaption);
        Banner.setPreferredSize(new java.awt.Dimension(419, 43));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Bắt đầu :");

        load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loader-newui.gif"))); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/table-export.png"))); // NOI18N
        jLabel6.setMaximumSize(new java.awt.Dimension(32, 32));
        jLabel6.setMinimumSize(new java.awt.Dimension(32, 32));
        jLabel6.setPreferredSize(new java.awt.Dimension(32, 32));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/061065-blue-jelly-icon-people-things-eye.png"))); // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(32, 32));
        jLabel5.setMinimumSize(new java.awt.Dimension(32, 32));
        jLabel5.setPreferredSize(new java.awt.Dimension(32, 32));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 152, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Kết thúc :");

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 154, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setText("Thống kê");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BannerLayout = new javax.swing.GroupLayout(Banner);
        Banner.setLayout(BannerLayout);
        BannerLayout.setHorizontalGroup(
            BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BannerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                .addComponent(load))
        );
        BannerLayout.setVerticalGroup(
            BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BannerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BannerLayout.createSequentialGroup()
                        .addGroup(BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BannerLayout.createSequentialGroup()
                                .addGroup(BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(load, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(Banner, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Banner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        ExportExcel ex = new ExportExcel();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        ex.setFolder(String.valueOf(simpleDateFormat.format(selectedDate1)+ "_" + simpleDateFormat.format(selectedDate2) + "_handspan") + ".xls");
        ex.setJtableData(tableAcount);
        if (ex.getPathFolder() != null) {
            ex.export();
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        TableModel tableModel = tableAcount.getModel();
        ArrayList<String> mails = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0) == true) {
                mails.add(String.valueOf(tableModel.getValueAt(i, 2)));
            }
        }
        if (mails.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn nhân viên muốn xem.", "", JOptionPane.INFORMATION_MESSAGE);
        } else if (mails.size() != 1) {
            JOptionPane.showMessageDialog(null, "Bạn chỉ được phép xem một người.", "", JOptionPane.INFORMATION_MESSAGE);
        } else {           
            DLElement e = new DLElement(null, true, tableListAcount.getInfo(), mails.get(0));
            e.setVisible(true);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        selectedDate1 = (Date) datePicker1.getModel().getValue();
        selectedDate2 = (Date) datePicker2.getModel().getValue();
        if (checkTime(selectedDate1, selectedDate2)) {
            //  JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
            if (flags) {
                runSelected();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Thời gian bắt đầu thấp hơn thời gian kết thúc.", "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public boolean checkTime(Date da1, Date da2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String[] str1 = simpleDateFormat.format(da1).split("-");
        String[] str2 = simpleDateFormat.format(da2).split("-");
        int y1, m1, d1, y2, m2, d2;
        y1 = Integer.parseInt(str1[0]);
        m1 = Integer.parseInt(str1[1]);
        d1 = Integer.parseInt(str1[2]);
        y2 = Integer.parseInt(str2[0]);
        m2 = Integer.parseInt(str2[1]);
        d2 = Integer.parseInt(str2[2]);
        if (y2 < y1) {
            return false;
        } else if (y2 == y1) {
            if (m2 < m1) {
                return false;
            } else if (m2 == m1) {
                if (d2 <= d1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void runSelected() {
        load.setVisible(true);
        if (th.isAlive()) {
            th.stop();
        }
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
    private javax.swing.JPanel Banner;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel load;
    private javax.swing.JTable tableAcount;
    // End of variables declaration//GEN-END:variables
}
