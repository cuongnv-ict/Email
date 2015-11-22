package cs.handmail.login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cs.handmail.panelmail.ListPerson;
import cs.handmail.mail.SessionEmail;
import cs.handmail.panelmail.StatisticMail;

/**
 *
 * @author Nguyen Van Cuong
 */
public class CenterMail extends javax.swing.JFrame {

    private ListPerson listPerson;
    private StatisticMail statisticEmail;
    private SessionEmail sessionEmail;

    /**
     * Creates new form CenterMail
     */
    public CenterMail(SessionEmail smail) {
        initComponents();
        sessionEmail = smail;
        listPerson = new ListPerson(sessionEmail);
        Email.setRightComponent(listPerson);

    }

    public void updateAcount() {
        listPerson.updateAcount();
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
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
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

        jMenu1.setText("Systems");

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

        jMenuItem5.setText("Đăng xuất");
        jMenu1.add(jMenuItem5);

        jMenuItem4.setText("Thoát");
        jMenu1.add(jMenuItem4);

        jMenuBar2.add(jMenu1);

        jMenu2.setText("Setting");

        jMenuItem3.setText("Port");
        jMenu2.add(jMenuItem3);

        jMenuBar2.add(jMenu2);

        jMenu3.setText("Help");
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
        Email.setRightComponent(new StatisticMail(sessionEmail));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Email.setRightComponent(listPerson);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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