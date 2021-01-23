import java.io.Serializable;
import java.util.Random;

/**
 * Gate.java
 * <p>
 * Represents Gates at the airport by assigning a random letter for terminal and a number for gate.
 *
 * @author Alan, Manan
 * @version 11/22/19
 */

public class Gate implements Serializable {
    private int gateNumber;
    private String terminal;

    public Gate() {
        Random rand = new Random();
        int terminalLetter = 65 + rand.nextInt(3);
        this.terminal = Character.toString((char) terminalLetter);
        this.gateNumber = 1 + rand.nextInt(19);
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getGate() {
        return terminal + gateNumber;
    }
}
