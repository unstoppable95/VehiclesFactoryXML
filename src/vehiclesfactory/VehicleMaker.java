package vehiclesfactory;

/**
 *  class which add vehicle produced by carsFactory class to current order
 */
public class VehicleMaker implements Runnable {

    /**
     * type of vehicle which we want produce
     */
    private String vehicleType;
    /**
     * reference to Factory which produces vehicles
     */
    private VehiclesFactory vehiclesFactory;
    /**
     * vehicle which we produced
     */
    private Vehicle v;
    /**
     * reference to current order
     */
    private Order order;

    /**
     * @param vehicleType type of vehicle which we want to produce
     * @param order reference to current order
     */
    public VehicleMaker(String vehicleType,Order order){
        this.vehicleType=vehicleType;
        this.vehiclesFactory=new VehiclesFactory();
        this.order=order;
    }


    /**
     * method which delegate vehicles's production to vehicleFactory,
     * wait vehicle's production time and then add vehicle to order
     */
    @Override
    public void run() {
        v = vehiclesFactory.createVehicle(vehicleType);
        try{
            Thread.sleep(v.getProductionTime()*1000);
        }catch (InterruptedException e){
            System.out.println("InterruptedException");
            e.printStackTrace();
        }
            order.addVehicleToOrder(v);
    }
}
