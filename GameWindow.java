import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    private JLabel statusBarL, keyL, mouseL, roundL;
    private JTextField statusBarTF ;
    private static JTextField roundTF;
    private static JTextField mouseTF;
    private static JTextField keyTF;
    private JButton startButton, pauseButton, LeaderboardButton, exitButton;
    private Container c;
    private JPanel mainPanel;
    private GamePanel gamePanel;
    private boolean up, down, left, right;

    public GameWindow() {
        setTitle("A Basic Zombie Survival Game 1.0.3.2");
        setSize(800, 800);

        statusBarL = new JLabel("Application Status: ");
        keyL = new JLabel("Player Health: ");
        mouseL = new JLabel("Current Round: ");
        roundL = new JLabel("Points: "); 
       
        statusBarTF = new JTextField(25);
        keyTF = new JTextField(25);
        mouseTF = new JTextField(25);
        roundTF = new JTextField(25);

        statusBarTF.setEditable(false);
        keyTF.setEditable(false);
        mouseTF.setEditable(false);
        roundTF.setEditable(false);

        statusBarTF.setBackground(Color.CYAN);
        keyTF.setBackground(Color.YELLOW);
        mouseTF.setBackground(Color.GREEN);
        roundTF.setBackground(Color.PINK);


        startButton = new JButton("Start New Game");
        pauseButton = new JButton("Start New Round");
        LeaderboardButton = new JButton("Force Focus on GamePanel");
        exitButton = new JButton("Exit");

        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        LeaderboardButton.addActionListener(this);
        exitButton.addActionListener(this);

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(600, 600));
        gamePanel.setBackground(Color.decode("#aad9a9"));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 2));
        infoPanel.setBackground(Color.ORANGE);

        infoPanel.add(statusBarL);
        infoPanel.add(statusBarTF);
        infoPanel.add(keyL);
        infoPanel.add(keyTF);
        infoPanel.add(mouseL);
        infoPanel.add(mouseTF);
        infoPanel.add(roundL);
        infoPanel.add(roundTF);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(LeaderboardButton);
        buttonPanel.add(exitButton);

        mainPanel.add(infoPanel);
        mainPanel.add(gamePanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(Color.decode("#202b20"));

        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        mainPanel.addKeyListener(this);

        c = getContentPane();
        c.add(mainPanel);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        statusBarTF.setText("Application started.");
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals(startButton.getText())) {
            System.out.println("Starting Game...");
            gamePanel.createGameEntities();
            gamePanel.drawGameEntities();
            gamePanel.startNewRound();
            mainPanel.requestFocus();
        }

        if (command.equals(pauseButton.getText())){
            System.out.println("Trying to Start new Round...");
            gamePanel.startNewRound();
        }

        if (command.equals(LeaderboardButton.getText())){
            System.out.println("Force Focus on GamePanel...");
            //gamePanel.killAllZombies();
            mainPanel.requestFocus();
   
        }

        if (command.equals(exitButton.getText())){
            System.exit(0);
            
        }
        mainPanel.requestFocus();
    }

    public static void updateHealthDisplay( int i ) {
        if ( i > 1 ){
            keyTF.setText("" + String.valueOf(i));
        }else{
            keyTF.setText("Player Killed");
        }
        
    }

    public static void updateRoundDisplay( int i ) {

        if ( i>10 ){
            mouseTF.setText(" Congratulations! You've beat the game! Endless mode comming in Future Update");
        } else {
            mouseTF.setText("" + String.valueOf(i));
        }
    }

    public static void updatePointsDisplay( int i ) {
        roundTF.setText("" + String.valueOf(i));
    
    
}

    

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) right = true;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) left = true;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) up = true;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) down = true;

        updateMovement();
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) right = false;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) left = false;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) up = false;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) down = false;
    }

    private void updateMovement() {
        if (up && right) gamePanel.updateGameEntities(5); // Move diagonally up-right
        else if (up && left) gamePanel.updateGameEntities(6); // Move diagonally up-left
        else if (down && right) gamePanel.updateGameEntities(7); // Move diagonally down-right
        else if (down && left) gamePanel.updateGameEntities(8); // Move diagonally down-left
        else if (right) gamePanel.updateGameEntities(2);
        else if (left) gamePanel.updateGameEntities(1);
        else if (up) gamePanel.updateGameEntities(3);
        else if (down) gamePanel.updateGameEntities(4);
        gamePanel.drawGameEntities();
    }

    public void keyTyped(KeyEvent e) {}


    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        gamePanel.playerAttack(x,y);
    
    }
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        gamePanel.updateMousePointer(mouseX, mouseY);
    }
    public void mouseDragged(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args) {
        new GameWindow();
    }
}
