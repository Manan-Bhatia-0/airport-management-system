/**
 * Alaska.java
 * <p>
 * Represents the Alaska Airline
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

public class Alaska implements Airline {

    private final int maxPassengers = 200;

    private Passenger[] passengers; //list of all passenger in this airline
    private int numOfPassenger; //keeps track of the num of passengers
    private Gate gate; //represents gate for the airline.

    public Alaska() {
        passengers = new Passenger[maxPassengers];
    }

    @Override
    public String welcomeMessage() {
        return "Alaska Airline is proud to serve the strong and knowledgeable Boilermakers from Purdue University.\n" +
                "We primarily fly westward, and often have stops in Alaska and California.\n" +
                "We have first class amenities, even in coach class.\n" +
                "We provide fun snacks like pretzels and goldfish.\n" +
                "We also have comfortable seats, and free WiFi.\n" +
                "We hope you choose Alaska Airline for your next itinerary!";
    }

    @Override
    public String getName() {
        return "Alaska Airline";
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