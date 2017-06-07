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


        System.out.println("{\"commit_date\":\""+commit_date+"\",\"addRevision\":"+0+",\"isTextType\":"+0+",\"content\":\""+"fff"+"\"}");

        String h="a\r\nb";
        System.out.println(string2Json("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<!--\n" +
                "  Licensed to the Apache Software Foundation (ASF) under one or more\n" +
                "  contributor license agreements.  See the NOTICE file distributed with\n" +
                "  this work for additional information regarding copyright ownership.\n" +
                "  The ASF licenses this file to You under the Apache License, Version 2.0\n" +
                "  (the \"License\"); you may not use this file except in compliance with\n" +
                "  the License.  You may obtain a copy of the License at\n" +
                "\n" +
                "      http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "  Unless required by applicable law or agreed to in writing, software\n" +
                "  distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "  See the License for the specific language governing permissions and\n" +
                "  limitations under the License.\n" +
                "-->\n" +
                "\n" +
                "<!--\n" +
                "  ** This XSD contains only the programatic elements required for an implementation.\n" +
                "  ** For the XSD from Sun that includes documentation and other copyrighted information\n" +
                "  ** please refer to http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd for a fully documented and latest\n" +
                "  **  XSD.\n" +
                "-->\n" +
                "\n" +
                "<xsd:schema targetNamespace=\"http://java.sun.com/xml/ns/javaee\" \n" +
                "            xmlns:javaee=\"http://java.sun.com/xml/ns/javaee\" \n" +
                "            xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \n" +
                "            elementFormDefault=\"qualified\" \n" +
                "            attributeFormDefault=\"unqualified\" \n" +
                "            version=\"2.1\">\n" +
                "\n" +
                "    <xsd:include schemaLocation=\"javaee_5.xsd\" />\n" +
                "\n" +
                "    <xsd:element name=\"taglib\" type=\"javaee:tldTaglibType\">\n" +
                "        <xsd:unique name=\"tag-name-uniqueness\">\n" +
                "            <xsd:selector xpath=\"javaee:tag|javaee:tag-file\" />\n" +
                "            <xsd:field xpath=\"javaee:name\" />\n" +
                "        </xsd:unique>\n" +
                "\n" +
                "        <xsd:unique name=\"function-name-uniqueness\">\n" +
                "            <xsd:selector xpath=\"javaee:function\" />\n" +
                "            <xsd:field xpath=\"javaee:name\" />\n" +
                "        </xsd:unique>\n" +
                "    </xsd:element>\n" +
                "\n" +
                "    <xsd:complexType name=\"body-contentType\">\n" +
                "        <xsd:simpleContent>\n" +
                "            <xsd:restriction base=\"javaee:string\">\n" +
                "                <xsd:enumeration value=\"tagdependent\" />\n" +
                "                <xsd:enumeration value=\"JSP\" />\n" +
                "                <xsd:enumeration value=\"empty\" />\n" +
                "                <xsd:enumeration value=\"scriptless\" />\n" +
                "            </xsd:restriction>\n" +
                "        </xsd:simpleContent>\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"extensibleType\" abstract=\"true\">\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"functionType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:group ref=\"javaee:descriptionGroup\" />\n" +
                "            <xsd:element name=\"name\" type=\"javaee:tld-canonical-nameType\"></xsd:element>\n" +
                "            <xsd:element name=\"function-class\" type=\"javaee:fully-qualified-classType\"></xsd:element>\n" +
                "            <xsd:element name=\"function-signature\" type=\"javaee:string\"></xsd:element>\n" +
                "            <xsd:element name=\"example\" type=\"javaee:xsdStringType\" minOccurs=\"0\"></xsd:element>\n" +
                "            <xsd:element name=\"function-extension\" type=\"javaee:tld-extensionType\" minOccurs=\"0\" maxOccurs=\"unbounded\"></xsd:element>\n" +
                "\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tagFileType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:group ref=\"javaee:descriptionGroup\" />\n" +
                "            <xsd:element name=\"name\" type=\"javaee:tld-canonical-nameType\" />\n" +
                "            <xsd:element name=\"path\" type=\"javaee:pathType\" />\n" +
                "            <xsd:element name=\"example\" type=\"javaee:xsdStringType\" minOccurs=\"0\"></xsd:element>\n" +
                "            <xsd:element name=\"tag-extension\" type=\"javaee:tld-extensionType\" minOccurs=\"0\" maxOccurs=\"unbounded\"></xsd:element>\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tagType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:group ref=\"javaee:descriptionGroup\" />\n" +
                "            <xsd:element name=\"name\" type=\"javaee:tld-canonical-nameType\" />\n" +
                "            <xsd:element name=\"tag-class\" type=\"javaee:fully-qualified-classType\"></xsd:element>\n" +
                "            <xsd:element name=\"tei-class\" type=\"javaee:fully-qualified-classType\" minOccurs=\"0\"></xsd:element>\n" +
                "            <xsd:element name=\"body-content\" type=\"javaee:body-contentType\"></xsd:element>\n" +
                "            <xsd:element name=\"variable\" type=\"javaee:variableType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "            <xsd:element name=\"attribute\" type=\"javaee:tld-attributeType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "            <xsd:element name=\"dynamic-attributes\" type=\"javaee:generic-booleanType\" minOccurs=\"0\" />\n" +
                "            <xsd:element name=\"example\" type=\"javaee:xsdStringType\" minOccurs=\"0\" />\n" +
                "            <xsd:element name=\"tag-extension\" type=\"javaee:tld-extensionType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tld-attributeType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:element name=\"description\" type=\"javaee:descriptionType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "\n" +
                "            <xsd:element name=\"name\" type=\"javaee:java-identifierType\" />\n" +
                "            <xsd:element name=\"required\" type=\"javaee:generic-booleanType\" minOccurs=\"0\"></xsd:element>\n" +
                "            <xsd:choice>\n" +
                "\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:sequence minOccurs=\"0\">\n" +
                "                        <xsd:element name=\"rtexprvalue\" type=\"javaee:generic-booleanType\"></xsd:element>\n" +
                "\n" +
                "                        <xsd:element name=\"type\" type=\"javaee:fully-qualified-classType\" minOccurs=\"0\"></xsd:element>\n" +
                "                    </xsd:sequence>\n" +
                "\n" +
                "                    <xsd:choice>\n" +
                "                        <xsd:element name=\"deferred-value\" type=\"javaee:tld-deferred-valueType\" minOccurs=\"0\"></xsd:element>\n" +
                "                        <xsd:element name=\"deferred-method\" type=\"javaee:tld-deferred-methodType\" minOccurs=\"0\"></xsd:element>\n" +
                "                    </xsd:choice>\n" +
                "                </xsd:sequence>\n" +
                "\n" +
                "                <xsd:element name=\"fragment\" type=\"javaee:generic-booleanType\" minOccurs=\"0\"></xsd:element>\n" +
                "\n" +
                "            </xsd:choice>\n" +
                "\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tld-canonical-nameType\">\n" +
                "        <xsd:simpleContent>\n" +
                "            <xsd:restriction base=\"javaee:xsdNMTOKENType\" />\n" +
                "        </xsd:simpleContent>\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tld-deferred-methodType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:element name=\"method-signature\" type=\"javaee:string\" minOccurs=\"0\"></xsd:element>\n" +
                "        </xsd:sequence>\n" +
                "\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tld-deferred-valueType\">\n" +
                "\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:element name=\"type\" type=\"javaee:fully-qualified-classType\" minOccurs=\"0\"></xsd:element>\n" +
                "\n" +
                "        </xsd:sequence>\n" +
                "\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tld-extensionType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:element name=\"extension-element\" type=\"javaee:extensibleType\" maxOccurs=\"unbounded\" />\n" +
                "        </xsd:sequence>\n" +
                "\n" +
                "        <xsd:attribute name=\"namespace\" use=\"required\" type=\"xsd:anyURI\" />\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"tldTaglibType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:group ref=\"javaee:descriptionGroup\" />\n" +
                "            <xsd:element name=\"tlib-version\" type=\"javaee:dewey-versionType\"></xsd:element>\n" +
                "\n" +
                "            <xsd:element name=\"short-name\" type=\"javaee:tld-canonical-nameType\">\n" +
                "\n" +
                "            </xsd:element>\n" +
                "\n" +
                "            <xsd:element name=\"uri\" type=\"javaee:xsdAnyURIType\" minOccurs=\"0\">\n" +
                "\n" +
                "            </xsd:element>\n" +
                "            <xsd:element name=\"validator\" type=\"javaee:validatorType\" minOccurs=\"0\">\n" +
                "\n" +
                "            </xsd:element>\n" +
                "            <xsd:element name=\"listener\" type=\"javaee:listenerType\" minOccurs=\"0\" maxOccurs=\"unbounded\"></xsd:element>\n" +
                "            <xsd:element name=\"tag\" type=\"javaee:tagType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "            <xsd:element name=\"tag-file\" type=\"javaee:tagFileType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "            <xsd:element name=\"function\" type=\"javaee:functionType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "            <xsd:element name=\"taglib-extension\" type=\"javaee:tld-extensionType\" minOccurs=\"0\" maxOccurs=\"unbounded\"></xsd:element>\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"version\" type=\"javaee:dewey-versionType\" fixed=\"2.1\" use=\"required\"></xsd:attribute>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"validatorType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:element name=\"description\" type=\"javaee:descriptionType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "            <xsd:element name=\"validator-class\" type=\"javaee:fully-qualified-classType\"></xsd:element>\n" +
                "            <xsd:element name=\"init-param\" type=\"javaee:param-valueType\" minOccurs=\"0\" maxOccurs=\"unbounded\"></xsd:element>\n" +
                "\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"variable-scopeType\">\n" +
                "        <xsd:simpleContent>\n" +
                "\n" +
                "            <xsd:restriction base=\"javaee:string\">\n" +
                "                <xsd:enumeration value=\"NESTED\" />\n" +
                "                <xsd:enumeration value=\"AT_BEGIN\" />\n" +
                "                <xsd:enumeration value=\"AT_END\" />\n" +
                "            </xsd:restriction>\n" +
                "        </xsd:simpleContent>\n" +
                "    </xsd:complexType>\n" +
                "\n" +
                "    <xsd:complexType name=\"variableType\">\n" +
                "        <xsd:sequence>\n" +
                "            <xsd:element name=\"description\" type=\"javaee:descriptionType\" minOccurs=\"0\" maxOccurs=\"unbounded\" />\n" +
                "\n" +
                "            <xsd:choice>\n" +
                "                <xsd:element name=\"name-given\" type=\"javaee:java-identifierType\"></xsd:element>\n" +
                "\n" +
                "                <xsd:element name=\"name-from-attribute\" type=\"javaee:java-identifierType\"></xsd:element>\n" +
                "            </xsd:choice>\n" +
                "            <xsd:element name=\"variable-class\" type=\"javaee:fully-qualified-classType\" minOccurs=\"0\"></xsd:element>\n" +
                "\n" +
                "            <xsd:element name=\"declare\" type=\"javaee:generic-booleanType\" minOccurs=\"0\"></xsd:element>\n" +
                "            <xsd:element name=\"scope\" type=\"javaee:variable-scopeType\" minOccurs=\"0\"></xsd:element>\n" +
                "        </xsd:sequence>\n" +
                "        <xsd:attribute name=\"id\" type=\"xsd:ID\" />\n" +
                "    </xsd:complexType>\n" +
                "</xsd:schema>\n"));



    }

    public static  String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            switch (c){
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}


