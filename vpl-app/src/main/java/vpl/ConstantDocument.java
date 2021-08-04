package vpl;
 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 *
 * @author Quanzhou Yu
 */
public class ConstantDocument {
    String filePath;
    Document document;
    public ConstantDocument(String path){
        filePath = path;
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(fXmlFile);
            document.getDocumentElement().normalize();
        }catch(Exception e){ 
            e.printStackTrace();
        } 
    }
    public Element getScopeByName(String name){
        NodeList nList = document.getElementsByTagName(name);
        Element e = (Element)nList.item(0);
        return e;
    }
    public String getElementContent(Element e, String attr){
        String data = "";
        data = e.getElementsByTagName(attr).item(0).getTextContent();
        return data;
    }
    public Element getElementByName(Element e, String name){
        NodeList nList = e.getElementsByTagName(name);
        Element e1 = (Element)nList.item(0);
        return e1;
    }
    
}
