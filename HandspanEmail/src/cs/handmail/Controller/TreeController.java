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
    private JTree   tree;
    private int inboxSeen=2;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode newMess;
    private DefaultMutableTreeNode inBox;
    private DefaultMutableTreeNode sendBox;
    private Properties mPropertise;
    private SessionEmail session;
    private Store store;
    
    
    public Store getStore()
    {
        return store;
    }
    public void setTree(JTree tree, SessionEmail session)
    {
        this.tree = tree;
        tree.setCellRenderer(new DefaultTreeCellRenderer(){
            private  Icon iconOpen = new ImageIcon(getClass().getClassLoader().getResource("image/mailOpen.png"));
            private  Icon iconClose = new ImageIcon(getClass().getClassLoader().getResource("image/mailClose.png"));
            private  Icon iconMailBox = new ImageIcon(getClass().getClassLoader().getResource("image/mailbox.png"));
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
               Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if(node.equals(root)){
                    setIcon(iconMailBox);
                }
                else if(node.equals(newMess)||node.equals(inBox)||node.equals(sendBox) ) {
                    setIcon(iconClose);
                }
                
                return this;
               
             
            }
            
        });
        // get data 
        this.session = session;
        store = session.getStore();
        //setAdapterMail();
    }
    
    public void setModelCustomTree(int number)
    {
        try{
            
            
            root = new DefaultMutableTreeNode("HandsMail");
            newMess = new DefaultMutableTreeNode("NEW");
            inBox = new DefaultMutableTreeNode("INBOX" + " (" + Integer.toString(number)+ ")");
            sendBox = new DefaultMutableTreeNode("SENDBOX");
            root.add(newMess);
            root.add(inBox);
            root.add(sendBox);
          //  DefaultMutableTreeNode 
            modelTree = new DefaultTreeModel(root);
            tree.setModel(modelTree);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        } 
    }
    
    public void setAdapterMail()
    {
        try{
            Vector<String> user = new DataUserFile().readDataUser();
            Vector<String> host = new PropertiesFile().readFile();
            SessionEmail session = new SessionEmail();
            session.connectIMAPS(user.get(0), user.get(1), host.get(0), host.get(1));
           // store = session.getStore();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public int getInboxUserMail(){
        try{   
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[] = folderInbox.search(ft);
            return folderInbox.getUnreadMessageCount();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
   
    
    
    
   
    
//    public static class UserInformation{
//        public static String userName = "test1@handspan.com";
//        public static String passWord = "h";
//        public static String mailHost = "imap.gmail.com";
//        public static String port     = "993";
//        public static String protocol =  "imap";
//        
//    }
    
}
