/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.handmail.dailog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Venus-NS
 */
public class MimeTypeJavaMail {
        public static String doc = "application/msword";
        public static String docs = "application/msword";
        public static String pdf = "application/pdf";
        public static String rss = "application/rss+xml";
        public static String kml = "application/vnd.google-earth.kml+xml";
        public static String kmz = "application/vnd.google-earth.kmz";
        public static String xls = "application/vnd.ms-excel";
        public static String xlsx = "application/vnd.ms-excel";
        public static String pptx = "application/vnd.ms-powerpoint";
        public static String pps = "application/vnd.ms-powerpoint";
        public static String ppt = "application/vnd.ms-powerpoint";
        public static String odp = "application/vnd.oasis.opendocument.presentation";
        public static String ods = "application/vnd.oasis.opendocument.spreadsheet";
        public static String odt = "application/vnd.oasis.opendocument.text";
        public static String sxc = "application/vnd.sun.xml.calc";
        public static String sxw = "application/vnd.sun.xml.writer";
        public static String gzip = "application/x-gzip";
        public static String zip = "application/zip";
        public static String au= "audio/basic";
        public static String snd = "audio/basic";
        public static String flac = "audio/flac";
        public static String mid  = "audio/mid";
        public static String rmi = "audio/mid";
        public static String m4a = "audio/mp4";
        public static String mp3 = "audio/mpeg";
        public static String oga = "audio/ogg";
        public static String ogg = "audio/ogg";
        public static String aif  = "audio/x-aiff";
        public static String aifc = "audio/x-aiff";
        public static String aiff = "audio/x-aiff";
        public static String wav= "audio/x-wav";
        public static String gif = "image/gif";
        public static String jpeg = "image/jpeg";
        public static String jpg = "image/jpeg";
        public static String jpe = "image/jpeg";
        public static String png = "image/png";
        public static String tiff = "image/tiff";
        public static String tif = "image/tiff";
        public static String wbmp = "image/vnd.wap.wbmp";
        public static String bmp = "image/x-ms-bmp";
        public static String ics = "text/calendar";
        public static String csv = "text/comma-separated-values";
        public static String css = "text/css";
        public static String htm  = "text/html";
        public static String html = "text/html";
        public static String text = "text/plain";
        public static String txt = "text/plain";
        public static String asc = "text/plain";
        public static String diff = "text/plain";
        public static String pot = "text/plain";
        public static String vcf = "text/x-vcard";
        public static String mp4 = "video/mp4";
        public static String mpeg  = "video/mpeg";
        public static String mpg  = "video/mpeg";
        public static String qt = "video/quicktime";
        public static String mov = "video/quicktime";
        public static String avi = "video/x-msvideo";
        Map<String,String> map ;

    public MimeTypeJavaMail() {
        map = new HashMap<String,String>();
        map.put("aif", aif);
        map.put("aifc",aifc);
        map.put("aiff", aiff);
        map.put("asc", asc);
        map.put("au", au);
        map.put("avi", avi);
        map.put("bmp", bmp);
        map.put("css", css);
        map.put("csv", csv);
        map.put("diff", diff);
        map.put("doc", doc);
        map.put("docs", docs);
        map.put("flac", flac);
        map.put("gif", gif);
        map.put("gzip", gzip);
        map.put("htm", htm);
        map.put("html", html);
        map.put("ics", ics);
        map.put("jpe", jpe);
        map.put("jpeg", jpeg);
        map.put("jpg", jpg);
        map.put("kml", kml);
        map.put("kmz", kmz);
        map.put("m4a", m4a);
        map.put("mid", mid);
        map.put("mov", mov);
        map.put("mp3", mp3);
        map.put("mp4", mp4);
        map.put("mpeg", mpeg);
        map.put("mpg", mpg);
        map.put("odp", odp);
        map.put("ods", ods);
        map.put("odt", odt);
        map.put("oga", oga);
        map.put("ogg", ogg);
        map.put("pdf", pdf);
        map.put("png", png);
        map.put("pot", pot);
        map.put("pps", pps);
        map.put("ppt", ppt);
        map.put("pptx", pptx);
        map.put("qt", qt);
        map.put("rmi", rmi);
        map.put("rss", rss);
        map.put("snd", snd);
        map.put("sxc", sxc);
        map.put("sxw", sxw);
        map.put("text", text);
        map.put("tif", tif);
        map.put("tiff", tiff);
        map.put("txt", txt);
        map.put("vcf", vcf);
        map.put("wav", wav);
        map.put("wbmp", wbmp);
        map.put("xls", xls);
        map.put("xlsx", xlsx);
        map.put("zip", zip);
    }
        
    public String getMimteString(String string){
        if(!map.getOrDefault(string, "not found").equalsIgnoreCase("not found"))
        {
            return map.get(string);
            
        }else{
            return "not found";
        }
    }
}
