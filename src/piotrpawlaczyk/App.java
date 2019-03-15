package piotrpawlaczyk;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    /**
     * @param args main method which controls which controls all processes in carsfactory
     */
    public static void main(String[] args) {

        //wait for input data
        while (true) {

            //read from standard input xml
            Scanner stdin = new Scanner(new BufferedInputStream(System.in));
            String contentXML = "";
            boolean startOrder = false;

            //read lines and start store content only which <order> tag appears, contentXML ends with </order>
            while (stdin.hasNext()) {
                String line = stdin.nextLine();
                if (line.equals("<order>")) startOrder = true;
                if (startOrder) {
                    contentXML += line;
                    if (line.equals("</order>")) break;
                }
            }
            proceedOrder(contentXML);
        }
    }

    private static void proceedOrder(String contentXML) {
        //change input to document
        Document documentXML = null;
        try {
            documentXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(contentXML)));
        } catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException occured");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException occured");
            ex.printStackTrace();
        } catch (SAXException ex) {
            System.out.println("SAXException occured");
            ex.printStackTrace();
        }

        //deserialize xml document
        XMLReader xmlReader = new XMLReader();
        ArrayList<String> typesOfVehicles = xmlReader.getData(documentXML);

        //create new order
        Order order = new Order();

        //create thread for every vehicle
        Thread[] threadArray = new Thread[typesOfVehicles.size()];

        //start threads
        for (int i = 0; i < typesOfVehicles.size(); i++) {
            VehicleMaker vehicleMaker = new VehicleMaker(typesOfVehicles.get(i), order);
            threadArray[i] = new Thread(vehicleMaker);
            threadArray[i].start();
        }

        //wait for complete order
        try {
            for (Thread t : threadArray)
                t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //print result
        order.calculateOrderCost();
        System.out.println(order.getTotalCost());

        //interrupt threads
        for (Thread t : threadArray) t.interrupt();

    }
}
