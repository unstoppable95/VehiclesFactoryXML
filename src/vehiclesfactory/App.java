package vehiclesfactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    /**
     * @param contentForXML order content from standard input
     * function which proceed detected order from standard input
     */
    private static void proceedOrder(String contentForXML) {
        //change input to document
        Document documentXML = null;
        try {
            documentXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(contentForXML)));
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

        //create vehiclesFactory
        VehiclesFactory factory = new VehiclesFactory();

        //create 3 threads for all vehicles in order
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //start threads
        for (int i = 0; i < typesOfVehicles.size(); i++) {
            executor.submit(new VehicleMaker(typesOfVehicles.get(i), order,factory));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //print result
        order.calculateOrderCost();
        System.out.println(order.getTotalCost());

    }
}
