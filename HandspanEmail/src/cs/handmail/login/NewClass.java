/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.handmail.login;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Van Cuong
 */
public class NewClass {

    public static void main(String[] args) {
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");

        System.out.println("DAY " + simpleDateFormat.format(date).toUpperCase());

        simpleDateFormat = new SimpleDateFormat("MMMM");
        System.out.println("MONTH " + simpleDateFormat.format(date).toUpperCase());

        simpleDateFormat = new SimpleDateFormat("YYYY");
        System.out.println("YEAR " + simpleDateFormat.format(date).toUpperCase());

    }
}
