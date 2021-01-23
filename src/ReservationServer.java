import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * ReservationServer.java
 * <p>
 * Represents the airport reservation server
 *
 * @author manan, alan
 * @version 11/22/19
 */

public class ReservationServer {

    private ServerSocket serverSocket;
    private File file;
    private Airline alaska;
    private Airline delta;
    private Airline southwest;

    //server runner
    public static void main(String[] args) {
        ReservationServer server;

        try {
            server = new ReservationServer(new File("reservation.txt"));
        } catch (Exception e) {
            e.printStackTrace();

            return;
        } //end try catch

        server.serveClients();
    } //main

    public ReservationServer(File file) throws NullPointerException, IOException {
        BufferedReader reader;
        String line;

        Objects.requireNonNull(file, "the specified file is null");
        this.file = file;

        this.serverSocket = new ServerSocket(0);
        int[] capacity = new int[3];
        String[][] passengers = new String[3][200];

        this.alaska = new Alaska();
        this.delta = new Delta();
        this.southwest = new Southwest();

        reader = new BufferedReader(new FileReader(file));

        line = reader.readLine();

        while (line != null) {

            if (line.contentEquals("Alaska passenger list")) {
                capacity[0] = 0;
                while (true) {
                    line = reader.readLine();
                    if (line.equals(""))
                        break;
                    passengers[0][capacity[0]++] = line;
                    reader.readLine();
                }
            }
            if (line.contentEquals("Delta passenger list")) {
                capacity[1] = 0;
                while (true) {
                    line = reader.readLine();
                    if (line.equals(""))
                        break;
                    passengers[1][capacity[1]++] = line;
                    reader.readLine();
                }
            }
            if (line.contentEquals("Southwest passenger list")) {
                capacity[2] = 0;
                while (true) {
                    line = reader.readLine();
                    if (line.equals(""))
                        break;
                    passengers[2][capacity[2]++] = line;
                    reader.readLine();
                }
            }


            line = reader.readLine();
        } //end while

        for (int i = 0; i < passengers.length; i++) {
            for (int j = 0; j < capacity[i]; j++) {
                String currentPassenger = passengers[i][j];
                String firstName = Character.toString(currentPassenger.charAt(0));
                String lastName = currentPassenger.substring(currentPassenger.indexOf(" ") + 1,
                        currentPassenger.indexOf(","));
                int age = Integer.parseInt(currentPassenger.substring(currentPassenger.indexOf(",") + 2));
                switch (i) {
                    case 0:
                        alaska.addPassenger(new Passenger(firstName, lastName, age));
                        break;
                    case 1:
                        delta.addPassenger(new Passenger(firstName, lastName, age));
                        break;
                    case 2:
                        southwest.addPassenger(new Passenger(firstName, lastName, age));
                        break;
                }
            }
        } //populates the airlines
        reader.close();

    } //ReservationServer

    /**
     * Serves the clients that connect to this server.
     */
    public void serveClients() {
        Socket clientSocket;
        Thread handlerThread;
        int clientCount = 0;

        System.out.printf("<Now serving clients on port %d...>%n", this.serverSocket.getLocalPort());

        while (true) {
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();

                return;
            } //end try catch

            handlerThread = new Thread(new ClientHandler(clientSocket, file, alaska, delta, southwest));

            handlerThread.start();

            System.out.printf("<Client %d connected...>%n", clientCount);

            clientCount++;
        } //end while
    } //serveClients

    /**
     * Returns the hash code of this server.
     *
     * @return the hash code of this server
     */

}

/**
 *
 * Client Handler
 *
 * @author Alan, Manan
 * @version 11-22-19
 *
 */
class ClientHandler implements Runnable {

    private Socket clientSocket;

    private Airline alaska;
    private Airline delta;
    private Airline southwest;
    private File file;

    public ClientHandler(Socket clientSocket, File file, Airline alaska, Airline delta, Airline southwest)
            throws NullPointerException {
        Objects.requireNonNull(clientSocket, "the specified client socket is null");
        Objects.requireNonNull(file, "the capacity is null");
        Objects.requireNonNull(alaska, "the specified airline is null");
        Objects.requireNonNull(delta, "the specified airline is null");
        Objects.requireNonNull(southwest, "the specified airline is null");

        this.clientSocket = clientSocket;
        this.alaska = alaska;
        this.delta = delta;
        this.southwest = southwest;
        this.file = file;

    } //CensoringRequestHandler

    /**
     * Handles the requests of the client connected to this request handler's client socket.
     */
    @Override
    public void run() {
        try {
            ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());

            Object input = reader.readObject();
            Object response = null;
            String currentAirline = "";
            while (input != null) {
                if (input instanceof String) {
                    if (input.equals("displayAlaskaPassengers"))
                        response = alaska.getPassengers();
                    else if (input.equals("displayDeltaPassengers"))
                        response = delta.getPassengers();
                    else if (input.equals("displaySouthwestPassengers"))
                        response = southwest.getPassengers();
                    else if (input.equals("maxNumOfAlaska"))
                        response = alaska.getMaxPassengers();
                    else if (input.equals("maxNumOfDelta"))
                        response = delta.getMaxPassengers();
                    else if (input.equals("maxNumOfSouthwest"))
                        response = southwest.getMaxPassengers();
                    else if (input.equals("numOfAlaska"))
                        response = alaska.getNumOfPassenger();
                    else if (input.equals("numOfDelta"))
                        response = delta.getNumOfPassenger();
                    else if (input.equals("numOfSouthwest"))
                        response = southwest.getNumOfPassenger();

                        //above is if you press "/" the serve send details about the airline from the file
                    else if (input.equals("alaskaMessage"))
                        response = alaska.welcomeMessage();
                    else if (input.equals("deltaMessage"))
                        response = delta.welcomeMessage();
                    else if (input.equals("southwestMessage"))
                        response = southwest.welcomeMessage();
                        //above displays the welcome message

                    else if (input.equals("Alaska")) {
                        currentAirline = "Alaska";
                        response = "alaskaConfirm";
                    } else if (input.equals("Delta")) {
                        currentAirline = "Delta";
                        response = "deltaConfirm";
                    } else if (input.equals("Southwest")) {
                        currentAirline = "Southwest";
                        response = "southwestConfirm";
                    }
                    //send to server: airline name first, then the passenger;
                } else if (input instanceof Passenger) {
                    switch (currentAirline) {
                        case "Alaska":
                            try {
                                ((Passenger) input).setBoardingPass(alaska);
                                writeToFile();
                                response = ((Passenger) input).getBoardingPass();
                            } catch (NoMorePlaceException e) {
                                response = e;
                            }
                            break;
                        case "Delta":
                            try {
                                ((Passenger) input).setBoardingPass(delta);
                                writeToFile();
                                response = ((Passenger) input).getBoardingPass();
                            } catch (NoMorePlaceException e) {
                                response = e;
                            }
                            break;
                        case "Southwest":
                            try {
                                ((Passenger) input).setBoardingPass(southwest);
                                writeToFile();
                                response = ((Passenger) input).getBoardingPass();
                            } catch (NoMorePlaceException e) {
                                response = e;
                            }
                            break;
                    }
                }

                writer.writeObject(response);
                writer.flush();
                input = reader.readObject();

            }
            reader.close();
            writer.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("User Disconnected!");
        }

    } //run

    public void writeToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write("ALASKA");
        writer.write("\n");
        writer.write(String.format("%d/%d", alaska.getNumOfPassenger(), alaska.getMaxPassengers()));
        writer.write("\n");
        writer.write("Alaska passenger list");
        writer.write("\n");
        for (Passenger passenger : alaska.getPassengers()) {
            writer.write(passenger.displayDetails());
            writer.write("\n");
            writer.write("--------------------ALASKA");
            writer.write("\n");
        }
        writer.write("\n");

        writer.write("DELTA");
        writer.write("\n");
        writer.write(String.format("%d/%d", delta.getNumOfPassenger(), delta.getMaxPassengers()));
        writer.write("\n");
        writer.write("Delta passenger list");
        writer.write("\n");
        for (Passenger passenger : delta.getPassengers()) {
            writer.write(passenger.displayDetails());
            writer.write("\n");
            writer.write("--------------------DELTA");
            writer.write("\n");
        }
        writer.write("\n");

        writer.write("SOUTHWEST");
        writer.write("\n");
        writer.write(String.format("%d/%d", southwest.getNumOfPassenger(), southwest.getMaxPassengers()));
        writer.write("\n");
        writer.write("Southwest passenger list");
        writer.write("\n");
        for (Passenger passenger : southwest.getPassengers()) {
            writer.write(passenger.displayDetails());
            writer.write("\n");
            writer.write("--------------------SOUTHWEST");
            writer.write("\n");
        }
        writer.write("\n");

        writer.write("EOF");
        writer.flush();
        writer.close();
    }
}