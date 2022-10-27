package ui;

import model.Account;
import model.Contest;
import model.Lineup;
import model.Player;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.WindowListener;


// Representing a daily fantasy basketball app
public class DailyFantasyBasketballApp extends JPanel implements WindowListener {
    private static final String JSON_STORE = "./data/Lineup.json";
    private Lineup myLineup;
    private Account myAccount;
    private Contest cheap;
    private Contest expensive;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel panel1;
    private JButton saveLineupButton;
    private JButton loadLineupButton;
    private JButton enterContestButton;
    private JButton viewLineupButton;
    private JButton createLineupButton;
    private JPanel panel2;
    private JTextArea textArea1;
    private JTextArea textArea2;

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    //EFEFCTS: Prints the event log to the console when the window is closed
    public void windowClosing(WindowEvent e) {
        PrintEventLog.printLog(EventLog.getInstance());
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    // Represents the action listener for the save lineup button
    class SaveLineUpButtonActionListener implements ActionListener {
        @Override
        //MODIFIES: this
        //EFFECTS: saves the current lineup
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(myLineup);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved your lineup to " + JSON_STORE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file: " + JSON_STORE);
            }

        }
    }

    // Represents the action listener for the view lineup button
    class ViewLineupButtonActionListener implements ActionListener {
        @Override
        //MODIFIES: this
        //EFFECTS: Prints out the lineup in the text area
        public void actionPerformed(ActionEvent e) {
            textArea1.setText("Player , Team, Position, Salary \n");
            for (int i = 0; i < 10; i++) {
                textArea1.append("" + myLineup.getPlayer(i).getName()
                        + " " + myLineup.getPlayer(i).getTeam() + " "
                        + myLineup.getPlayer(i).getPosition() + " "
                        + Integer.toString(myLineup.getPlayer(i).getSalary()) + "\n");
            }
        }
    }

    // Represents the action listener for the create lineup button
    class CreateLineupButtonActionListener implements ActionListener {
        @Override
        // MODIFIES: this
        // EFFECTS: Prompts the user to add a player to the lineup
        public void actionPerformed(ActionEvent e) {
            String playerName = JOptionPane.showInputDialog("Enter players name");
            String playerTeam = JOptionPane.showInputDialog("Enter players team (3 letter abbreviation)");
            String playerPosition = JOptionPane.showInputDialog("Enter players position");
            int playerSalary = Integer.parseInt(JOptionPane.showInputDialog("Enter players salary"));
            myLineup.addPlayer(new Player(playerName, playerTeam, playerPosition, playerSalary));
            JOptionPane.showMessageDialog(null,
                    "" + playerName + " has been added to the lineup");
        }
    }

    // Represents the action listener for the enter contest button
    class EnterContestButtonActionListener implements ActionListener {
        @Override
        // MODIFIES: this
        // EFFECTS: Prompts the user to enter a contest
        public void actionPerformed(ActionEvent e) {
            textArea2.setText("Account Name" + "\n");
            String contest = JOptionPane.showInputDialog("Enter c for cheap, e for expensive");
            if (Objects.equals(contest, "c")) {
                cheap.addLineup(myLineup);
                JOptionPane.showMessageDialog(null,
                        "Lineup has been entered into cheap contest");
            } else if (Objects.equals(contest, "e")) {
                expensive.addLineup(myLineup);
                JOptionPane.showMessageDialog(null,
                        "Lineup has been entered into expensive contest");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid Entry");
            }
            textArea2.append(myLineup.getAccount().getUsername());

        }
    }

    // Represents the action listener for the load lineup button
    class LoadLineupButtonActionListener implements ActionListener {
        @Override
        //MODIFIES: this
        //EFFECTS: loads the saved lineup
        public void actionPerformed(ActionEvent e) {
            try {
                myLineup = jsonReader.read();
                JOptionPane.showMessageDialog(null,
                        "Loaded your lineup from " + JSON_STORE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // A daily fantasy basketball app
    public DailyFantasyBasketballApp() {
        myAccount = new Account(150, "fantasyking");
        myLineup = new Lineup(myAccount);
        cheap = new Contest(2, 10, 20);
        expensive = new Contest(2, 100, 200);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        panel1 = new JPanel();
        panel2 = new JPanel();
        saveLineupButton = new JButton("Save Lineup");
        loadLineupButton = new JButton("Load Lineup");
        enterContestButton = new JButton("Enter Contest");
        viewLineupButton = new JButton("View Lineup");
        createLineupButton = new JButton("Add Player");
        textArea1 = new JTextArea();
        textArea2 = new JTextArea();


        viewLineupButton.addActionListener(new ViewLineupButtonActionListener());
        createLineupButton.addActionListener(new CreateLineupButtonActionListener());
        enterContestButton.addActionListener(new EnterContestButtonActionListener());
        loadLineupButton.addActionListener(new LoadLineupButtonActionListener());
        saveLineupButton.addActionListener(new SaveLineUpButtonActionListener());

        initialize();


    }

    //MODIFIES: this
    //EFFECTS: initializes the panels, buttons, etc.
    public void initialize() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./data/dj.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        add(picLabel);


        panel1.setLayout(new GridLayout(1, 5));
        panel1.add(createLineupButton);
        panel1.add(viewLineupButton);
        panel1.add(enterContestButton);
        panel1.add(saveLineupButton);
        panel1.add(loadLineupButton);
        panel2.add(textArea1);
        panel2.add(textArea2);
        panel2.add(picLabel);
        panel1.add(panel2);

        add(panel1);
    }

    // Respresents the actions taken to print the event log
    static class PrintEventLog {
        public static void printLog(EventLog el) {
            for (Event next : el) {
                System.out.println(next);
            }
        }
    }


    // Initiates the daily fantasy basketball app.
    public static void main(String[] args) {
        DailyFantasyBasketballApp dfsa = new DailyFantasyBasketballApp();
        dfsa.createAndStartGUI();
    }

    //MODIFIES: this
    //EFFECTS: Creates the gui and basic settings
    public void createAndStartGUI() {
        JFrame frame = new JFrame("DailyFantasyBasketballApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DailyFantasyBasketballApp contentPane = new DailyFantasyBasketballApp();
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        contentPane.setLayout(new GridLayout(1, 4));
        frame.addWindowListener(this);
    }

}

