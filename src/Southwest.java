/**
 * SouthWest.java
 * <p>
 * Represents the SouthWest Airline.
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

public class Southwest implements Airline {

    private final int maxPassengers = 200;

    private Passenger[] passengers; //list of all passenger in this airline
    private int numOfPassenger; //keeps track of the num of passengers
    private Gate gate; //represents gate for the airline.

    public Southwest() {
        passengers = new Passenger[maxPassengers];
    }

    @Override
    public String welcomeMessage() {
        return "Southwest Airline is proud to offer flights to Purdue University.\n" +
                "We are happy to offer free WiFi, as well as our amazing snacks.\n" +
                "In addition, we offer flights for much cheaper than other airlines, and offer two free checked bags.\n"
                + "We hope you choose Southwest for your next flight.";
    }


    @Override
    public String getName() {
        return "Southwest Airline";
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
