/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.Controller;

import cs.handmail.file.DataUserFile;
import cs.handmail.file.PropertiesFile;
import cs.handmail.mail.SessionEmail;
import java.awt.Component;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import static javafx.scene.input.DataFormat.IMAGE;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Venus-NS
 */
public class TreeController {

    private TreeModel modelTree;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode inbox;
    private DefaultMutableTreeNode send;
    private DefaultMutableTreeNode delete;
    private DefaultMutableTreeNode deleteInbox;
    private DefaultMutableTreeNode deleteSent;
    private DefaultMutableTreeNode customer;
    private DefaultMutableTreeNode staff;
    private Properties mPropertise;
    private SessionEmail session;
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setTree(JTree tree, SessionEmail session) {
        this.tree = tree;
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            Icon iconOpen = new ImageIcon(getClass().getClassLoader().getResource("image/mailOpen.png"));
            Icon iconClose = new ImageIcon(getClass().getClassLoader().getResource("image/mailClose.png"));
            Icon iconMailBox = new ImageIcon(getClass().getClassLoader().getResource("image/mailbox.png"));
            Icon user = new ImageIcon(getClass().getClassLoader().getResource("image/user.png"));
            Icon staff = new ImageIcon(getClass().getClassLoader().getResource("image/staff.png"));

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (node.equals(root)) {
                    setIcon(iconMailBox);
                } else if (node.equals(inbox) || node.equals(send) || node.equals(delete)|| node.equals(deleteInbox)|| node.equals(deleteSent)) {
                    setIcon(iconClose);
                } else if (node.equals(customer)) {
                    setIcon(user);
                } else {
                    setIcon(staff);
                }

                return this;

            }

        });
        // get data 
        this.session = session;
        store = session.getStore();
        //setAdapterMail();
    }

    public void setModelCustomTree() {
        try {

            root = new DefaultMutableTreeNode("Handspan");
            inbox = new DefaultMutableTreeNode("Inbox");
            send = new DefaultMutableTreeNode("Send");
            delete = new DefaultMutableTreeNode("Delete");
            customer = new DefaultMutableTreeNode("Customer");
            staff = new DefaultMutableTreeNode("Staff");
            deleteInbox = new DefaultMutableTreeNode("Delete Inbox");
            deleteSent = new DefaultMutableTreeNode("Delete Send");

            root.add(inbox);
            root.add(send);
            root.add(delete);
            inbox.add(customer);
            inbox.add(staff);
            delete.add(deleteInbox);
            delete.add(deleteSent);
            //  DefaultMutableTreeNode 
            modelTree = new DefaultTreeModel(root);
            tree.setModel(modelTree);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }  
}
