/**
 * 
 */
package com.otzg.wxpay.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  xml文件转换为map
 *  @author G/2016-1-4
 *
 */
public class XmlUtil{

    public static Map<String,Object> parse(String protocolXML) {
    	Map<String,Object> params = new HashMap<>();
    	try{   
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
    		DocumentBuilder builder = factory.newDocumentBuilder();   
            Document doc = builder.parse(new InputSource(new StringReader(protocolXML)));   
            Element root = doc.getDocumentElement();   
            NodeList books = root.getChildNodes();   
            if(books != null){   
                for (int i = 0; i < books.getLength(); i++) {   
                	Node book = books.item(i);
                	if(book.getFirstChild()!=null && book.getFirstChild().getNodeValue()!=null) {
                        params.put(book.getNodeName(), book.getFirstChild().getNodeValue());
                    }
                 }   
            }   
        }catch(Exception e){ 
            return null;   
        }
    	return params;
    }
    
    /**
     * 把一个map格式的数据转换成xml文件
     * @param dataMap  the data map
     * @return the string
     */
    public static String Map2Xml(Map<String,String> dataMap){
    	StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<xml>\n");
        Set<String> objSet = dataMap.keySet();
        for (String key : objSet){

            if (key == null
                    || key.toLowerCase().equals("key")){    //必须过滤掉key
                continue;
            }
            strBuilder.append("<").append(key.toString()).append(">");
            Object value = dataMap.get(key);
            strBuilder.append(value);
            strBuilder.append("</").append(key.toString()).append(">\n");
        }
        strBuilder.append("</xml>");
        return strBuilder.toString();
    }
}
