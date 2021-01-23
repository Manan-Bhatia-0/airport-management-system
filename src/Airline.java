import java.io.Serializable;

/**
 * Airline.java
 * <p>
 * an interface to represent the three airlines.
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

public interface Airline extends Serializable {

    String welcomeMessage(); // information about the airlines.

    String getName(); // returns the name of the airline.

    String displayPassengers(); //displays all the current passengers

    void setGate(Gate gate);

    Gate getGate();

    void addPassenger(Passenger passenger);

    int getNumOfPassenger();

    int getMaxPassengers();

    Passenger[] getPassengers();

}