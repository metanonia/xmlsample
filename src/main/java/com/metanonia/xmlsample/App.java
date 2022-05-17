package com.metanonia.xmlsample;

import com.metanonia.xmlsample.service.RestService;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class App {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, JDOMException {
        String key = args[0];
        String path = "http://apis.data.go.kr/1360000/TourStnInfoService/getTourStnVilageFcst" +
                "?serviceKey=" + key + "&numOfRows=100&pageNo=1" +
                "&CURRENT_DATE=2022051710&HOUR=24&COURSE_ID=1";
        String ret = RestService.Rest("GET", path, null);
        System.out.println(ret);
        System.out.println("====================================================================");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(path);
        document.getDocumentElement().normalize();
        System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
        NodeList nList = document.getElementsByTagName("item");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("TM : " + eElement.getElementsByTagName("tm").item(0).getTextContent());
                System.out.println("thema : " + eElement.getElementsByTagName("thema").item(0).getTextContent());
                System.out.println("courseId : " + eElement.getElementsByTagName("courseId").item(0).getTextContent());
                System.out.println("courseAreaId : " + eElement.getElementsByTagName("courseAreaId").item(0).getTextContent());
                System.out.println("courseAreaName : " + eElement.getElementsByTagName("courseAreaName").item(0).getTextContent());
                System.out.println("courseName : " + eElement.getElementsByTagName("courseName").item(0).getTextContent());
                System.out.println("spotAreaId : " + eElement.getElementsByTagName("spotAreaId").item(0).getTextContent());
                System.out.println("spotAreaName : " + eElement.getElementsByTagName("spotAreaName").item(0).getTextContent());
                System.out.println("spotName : " + eElement.getElementsByTagName("spotName").item(0).getTextContent());
                System.out.println("th3 : " + eElement.getElementsByTagName("th3").item(0).getTextContent());
                System.out.println("wd : " + eElement.getElementsByTagName("wd").item(0).getTextContent());
                System.out.println("ws : " + eElement.getElementsByTagName("ws").item(0).getTextContent());
                System.out.println("sky : " + eElement.getElementsByTagName("sky").item(0).getTextContent());
                System.out.println("rhm : " + eElement.getElementsByTagName("rhm").item(0).getTextContent());
                System.out.println("pop : " + eElement.getElementsByTagName("pop").item(0).getTextContent());
            }
        }
        System.out.println("====================================================================");
        SAXBuilder saxBuilder = new SAXBuilder();
        InputStream is = new ByteArrayInputStream(ret.getBytes(StandardCharsets.UTF_8));
        org.jdom2.Document document2 = saxBuilder.build(is);
        System.out.println("Root element :" + document2.getRootElement().getName());
        org.jdom2.Element rootElement = document2.getRootElement();
        List<org.jdom2.Element> list = rootElement.getChildren();
        for(org.jdom2.Element element: list) {
            if(element.getName().matches("body")) {
                List<org.jdom2.Element> list1 = element.getChildren();
                for(org.jdom2.Element element1: list1) {
                    if(element1.getName().matches("items")) {
                        List<org.jdom2.Element> list2 = element1.getChildren();
                        for(org.jdom2.Element element2: list2) {
                            if(element2.getName().matches("item")) {
                                List<org.jdom2.Element> list3 = element2.getChildren();
                                for(org.jdom2.Element element3: list3) {
                                    System.out.println(element3.getName()+" : " + element3.getContent(0).getValue());
                                }
                                System.out.println();
                            }
                        }
                    }
                }
            }
        }
        System.out.println("====================================================================");
        JSONObject jsonObject = XML.toJSONObject(ret);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray jsonArray = items.getJSONArray("item");
        for(Object obj: jsonArray) {
            JSONObject item = (JSONObject) obj;
            System.out.println("courseAreaId " + item.getBigInteger("courseAreaId").toString());
            System.out.println("sky " + item.getBigInteger("sky").toString());
            System.out.println("wd " + item.getBigInteger("wd").toString());
            System.out.println("spotAreaId " + item.getBigInteger("spotAreaId").toString());
            System.out.println("pop " + item.getBigInteger("pop").toString());
            System.out.println("rhm " + item.getBigInteger("rhm").toString());
            System.out.println("ws " + item.getBigInteger("ws").toString());
            System.out.println("courseId " + item.getBigInteger("courseId").toString());
            System.out.println("th3 " + item.getBigInteger("th3").toString());
            System.out.println("spotAreaName " + item.getString("spotAreaName"));
            System.out.println("courseAreaName " + item.getString("courseAreaName"));
            System.out.println("courseName " + item.getString("courseName"));
            System.out.println("thema " + item.getString("thema"));
            System.out.println("spotName " + item.getString("spotName"));
            System.out.println("tm " + item.getString("tm"));
        }
        System.out.println("");
    }
}
