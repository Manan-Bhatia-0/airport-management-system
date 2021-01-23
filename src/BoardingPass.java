import java.io.Serializable;

/**
 * BoardingPass.java
 * <p>
 * Represents each flyer's boarding pass.
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

public class BoardingPass implements Serializable {

    private Passenger passenger;
    private Airline airline;
    private Gate gate;

    public BoardingPass(Passenger passenger, Airline airline) throws NoMorePlaceException {

        this.passenger = passenger;
        if (airline.getNumOfPassenger() >= airline.getMaxPassengers()) {
            throw new NoMorePlaceException();
        }
        this.airline = airline;

        //adds passenger to airline
        airline.addPassenger(passenger);

        //checks if the gate DNE and creates a new one
        if (airline.getGate() == null)
            airline.setGate(new Gate());

        this.gate = airline.getGate();

    }

    public String displayBoardingPass() {
        return String.format("-----------------------------------------------------------------------------" +
                        "\nBOARDING PASS FOR FLIGHT 18000 WITH %s\n" +
                        "PASSENGER FIRST NAME: %s\n" +
                        "PASSENGER LAST NAME: %s\n" +
                        "PASSENGER AGE: %d\n" +
                        "You can now begin boarding at gate %s\n" +
                        "-----------------------------------------------------------------------------",
                airline.getName(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getAge(),
                gate.getGate());
    }

    public Airline getAirline() {
        return airline;
    }

    public Gate getGate() {
        return gate;
    }

    public Passenger getPassenger() {
        return passenger;
    }
}

/**
 * Exception class
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

class NoMorePlaceException extends Exception {

    public NoMorePlaceException() {
        super();
    }

    public NoMorePlaceException(String message) {
        super(message);
    }
}
