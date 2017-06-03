package test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.annotations.SourceType;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/23.
 */
public class T10 {
    public static void main(String[] args) {

        int Acounts=0;int Mcounts=0;int Dcounts=0;
        String resp="dsfds ";
        int revision=22;
        String commit_date="dsfds ";
        String username="dsfds ";
    //    System.out.println("{\"resp\":\""+resp+"\",\"revision\":"+revision+",\"commit_date\":\""+commit_date+"\",\"username\":\""+username+"\"}");
     //   System.out.println(" abc".substring(0,2));
        String fileName="abc.vv.nn";
     //   System.out.println(fileName.substring(fileName.lastIndexOf(".")+1));
     //   System.out.println("{\"counts\":["+Acounts+","+Mcounts+","+Dcounts+"],\"typeCounts\":["+0+","+0+","+0+","+0+","+0+"]}");


        ArrayList<String> arr=new ArrayList<String>();
        arr.add("a");
        arr.add("b");
        arr.add("c");
        arr.add("d");
        arr.add("e");


        JSONObject Json = new JSONObject();
        JSONArray JsonArray = new JSONArray();;;;;;

        Json.put("key", "value");;//JSONObject对象中添加键值对
        JsonArray.add(arr);//将JSONObject对象添加到Json数组中
        //System.out.println(Json);
      //  System.out.println(arr);
        ArrayList<String> commit_dateList=new ArrayList<String>();ArrayList<Integer> addLineList=new ArrayList<Integer>();ArrayList<Integer> reduceLineList=new ArrayList<Integer>();
        commit_dateList.add("as");
        commit_dateList.add("as");
        commit_dateList.add("as");

        System.out.println("{\"names\":"+ JSONArray.fromObject(commit_dateList).toString()+"}");







    }
}


