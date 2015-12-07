/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.panelmail;

import cs.handmail.dailog.CenterMail;
import cs.handmail.dailog.ReceiveMail;
import cs.handmail.mail.SessionEmail;
import cs.handmail.processtable.TableListEmail;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Nguyen Van Cuong
 */
public class InBox extends javax.swing.JPanel {

    private int flags;
    private SessionEmail sessionEmail;
    private TableListEmail tableListEmail;
    private boolean timeout;
    private Message message[];
    Vector messageDisplay;
    private InBox main;
    /**
     * Creates new form InBox
     *
     * @param flags
     * @param session
     */
    public InBox(int flags, SessionEmail session) {
        initComponents();
        this.flags = flags;
        messageDisplay = new Vector<Integer>();
        main = this;
        sessionEmail = session;
        tableListEmail = new TableListEmail();
        timeout = true;
        setTableDoubleClick();
        switch (this.flags) {
            case CenterMail.CUSTOMER:
                title.setText("Customer");
                break;
            case CenterMail.STAFF:
                title.setText("Staff");
                break;
            case CenterMail.SENT:
                title.setText("Send");
                break;
            case CenterMail.DELETEINBOX:
                title.setText("Delete Inbox");
                break;
            case CenterMail.DELETESENT:
                title.setText("Delete Send");
                break;
        }

        Thread th = new Thread() {
            @Override
            public void run() {
                while (timeout) {
                    try {
                        updateEmail();
                        load.setVisible(false);
                        Thread.sleep(60000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InBox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };
        th.start();
    }

    public void openMessage(int numberOfmessageInbox)
    {
        messageDisplay.add(numberOfmessageInbox);
    }
    
    public void closeMessage(int numberOfmessageInbox)
    {
        int i =0;
        while(i<messageDisplay.size())
        {
            if((int)messageDisplay.get(i) == numberOfmessageInbox) break;
            i++;
        }
        messageDisplay.removeElementAt(i);
    }
    
    boolean checkMessage(int numberOfmessageInbox)
    {
        for(int i = 0; i<messageDisplay.size();i++)
        {
            if((int)messageDisplay.get(i)== numberOfmessageInbox) return true;
        }
        return false;
    }
    
    public void updateEmail() {
        message = null;
        switch (flags) {
            case CenterMail.CUSTOMER:
                message = sessionEmail.getMessageCustomer();
                break;
            case CenterMail.STAFF:
                message = sessionEmail.getMessageStaff();
                break;
            case CenterMail.SENT:
                message = sessionEmail.getMessageSent();
                break;
            case CenterMail.DELETEINBOX:
                message = sessionEmail.getMessageDeleteInbox();
                break;
            case CenterMail.DELETESENT:
                message = sessionEmail.getMessageDeleteSent();
                break;
        }
        tableListEmail.displayEmail(table, message);
    }

    public void close() {
        timeout = false;
    }

    /****
     * click in row double click
     */
    
    void setTableDoubleClick()
    {
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point p = e.getPoint();
                
                int row = table.rowAtPoint(p);
                if (e.getClickCount() == 2) {
                    int temp =message[message.length-1-row].getMessageNumber();
                    if(!checkMessage(temp))
                    {
                        ReceiveMail receveiMail = new ReceiveMail(null, false,message[message.length-1-row],sessionEmail);
                        receveiMail.setParent(main);
                        receveiMail.numberMess = temp;
                        openMessage(temp);
                        receveiMail.addWindowListener(new WindowAdapter() {

                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                                closeMessage(temp);
                            }

                        });
                        receveiMail.show();
                    }else{
                        JOptionPane.showMessageDialog(null, "message opened");
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

        jPanel1 = new javax.swing.JPanel();
        load = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){

                Component c = super.prepareRenderer(renderer, row, column);
                c.setBackground(row % 2 == 1 ? Color.LIGHT_GRAY : Color.WHITE);
                c.setForeground(Color.BLACK);
                return c;

            }
        };

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setForeground(java.awt.SystemColor.activeCaption);
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(419, 43));

        load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loader-newui.gif"))); // NOI18N

        title.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        title.setText("Customer");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(load))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(load, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "STT", "Date", "From", "Subject"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(32);
            table.getColumnModel().getColumn(0).setPreferredWidth(32);
            table.getColumnModel().getColumn(0).setMaxWidth(32);
            table.getColumnModel().getColumn(1).setMinWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setMaxWidth(50);
            table.getColumnModel().getColumn(2).setMinWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setMaxWidth(200);
            table.getColumnModel().getColumn(3).setMinWidth(250);
            table.getColumnModel().getColumn(3).setPreferredWidth(250);
            table.getColumnModel().getColumn(3).setMaxWidth(250);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel load;
    private javax.swing.JTable table;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
