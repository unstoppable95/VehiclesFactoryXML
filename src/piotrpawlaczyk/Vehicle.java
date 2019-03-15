package piotrpawlaczyk;

/**
 * main abstract class Vehicle which is a supertype of every vehicle in cars factory
 */
public abstract class Vehicle {

    /**
     * production time in seconds
     */
    private int productionTime;
    /**
     * cost of vehicle's production
     */
    private int cost;

    /**
     * @param productionTime which means how long we need to product this vehicle (in seconds)
     * @param cost
     */
    public Vehicle(int productionTime, int cost){
        this.productionTime=productionTime;
        this.cost = cost ;
    }

    /**
     * @return time which we need to product vehicle
     */
    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
