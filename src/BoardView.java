/**
 * Created by AaronR on 3/27/17.
 * for Dr. Han
 *  Aaron Wrote this whole Class
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

/**
 * The View for the Game
 */
public class BoardView{
    // Delegates
     ViewController vc;

    private JFrame frame;
    private JButton newGame;
    private JButton resetGame;
    private JButton exitButton;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JLabel nameLabel1;
    private JLabel nameLabel2;
    private JLabel player1Wins;
    private JLabel player2Wins;
    private JLabel player1Losses;
    private JLabel player2Losses;
    private JLabel winsLabel1;
    private JLabel lossesLabel1;
    private JLabel winsLabel2;
    private JLabel lossesLabel2;
    private JLabel statusLabel;
    // Collection of all the tic tac toe tiles
    private List<JButton> spaces;
    String p1NameText;
    String p2NameText;

    /**
     * Constructor
     * @param delegate The Controller of the view
     */
     public BoardView(ViewController delegate){
         this.vc = delegate;
        newGame = new JButton("New Game");
        newGame.addActionListener(new NewGameListener());
        resetGame = new JButton("Reset");
        resetGame.addActionListener(new ResetGameListener());
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new CloseListener());
        nameLabel1 = new JLabel("Name:");
        nameLabel2 = new JLabel("Name:");
        player1NameField = new JTextField("Player 1");
        player2NameField = new JTextField("Player 2");
        winsLabel1 = new JLabel("Wins:");
        lossesLabel1 = new JLabel("Losses:");
        winsLabel2 = new JLabel("Wins:");
        lossesLabel2 = new JLabel("Losses:");
        player1Wins = new JLabel("0");
        player1Losses = new JLabel("0");
        player2Wins = new JLabel("0");
        player2Losses = new JLabel("0");
        statusLabel = new JLabel("Welcome To Tic-Tac-Toe");
        spaces = new ArrayList();

        // creates 9 space buttons and adds creates and adds an
        // action Listener with a unique button ID to it.
        //seems to want an integer so I'll have to use this values, I'll jst make a translation table.
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton();
            button.addActionListener(new SpacePressedListener(i));
            button.setEnabled(false);
            spaces.add(button);
        }

        frame = new JFrame("Tic-Tac-Toe");
        frame.setLocation(300,100);
        frame.setSize(500,500);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        designLayout();
        frame.setVisible(true);
    }
    // helper to setup Layouts
    private void designLayout(){

        Container scoreBoards = new JPanel();
        layoutScoreBoards(scoreBoards);

        Container gameField = new JPanel();
        layoutGameField(gameField);

        Container systemButtons = new JPanel();
        layoutSystemButtons(systemButtons);

        frame.add(scoreBoards, BorderLayout.NORTH);
        frame.add(gameField, BorderLayout.CENTER);
        frame.add(systemButtons, BorderLayout.SOUTH);
    }
    // sets up score boards
    private void layoutScoreBoards(Container scoreBoards){
        JPanel p1Panel = new JPanel();
        p1Panel.setLayout(new GridLayout(3,2));
        p1Panel.add(nameLabel1);
        p1Panel.add(player1NameField);
        p1Panel.add(winsLabel1);
        p1Panel.add(player1Wins);
        p1Panel.add(lossesLabel1);
        p1Panel.add(player1Losses);
        p1Panel.setBorder(BorderFactory.createTitledBorder("Player1(X):"));
        p1Panel.setPreferredSize(new Dimension(160,90));


        JPanel p2Panel = new JPanel();
        p2Panel.setLayout(new GridLayout(3,2));
        p2Panel.add(nameLabel2);
        p2Panel.add(player2NameField);
        p2Panel.add(winsLabel2);
        p2Panel.add(player2Wins);
        p2Panel.add(lossesLabel2);
        p2Panel.add(player2Losses);
        p2Panel.setPreferredSize(new Dimension(160,90));


        p2Panel.setBorder(BorderFactory.createTitledBorder("Player2(0):"));

        scoreBoards.add(p1Panel);
        scoreBoards.add(p2Panel);
        //scoreBoards.setPreferredSize(new Dimension(320,120));
    }
    // sets up bottom buttons new game reset exit
    private void layoutSystemButtons(Container bottom){
        bottom.setLayout(new BoxLayout(bottom,BoxLayout.Y_AXIS));

        JPanel sysButtons = new JPanel();
        sysButtons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sysButtons.add(newGame);
        sysButtons.add(resetGame);
        sysButtons.add(exitButton);
        bottom.add(sysButtons);
        bottom.add(statusLabel);
    }
    // sets up playing field
    private void layoutGameField(Container gameField){

        gameField.setLayout(new GridLayout(3,3));

        for (int i = 0; i < spaces.size(); i++) {
            gameField.add(spaces.get(i));
        }

    }

    // EXIT BUTTON
    private class CloseListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //DO SOMETHING
            System.exit(0);
        }
    }
    // NEW GAME BUTTON ACTION
    private class NewGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            //TODO: Initiate new Game in the controller
            if (player1NameField.getText().trim().equals("") || player2NameField.getText().trim().equals("")) {
                JOptionPane.showOptionDialog(frame, "Illegal player name(s).", "Error", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, null, null);
            }
            p1NameText = player1NameField.getText();
            p2NameText = player2NameField.getText();
            vc.newGame();
            for (int i = 0; i < 9; i++) {
                spaces.get(i).setText("");
            }
            statusLabel.setText(p1NameText+"'s Turn");
            gameButtonsEnabled(true);

        }
    }
    // RESET GAME ACTION
    private class ResetGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //DO SOMETHING
            //TODO: Reset Game in controller
            int res = JOptionPane.showOptionDialog(frame, "This will end the game and set the win/losses stats to 0. Are you sure?", "Are You Sure?", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (res == JOptionPane.YES_OPTION) {
                System.out.println("Players wish to reset game");
                vc.reset();
                gameButtonsEnabled(false);
                for (int i = 0; i < 9; i++) {
                    spaces.get(i).setText("");
                }
            }
        }
    }
    // TILE Pressed Button
    private class SpacePressedListener implements ActionListener {

        int buttonID;

        public SpacePressedListener(int buttonNumber){
            this.buttonID = buttonNumber;
        }
        // This is the function that gets called
        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Game Button " + buttonID + " was Pressed");
            //passing value of button pressed to makeMove function
            String check = vc.makeMove(buttonID);
            switch (check){
                case "yes":
                    break;
                case "no":
                    statusLabel.setText("Can't Go there, Try again");
                    break;
                case "draw":
                    statusLabel.setText("There is a draw");
                    gameButtonsEnabled(false);
                    break;
                case "PLAYER1":
                    statusLabel.setText( p1NameText+" WINS!");
                    gameButtonsEnabled(false);
                    break;
                case "PLAYER2":
                    statusLabel.setText( p2NameText+" WINS!");
                    gameButtonsEnabled(false);
                    break;
                default: break;
            }

        }
    }

    // Gets called by the controller

    /**
     * Updates the wins labels
     * @param p1wins
     * @param p2wins
     */
    public void setWins(int p1wins, int p2wins){
        player1Wins.setText(String.valueOf(p1wins));

        player2Wins.setText(String.valueOf(p2wins));

    }

    /**
     * updates the looses counter
     * @param p1Val
     * @param p2Val
     */
    public void setLooses(int p1Val, int p2Val){

        player1Losses.setText(String.valueOf(p1Val));

        player2Losses.setText(String.valueOf(p2Val));

    }

    /**
     * Receives message from VC saying it is ok to mark space
     * @param space To be marked
     * @param p1Turn Who made the mark. Used to put an x or a o
     */
    public void markTile(int space, boolean p1Turn){
        JButton spaceButton = spaces.get(space-1);
        if (p1Turn){
            spaceButton.setText("X");
            statusLabel.setText(p2NameText+"'s Turn");
        }
        else{
            spaceButton.setText("O");
            statusLabel.setText(p1NameText+"'s Turn");
        }
    }
    // helper method used to enable and disable the board
    private void gameButtonsEnabled(boolean status){
        for(JButton b:spaces){
            b.setEnabled(status);
        }
    }

}
