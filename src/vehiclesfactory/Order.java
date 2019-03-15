package vehiclesfactory;

import java.util.ArrayList;

/**
 * class which stores vehiclesList and cost of them
 */
public class Order {

    /**
     * list of all vehicles in order
     */
    private ArrayList<Vehicle> vehicleList;
    /**
     * cost of all vehicles in order
     */
    private int totalCost;



    public Order(){
        this.vehicleList=new ArrayList<>();
        this.totalCost=0;
    }

    /**
     * @param v vehicle which we want to add to vehicleList
     */
    public synchronized void  addVehicleToOrder(Vehicle v){
        this.vehicleList.add(v);
    }

    /**
     * @return vehiclesList
     */
    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * calculate cost of vehicles
     */
    public void calculateOrderCost(){
        int tmpCost=0;
        for (Vehicle v : vehicleList){
            tmpCost+=v.getCost();
        }
        totalCost=tmpCost;
    }

    /**
     * @return cost of vehicles
     */
    public int getTotalCost() {
        return totalCost;
    }


}
