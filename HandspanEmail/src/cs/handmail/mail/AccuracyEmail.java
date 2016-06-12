/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.mail;

/**
 *
 * @author Nguyen Van Cuong
 */
public class AccuracyEmail {

    public String extraEmail(String mail) {
        String[] arrayMail = mail.split(" ");
        String m = arrayMail[arrayMail.length - 1];
        if (m.endsWith(">")) {
            return m.substring(1,m.length()-1).toLowerCase();
        } else {
            return m;
        }
    }

    public boolean isEmailHandspan(String mail) {
        return mail.endsWith("@handspan.com");
    }
}
