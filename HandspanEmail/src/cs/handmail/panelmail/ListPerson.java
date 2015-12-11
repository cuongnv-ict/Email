/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.panelmail;

import cs.handmail.dailog.AddAcount;
import cs.handmail.dailog.EditAcount;
import cs.handmail.dailog.NewEmail;
import cs.handmail.dailog.ReceiveMail;
import cs.handmail.file.ListAcountFile;
import cs.handmail.mail.SessionEmail;
import cs.handmail.processtable.TableListAcount;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import sun.reflect.generics.tree.Tree;

/**
 *
 * @author Nguyen Van Cuong
 */
public class ListPerson extends javax.swing.JPanel {
    
    private ListAcountFile listAcountFile;
    private SessionEmail sessionEmail;
    private Properties properties;
    private TableListAcount tableListAcount;
    private Vector<String> _addressMailClick;

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
        updateAcount();
        _addressMailClick = new Vector<String>();
        clickListPersonListener();
    }
    
    private void updateAcount() {
        properties = listAcountFile.readListAcount();
        if (properties != null) {
            Map<String, Integer> map = new TreeMap<>();
            for (Object key : properties.keySet()) {
                map.put(String.valueOf(key), 1);
            }
            tableListAcount.listAcount(tableAcount, map);
        }
    }
    
    void addToVector(String _address) {
        _addressMailClick.add(_address);
    }
    
    boolean checkAddrVector(String _address) {
        for (int i = 0; i < _addressMailClick.size(); i++) {
            if (_addressMailClick.get(i).equals(_address)) {
                return true;
            }
        }
        return false;
    }
    
    void removeAddrVector(String _address) {
        for (int i = 0; i < _addressMailClick.size(); i++) {
            if (_addressMailClick.get(i).equals(_address)) {
                _addressMailClick.removeElementAt(i);
                break;
            }
        }
    }
    
    void clickListPersonListener() {
        tableAcount.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); //To change body of generated methods, choose Tools | Templates.
                JTable table = (JTable) e.getSource();
                Point p = e.getPoint();
                
                int row = table.rowAtPoint(p);
                if (e.getClickCount() == 2) {
                    String addr = tableAcount.getValueAt(row, 2).toString();
                    if (!checkAddrVector(addr)) {
                        NewEmail email = new NewEmail(null, true, sessionEmail);
                        email.setAddress(addr);
                        addToVector(addr);
                        email.addWindowListener(new WindowAdapter() {
                            
                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                                removeAddrVector(addr);
                            }
                            
                        });
                        email.setAlwaysOnTop(true);
                        email.setVisible(true);
                        
                    } else {
                        
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);                        
                        JOptionPane.showMessageDialog(dialog, "Mail đã được mở");
                        
                    }
                }
            }
            
        });
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
        jPanel1 = new javax.swing.JPanel();
        load = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        tableAcount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "STT", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
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
            tableAcount.getColumnModel().getColumn(0).setMinWidth(32);
            tableAcount.getColumnModel().getColumn(0).setPreferredWidth(32);
            tableAcount.getColumnModel().getColumn(0).setMaxWidth(32);
            tableAcount.getColumnModel().getColumn(1).setMinWidth(50);
            tableAcount.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableAcount.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setPreferredSize(new java.awt.Dimension(419, 43));

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
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

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
                .addComponent(load))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(load, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        AddAcount addAcount = new AddAcount(null, true, tableAcount, sessionEmail);
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
            if (properties != null) {
                for (Object key : properties.keySet()) {
                    map.put(String.valueOf(key), 1);
                }
            }
            tableListAcount.listAcount(tableAcount, map);
            
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        TableModel tableModel = tableAcount.getModel();
        ArrayList<String> mails = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0) == true) {
                mails.add(String.valueOf(tableModel.getValueAt(i, 2)));
            }
        }
        if (mails.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn tài khoản để chỉnh sửa.", "Delete Acount", JOptionPane.INFORMATION_MESSAGE);
        } else if (mails.size() != 1) {
            JOptionPane.showMessageDialog(null, "Bạn chỉ được phép chỉnh sửa 1 mail.", "Delete Acount", JOptionPane.INFORMATION_MESSAGE);
        } else {
            EditAcount e = new EditAcount(null, true, mails.get(0),sessionEmail);
            e.setVisible(true);
        }
    }//GEN-LAST:event_jLabel3MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel load;
    private javax.swing.JTable tableAcount;
    // End of variables declaration//GEN-END:variables
}
