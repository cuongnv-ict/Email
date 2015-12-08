package cs.handmail.dailog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cs.handmail.Controller.TreeController;
import cs.handmail.panelmail.ListPerson;
import cs.handmail.mail.SessionEmail;
import cs.handmail.panelmail.InBox;
import cs.handmail.panelmail.StatisticMail;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Nguyen Van Cuong
 */
public class CenterMail extends javax.swing.JFrame {

    private StatisticMail statisticEmail;
    private SessionEmail sessionEmail;
    private TreeController treeCtr;
    private JPanel rightComponet;
    private Timer timer;
    public static final int CUSTOMER = 1;
    public static final int STAFF = 2;
    public static final int SENT = 3;
    public static final int DELETEINBOX = 4;
    public static final int DELETESENT = 5;
    public static final int NOMAIL = -1;
    public int flagsMail;

    /**
     * Creates new form CenterMail
     *
     * @param smail
     */
    public CenterMail(SessionEmail smail) {
        initComponents();
        sessionEmail = smail;
        // add Tree
        treeCtr = new TreeController();
        treeCtr.setTree(TreeMail, sessionEmail);
        treeCtr.setModelCustomTree();
        setTreeClick();
        // add RightComponet
        flagsMail = CUSTOMER;
        rightComponet = new InBox(CUSTOMER, sessionEmail);
        Email.setRightComponent(rightComponet);

    }

    private void setTreeClick() {
        TreeMail.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); //To change body of generated methods, choose Tools | Templates.
                if (e.getClickCount() == 2) {
                    try {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) TreeMail.getLastSelectedPathComponent();
                        if (node == null) {
                            return;
                        } else {
                            String nodeName = node.getUserObject().toString();
                            // Xu ly
                            if (nodeName.contains("Customer") && flagsMail != CUSTOMER) {
                                if (flagsMail != NOMAIL) {
                                    ((InBox) rightComponet).close();
                                }
                                flagsMail = CUSTOMER;
                                rightComponet = new InBox(CUSTOMER, sessionEmail);
                                Email.setRightComponent(rightComponet);
                            }
                            if (nodeName.contains("Staff") && flagsMail != STAFF) {
                                if (flagsMail != NOMAIL) {
                                    ((InBox) rightComponet).close();
                                }
                                flagsMail = STAFF;
                                rightComponet = new InBox(STAFF, sessionEmail);
                                Email.setRightComponent(rightComponet);
                            }
                            if (nodeName.contains("Send") && flagsMail != SENT) {
                                if (flagsMail != NOMAIL) {
                                    ((InBox) rightComponet).close();
                                }
                                flagsMail = SENT;
                                rightComponet = new InBox(SENT, sessionEmail);
                                Email.setRightComponent(rightComponet);
                            }
                            if (nodeName.contains("Delete Inbox") && flagsMail != DELETEINBOX) {
                                if (flagsMail != NOMAIL) {
                                    ((InBox) rightComponet).close();
                                }
                                flagsMail = DELETEINBOX;
                                rightComponet = new InBox(DELETEINBOX, sessionEmail);
                                Email.setRightComponent(rightComponet);
                            }
                            if (nodeName.contains("Delete Send") && flagsMail != DELETEINBOX) {
                                if (flagsMail != NOMAIL) {
                                    ((InBox) rightComponet).close();
                                }
                                flagsMail = DELETESENT;
                                rightComponet = new InBox(DELETESENT, sessionEmail);
                                Email.setRightComponent(rightComponet);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void updateAcount() {
        //  listPerson.updateAcount();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Email = new javax.swing.JSplitPane();
        ScrollPane = new javax.swing.JScrollPane();
        TreeMail = new javax.swing.JTree();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Email.setBorder(null);
        Email.setMinimumSize(new java.awt.Dimension(191, 322));

        ScrollPane.setMinimumSize(new java.awt.Dimension(156, 322));
        ScrollPane.setPreferredSize(new java.awt.Dimension(156, 322));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("HandspanEmail");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Inbox");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Sent Email");
        treeNode1.add(treeNode2);
        TreeMail.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        TreeMail.setMinimumSize(new java.awt.Dimension(95, 0));
        ScrollPane.setViewportView(TreeMail);

        Email.setLeftComponent(ScrollPane);

        jMenu1.setText("Hệ thống");

        jMenuItem5.setText("Mail mới");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem1.setText("Thống kê");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Danh sách");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Thoát");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar2.add(jMenu1);

        jMenu2.setText("Cài đặt");

        jMenuItem3.setText("Thiết lập mail quản lý");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar2.add(jMenu2);

        jMenu3.setText("Giúp đỡ");
        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (!sessionEmail.isAdmin()) {
            JOptionPane.showMessageDialog(null, "Tài khoản của bạn không được quyền truy nhập chức năng này.", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        flagsMail = NOMAIL;
        Email.setRightComponent(new StatisticMail(sessionEmail));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if (!sessionEmail.isAdmin()) {
            JOptionPane.showMessageDialog(null, "Tài khoản của bạn không được quyền truy nhập chức năng này.", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        flagsMail = NOMAIL;
        Email.setRightComponent(new ListPerson(sessionEmail));
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        NewEmail newEmail = new NewEmail(null, true, sessionEmail);
        newEmail.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Admin admin = new Admin(null,true);
        admin.setVisible(true);
        sessionEmail.checkAdmin();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane Email;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JTree TreeMail;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    // End of variables declaration//GEN-END:variables
}
