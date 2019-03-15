package piotrpawlaczyk;

import java.util.ArrayList;

public class App {
    /**
     * @param args
     * main method which controls which controls all processes in carsfactory
     */
    public static void main (String[] args){

        //deserialize xml file
        XMLReader xmlReader = new XMLReader();
        ArrayList<String> typesOfVehicles = xmlReader.getData();

        //create new order
        Order order = new Order();

        //create thread for every vehicle
        Thread [] threadArray = new Thread[typesOfVehicles.size()];

        //start threads
        for(int i=0; i<typesOfVehicles.size();i++){
            VehicleMaker vehicleMaker = new VehicleMaker(typesOfVehicles.get(i),order);
            threadArray[i]= new Thread(vehicleMaker);
            threadArray[i].start();
        }

        //wait for complete order
        try{
        for (Thread t : threadArray) t.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        //print result
        order.calculateOrderCost();
        System.out.println(order.getTotalCost());

    }
}
