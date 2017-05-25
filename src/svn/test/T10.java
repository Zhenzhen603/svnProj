package svn.test;
import java.text.SimpleDateFormat;
import  java.util.*;
/**
 * Created by Administrator on 2017/5/23.
 */
public class T10 {
    public static void main(String[] args) {
        int Acounts=0;int Mcounts=0;int Dcounts=0;
        System.out.println("{");
        System.out.println("\"Acounts\":"+"\""+Acounts+"\",");
        System.out.println("\"Mcounts\":"+"\""+Mcounts+"\",");
        System.out.println("\"Dcounts\":"+"\""+Dcounts+"\"");
        System.out.println("}");
        System.out.println("{\"counts\":["+Acounts+","+Mcounts+","+Dcounts+"]}");
      //  System.out.println(new GregorianCalendar().getTimeZone().getID());
        System.out.println(new SimpleDateFormat("yyyy-M-d").format(new Date()));

    }
}


