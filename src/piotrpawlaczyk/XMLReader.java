package piotrpawlaczyk;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class which is responsible for every operation on xml input
 */
public class XMLReader {

    /**
     * @return types of vehicles deserialized from xml
     */
    public ArrayList<String> getData() {
        ArrayList<String> orderContent = new ArrayList<>();

        try {
            //open xml file which contains order
            File file = new File("SampleOrder.xml");

            //parse xml from file
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            //get all nodes from parsed xml
            NodeList listOfVehicles = document.getElementsByTagName("item");

            //add every node to list
            for (int i = 0; i < listOfVehicles.getLength(); i++) {
                Node node = listOfVehicles.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    orderContent.add(eElement.getAttribute("type"));
                }
            }
        } catch (IOException ex) {
            System.out.println("File doesn't exist");
            ex.printStackTrace();
        } catch (SAXException ex) {
            System.out.println("SAX Exception");
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            System.out.println("ParserException");
            ex.printStackTrace();
        }

        return orderContent;
    }
}
