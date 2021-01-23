import org.w3c.dom.ls.LSOutput;

/**
 * Delta.java
 * <p>
 * Represents the Delta Airline
 *
 * @author Alan, Manan
 * @version 11/22/19
 */
public class Delta implements Airline {

    private final int maxPassengers = 200;

    private Passenger[] passengers; //list of all passenger in this airline
    private int numOfPassenger; //keeps track of the num of passengers
    private Gate gate; //represents gate for the airline.

    public Delta() {
        passengers = new Passenger[maxPassengers];
    }


    @Override
    public String welcomeMessage() {
        return "Delta Airline is proud to be one of the five premier Airline at Purdue University.\n" +
                "We offer extremely exceptional services, with free limited WiFi for all costumers.\n" +
                "Passengers who use T-Mobile as a cell phone carrier get additional benefits.\n" +
                "We are also happy to offer power outlets in each seat for passenger use.\n" +
                "We hope you choose to fly Delta as your next Airline.";
    }

    @Override
    public String getName() {
        return "Delta Airline";
    }

    @Override
    public Gate getGate() {
        return gate;
    }

    @Override
    public void setGate(Gate gate) {
        this.gate = gate;
    }

    //used to add a new Passenger to the airline
    @Override
    public synchronized void addPassenger(Passenger passenger) {
        passengers[numOfPassenger++] = passenger;
    }

    @Override
    public synchronized int getNumOfPassenger() {
        return numOfPassenger;
    }

    @Override
    public int getMaxPassengers() {
        return maxPassengers;
    }

    @Override
    public Passenger[] getPassengers() {
        Passenger[] actualPassengers = new Passenger[numOfPassenger];
        int counter = 0;
        for (Passenger passenger : passengers) {
            if (passenger == null)
                break;
            actualPassengers[counter++] = passenger;
        }
        return actualPassengers;
    }

    @Override
    public String displayPassengers() {
        return getPassengersDetail();
    }

    //private Method bc passengers data should be private to this class
    private String getPassengersDetail() {
        StringBuilder allPassengers = new StringBuilder();
        String split = "";
        for (Passenger passenger : passengers) {
            if (passenger == null)
                break;
            allPassengers.append(split).append(passenger.displayDetails());
            split = "\n";
        }
        return allPassengers.toString();
    }


}





