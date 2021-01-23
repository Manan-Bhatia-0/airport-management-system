import java.io.Serializable;

/**
 * Passenger.java
 * <p>
 * Represents the passengers of each airline.
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

public class Passenger implements Serializable {

    private String firstName;
    private String lastName;
    private int age; //parsed from user input
    private BoardingPass boardingPass;

    public Passenger(String firstName, String lastName, int age) throws NullPointerException, IllegalArgumentException {
        //use try catch with GUIs to catch the exceptions using bit-wise OR.

        if (firstName == null | lastName == null)
            throw new NullPointerException();

        if (age < 0)
            throw new IllegalArgumentException();

        //the regex matches chars from a-z and A-Z as well as the char -(dash)
        if (!firstName.matches("[a-zA-Z-]*"))
            throw new IllegalArgumentException();

        if (!lastName.matches("[a-zA-Z-]*"))
            throw new IllegalArgumentException();

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;

    }

    //method to set the boarding pass of a passenger depending on whether they purchased a ticket or not.

    //airline is determined by selection in GUI
    public void setBoardingPass(Airline airline) throws NoMorePlaceException {
        this.boardingPass = new BoardingPass(this, airline);
    }

    public BoardingPass getBoardingPass() {
        return boardingPass;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String displayDetails() {
        return getFirstName().charAt(0) + ". " + getLastName() + ", " + getAge();
    }
}
