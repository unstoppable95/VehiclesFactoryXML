package vehiclesfactory;

/**
 * class which produces vehicles
 */
public class VehiclesFactory {

    /**
     * @param type type of vehicle which we want to produce
     * @return kind of vehicle
     */
    public Vehicle createVehicle(String type){
        Vehicle v=null;
        if(type.equals("car")) {
            v=new Car();
        }
        else if (type.equals("truck")) {
            v= new Truck();
        }
        else if (type.equals("motorcycle")){
            v=new Motorcycle();
        }

        return v;
    }

}
