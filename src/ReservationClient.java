import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ReservationClient.java
 * <p>
 * Represents the client.
 *
 * @author manan, alan
 * @version 11/22/19
 */

public class ReservationClient {

    private String firstName;
    private String lastName;
    private int age;
    private String airline;
    private String message;
    private Passenger passenger;
    private BoardingPass boardingPass;
    private String passengerDetails;
    private int maxNumOfPassengers;
    private int numOfPassengers;
    private ResponseListener responseListener;
    private ObjectOutputStream request;
    ///

    ///Test Client for the server

    ///

    ///

    public ReservationClient() {
        String hostname;
        String portString;
        int port;
        Socket socket = null;
        hostname = JOptionPane.showInputDialog(null,
                "What is the hostname you'd like to connect to?",
                "Hostname?", JOptionPane.INFORMATION_MESSAGE);
        if (hostname == null) {
            JOptionPane.showMessageDialog(null, "Goodbye!",
                    "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            portString = JOptionPane.showInputDialog(null,
                    "What is the port you'd like to connect to?",
                    "Port?", JOptionPane.INFORMATION_MESSAGE);
            if (portString == null) {
                JOptionPane.showMessageDialog(null, "Goodbye!",
                        "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                port = Integer.parseInt(portString);
                try {
                    socket = new Socket(hostname, port);
                    this.request = new ObjectOutputStream(socket.getOutputStream());
                    this.responseListener = new ResponseListener(socket);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //end if
        } //end if


    }

    public static void main(String[] args) throws IOException {
        ReservationClient client = new ReservationClient();
        SwingUtilities.invokeLater(client::gui);
    }


    private JPanel currentJPanel;
    private JFrame jFrame;
    private final String[] currentAirline = {null};
    private boolean keyListenerActive = false;

    public void gui() {
        String title = "Purdue University Flight Reservation System";
        jFrame = new JFrame(title);
        jFrame.setSize(720, 480);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.add(stage2());
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH && keyListenerActive) {
                    refresh();
                    popup(currentAirline[0] + " Airlines", passengerDetails, numOfPassengers,
                            maxNumOfPassengers);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jFrame.setVisible(true);
    }

    public JPanel stage2() {
        keyListenerActive = false;
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JLabel welcomeMessage = new JLabel(
                "Welcome to the Purdue University Airline Reservation Management System!", JLabel.CENTER);
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel logo = new JPanel(new GridLayout());
        JLabel picLabel = new JLabel();
        picLabel.setIcon(new ImageIcon(new ImageIcon("PurdueLogo.png")
                .getImage().getScaledInstance(480, 262, Image.SCALE_SMOOTH)));
        picLabel.setHorizontalAlignment(0);
        logo.add(picLabel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton exit = new JButton("Exit");
        JButton bookAFlight = new JButton("Book a Flight");
        buttons.setLayout(new FlowLayout());
        buttons.add(exit);
        buttons.add(bookAFlight);

        jPanel.add(welcomeMessage, BorderLayout.NORTH);
        jPanel.add(logo, BorderLayout.CENTER);
        jPanel.add(buttons, BorderLayout.SOUTH);

        exit.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Purdue University Airline Management System!",
                    "Thank you!", JOptionPane.INFORMATION_MESSAGE);
            jFrame.dispose();
        });
        bookAFlight.addActionListener(actionEvent -> {
            jFrame.remove(currentJPanel);
            jFrame.add(stage3());
            jFrame.setVisible(true);
        });
        currentJPanel = jPanel;
        return jPanel;
    }
    
    public JPanel stage3() {
        keyListenerActive = false;
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JLabel welcomeMessage = new JLabel("Do you want to book a flight today?", JLabel.CENTER);
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel logo = new JPanel(new GridLayout());
        JLabel picLabel = new JLabel();
        picLabel.setIcon(new ImageIcon(new ImageIcon("PurdueLogo.png")
                .getImage().getScaledInstance(480, 262, Image.SCALE_SMOOTH)));
        picLabel.setHorizontalAlignment(0);
        logo.add(picLabel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton exit = new JButton("Exit");
        JButton bookAFlight = new JButton("Yes, I want to book a flight.");
        buttons.setLayout(new FlowLayout());
        buttons.add(exit);
        buttons.add(bookAFlight);

        jPanel.add(welcomeMessage, BorderLayout.NORTH);
        jPanel.add(logo, BorderLayout.CENTER);
        jPanel.add(buttons, BorderLayout.SOUTH);

        exit.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Purdue University Airline Management System!",
                    "Thank you!", JOptionPane.INFORMATION_MESSAGE);
            jFrame.dispose();
        });
        bookAFlight.addActionListener(actionEvent -> {
            jFrame.remove(currentJPanel);
            jFrame.add(stage4());
            jFrame.setVisible(true);
            jFrame.setFocusable(true);
            jFrame.requestFocus();
        });
        currentJPanel = jPanel;
        return jPanel;
    }

    public JPanel stage4() {
        keyListenerActive = true;
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        //title message and dropdown
        JPanel top = new JPanel(new BorderLayout());
        JLabel welcomeMessage = new JLabel("Choose a flight from the drop down menu.", JLabel.CENTER);
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel box = new JPanel(new FlowLayout());
        JComboBox<String> dropDown = new JComboBox<>(new String[]{"Alaska", "Delta", "Southwest"});
        dropDown.setSelectedIndex(1);
        box.add(dropDown);
        top.add(welcomeMessage, BorderLayout.NORTH);
        top.add(box, BorderLayout.SOUTH);
        //title message

        //buttons
        JPanel buttons = new JPanel();
        JButton exit = new JButton("Exit");
        JButton bookAFlight = new JButton("Choose this flight");
        buttons.setLayout(new FlowLayout());
        buttons.add(exit);
        buttons.add(bookAFlight);
        //buttons

        //airline message
        JPanel messagePanel = new JPanel(new GridBagLayout());
        JTextPane message1 = new JTextPane();


        message1.setSize(jFrame.getSize());
        message1.setEditable(false);
        message1.setAlignmentX(Component.CENTER_ALIGNMENT);
        message1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        sendToServer("deltaMessage");
        currentAirline[0] = "Delta";

        message1.setBackground(UIManager.getColor("Label.background"));
        message1.setFont(UIManager.getFont("Label.font"));
        message1.setBorder(UIManager.getBorder("Label.border"));
        responseListener.listenToServer();
        String serverMessage = responseListener.getMessage();
        refresh();
        message1.setText(serverMessage);

        messagePanel.add(message1);
        //airline message

        jPanel.add(top, BorderLayout.NORTH);
        jPanel.add(messagePanel, BorderLayout.CENTER);
        jPanel.add(buttons, BorderLayout.SOUTH);

        dropDown.addActionListener(e -> {
            int index = dropDown.getSelectedIndex();
            switch (index) {
                case 0:
                    sendToServer("alaskaMessage");
                    currentAirline[0] = "Alaska";
                    break;
                case 1:
                    sendToServer("deltaMessage");
                    currentAirline[0] = "Delta";
                    break;
                case 2:
                    sendToServer("southwestMessage");
                    currentAirline[0] = "Southwest";
                    break;
            }
            responseListener.listenToServer();
            message1.setText(responseListener.getMessage());

            jFrame.remove(currentJPanel);
            jFrame.add(jPanel);
            jFrame.setVisible(true);
        });
        exit.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Purdue University Airline Management System!",
                    "Thank you!", JOptionPane.INFORMATION_MESSAGE);
            jFrame.dispose();
        });
        bookAFlight.addActionListener(actionEvent -> {
            jFrame.remove(currentJPanel);
            jFrame.add(stage5());
            jFrame.setVisible(true);
        });


        currentJPanel = jPanel;
        return jPanel;
    }

    public JPanel stage5() {
        keyListenerActive = true;
        JPanel jPanel = new JPanel(new BorderLayout());

        JPanel title = new JPanel(new GridLayout(2, 1));
        JLabel title1 = new JLabel("Are you sure you want to book a flight on");
        JLabel title2 = new JLabel(currentAirline[0] + " Airlines?");
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title2.setFont(new Font(Font.SERIF, Font.BOLD, 20));

        title.add(title1);
        title.add(title2);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton exit = new JButton("Exit");
        JButton no = new JButton("No, I want a different flight.");
        JButton yes = new JButton("Yes, I want this flight.");
        buttons.add(exit);
        buttons.add(no);
        buttons.add(yes);

        jPanel.add(title, BorderLayout.NORTH);
        jPanel.add(buttons, BorderLayout.SOUTH);
        jPanel.setVisible(true);
        jPanel.setFocusable(true);
        jPanel.requestFocus();


        exit.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Purdue University Airline Management System!",
                    "Thank you!", JOptionPane.INFORMATION_MESSAGE);
            jFrame.dispose();
        });

        no.addActionListener(e -> {
            jFrame.remove(currentJPanel);
            jFrame.add(stage4());
            jFrame.setVisible(true);
        });

        yes.addActionListener(e -> {
            jFrame.remove(currentJPanel);
            jFrame.add(stage6());
            jFrame.setVisible(true);
        });

        currentJPanel = jPanel;
        return jPanel;

    }

    public JPanel stage6() {
        keyListenerActive = true;
        // to be parsed from the String input from the GUI.
        JPanel jPanel = new JPanel(new BorderLayout());
        JLabel titleMessage = new JLabel("Please input you information below.", JLabel.CENTER);
        titleMessage.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel firstNamePanel = new JPanel(new BorderLayout());
        JLabel title1 = new JLabel("What is your first name?");
        JTextField firstNameInput = new JTextField();
        firstNamePanel.add(title1, BorderLayout.NORTH);
        firstNamePanel.add(firstNameInput, BorderLayout.CENTER);


        JPanel lastNamePanel = new JPanel(new BorderLayout());
        JLabel title2 = new JLabel("What is your last name?");
        JTextField lastNameInput = new JTextField();
        lastNamePanel.add(title2, BorderLayout.NORTH);
        lastNamePanel.add(lastNameInput, BorderLayout.CENTER);


        JPanel agePanel = new JPanel(new BorderLayout());
        JLabel title3 = new JLabel("What is your age?");
        JTextField ageInput = new JTextField();
        agePanel.add(title3, BorderLayout.NORTH);
        agePanel.add(ageInput, FlowLayout.CENTER);


        JPanel textboxes = new JPanel(new GridLayout(3, 1));
        textboxes.add(firstNamePanel);
        textboxes.add(lastNamePanel);
        textboxes.add(agePanel);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton exit = new JButton("Exit");
        JButton next = new JButton("Next");
        buttons.add(exit);
        buttons.add(next);
        exit.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Purdue University Airline Management System!",
                    "Thank you!", JOptionPane.INFORMATION_MESSAGE);
            jFrame.dispose();
        });
        next.addActionListener(actionEvent -> {
            try {
                firstName = firstNameInput.getText();
                lastName = lastNameInput.getText();
                age = Integer.parseInt(ageInput.getText());
                this.passenger = new Passenger(firstName, lastName, age);
            } catch (IllegalArgumentException | NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Please enter the correct details!",
                        "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
                jFrame.remove(currentJPanel);
                jFrame.add(stage6());
                jFrame.setVisible(true);
            }

            stage7(passenger);
            jFrame.setVisible(true);
        });
        jPanel.add(titleMessage, BorderLayout.NORTH);
        jPanel.add(textboxes, BorderLayout.CENTER);
        jPanel.add(buttons, BorderLayout.SOUTH);

        currentJPanel = jPanel;
        return jPanel;

    }


    //this.passenger = new Passenger(firstName, lastName, age);
    public void stage7(Passenger thisPassenger) {
        this.firstName = thisPassenger.getFirstName();
        this.lastName = thisPassenger.getLastName();
        this.age = thisPassenger.getAge();
        keyListenerActive = false;
        String message2 = String.format(" Are all the details you entered correct?\n" +
                " The Passenger's name is %s %s and their age is %d.\n" +
                " If all the information shown is correct, select the Yes button below, " +
                "otherwise, select the No button.", firstName, lastName, age);
        int confirm = JOptionPane.showConfirmDialog(null, message2, "Confirm Info",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            System.out.println("backedOut");
            jFrame.remove(currentJPanel);
            jFrame.add(stage6());
            jFrame.setVisible(true);
            //stage6();
        } else if (confirm == JOptionPane.YES_OPTION) {
            sendToServer(currentAirline[0]);
            responseListener.listenToServer();
            if (responseListener.isAirlineConfirmation())
                sendToServer(this.passenger);
            responseListener.listenToServer();
            this.boardingPass = responseListener.getBoardingPass();

            jFrame.remove(currentJPanel);
            jFrame.add(stage8());
            jFrame.setVisible(true);
        }
    }

    public JPanel stage8() {
        keyListenerActive = false;
        JPanel jPanel = new JPanel(new BorderLayout());

        JPanel title = new JPanel(new GridLayout(3, 1));
        JLabel title1 = new JLabel("Flight data displaying for " + currentAirline[0] + " Airlines");
        JLabel title2 = new JLabel("Enjoy your flight!");
        JLabel title3 = new JLabel("Flight is now boarding at Gate " + boardingPass.getGate().getGate());
        JLabel title4 = new JLabel(String.format("%d : %d", numOfPassengers, maxNumOfPassengers));
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title3.setHorizontalAlignment(SwingConstants.CENTER);
        title4.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title2.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title3.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title4.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.add(title1);
        title.add(title2);
        title.add(title3);
        title.add(title4);


        JPanel body = new JPanel(new BorderLayout());
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setOpaque(false);

        textPane.setText(passengerDetails);
        textPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textPane);
        scrollPane.setVerticalScrollBar(new JScrollBar());


        JTextPane boardingPassPane = new JTextPane();
        boardingPassPane.setEditable(false);
        boardingPassPane.setOpaque(false);
        boardingPassPane.setText(this.boardingPass.displayBoardingPass());

        body.add(scrollPane, BorderLayout.CENTER);
        body.add(boardingPassPane, BorderLayout.SOUTH);


        JPanel buttons = new JPanel(new FlowLayout());
        JButton exit = new JButton("Exit");
        JButton refresh = new JButton("Refresh Flight Status");
        buttons.add(exit);
        buttons.add(refresh);
        exit.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Purdue University Airline Management System!",
                    "Thank you!", JOptionPane.INFORMATION_MESSAGE);
            jFrame.dispose();
        });


        jPanel.add(title, BorderLayout.NORTH);
        jPanel.add(body, BorderLayout.CENTER);
        jPanel.add(buttons, BorderLayout.SOUTH);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
                jFrame.remove(currentJPanel);
                jFrame.add(stage8());
                jFrame.setVisible(true);
            }
        });

        currentJPanel = jPanel;
        return jPanel;
    }

    public void refresh() {
        sendToServer("display" + currentAirline[0] + "Passengers");
        responseListener.listenToServer();
        passengerDetails = responseListener.getPassengers();

        sendToServer("numOf" + currentAirline[0]);
        responseListener.listenToServer();
        numOfPassengers = responseListener.getNumOfPassengers();

        sendToServer("maxNumOf" + currentAirline[0]);
        responseListener.listenToServer();
        maxNumOfPassengers = responseListener.getMaxNumOfPassengers();
    }

    public void popup(String airlineName, String passengerDetails1, int numOfPassenger1, int maxPassengers1) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(240, 150);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("<html><b>" + airlineName + ". " + "</b>" + numOfPassenger1 + ":" +
                maxPassengers1 + "</html>");
        title.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        jPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton exit = new JButton("Exit");
        buttonPanel.add(exit);
        jPanel.add(buttonPanel, BorderLayout.SOUTH);

        JTextPane textPane = new JTextPane();
        textPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setText(passengerDetails1);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textPane);
        scrollPane.setVerticalScrollBar(new JScrollBar());

        jPanel.add(scrollPane, BorderLayout.CENTER);
        jDialog.add(jPanel);
        jDialog.setFocusable(true);
        jDialog.requestFocus();
        jDialog.setVisible(true);
        exit.addActionListener(e -> jDialog.dispose());
        jDialog.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    jDialog.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {


            }
        });

    }


    public void sendToServer(String string) {
        try {
            request.writeObject(string);
            request.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(Passenger passenger3) {
        try {
            request.writeObject(passenger3);
            request.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }
}

/**
 *
 * Response Listener
 *
 * @author Alan, Manan
 * @version 11-22-19
 *
 */
class ResponseListener {

    private boolean airlineConfirmation;
    private BoardingPass boardingPass;
    private Passenger[] passengers;
    private String message;
    private Socket socket;
    private ObjectInputStream reader;
    private int numOfPassengers;
    private int maxNumOfPassengers;


    public ResponseListener(Socket socket) {
        this.socket = socket;
        this.airlineConfirmation = false;
        this.boardingPass = null;
        this.passengers = null;
        this.message = null;
        this.numOfPassengers = 0;
        this.maxNumOfPassengers = 0;

        try {
            this.reader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public int getMaxNumOfPassengers() {
        return maxNumOfPassengers;
    }

    public void setMaxNumOfPassengers(int maxNumOfPassengers) {
        this.maxNumOfPassengers = maxNumOfPassengers;
    }

    public void listenToServer() {
        Object input;

        try {
            input = reader.readObject();
            if (input instanceof String) {
                if (((String) input).contains("Airline")) {
                    message = (String) input;
                } else if (((String) input).contains("Confirm")) {
                    airlineConfirmation = true;
                }
            } else if (input instanceof Passenger[]) {
                passengers = (Passenger[]) input;
            } else if (input instanceof BoardingPass) {
                boardingPass = (BoardingPass) input;
            } else if (input instanceof Integer) {
                maxNumOfPassengers = (int) input;
                numOfPassengers = (int) input;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isAirlineConfirmation() {
        return airlineConfirmation;
    }

    public void setAirlineConfirmation(boolean airlineConfirmation) {
        this.airlineConfirmation = airlineConfirmation;
    }

    public BoardingPass getBoardingPass() {
        return boardingPass;
    }

    public void setBoardingPass(BoardingPass boardingPass) {
        this.boardingPass = boardingPass;
    }

    public String getPassengers() {
        String split = "";
        String allPassengers = "";
        for (Passenger passenger : passengers) {
            allPassengers += split + passenger.displayDetails();
            split = "\n";
        }
        return allPassengers;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
