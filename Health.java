import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Health extends Thread {
    private int x;
    private int y, height, width, spawnrate = 9999;
    private Color color = Color.decode("#f5e63d");
    public boolean collectable;

    private final JPanel panel;
    public boolean isRunning;
    private final Random random;

    private final GamePanel gamePanel;

    public Health(JPanel p, PlayerEntity player, GamePanel gamePanel) {
        this.panel = p;
        this.gamePanel = gamePanel;
        height = 15;
        width = 10;
        random = new Random();
    }

    public void draw() {
        if (gamePanel.roundStarted) {
            Graphics g = panel.getGraphics();
            Graphics2D g2 = (Graphics2D) g;

            Rectangle2D.Double health = new Rectangle2D.Double(x, y, width, height);
            g2.setColor(color);
            g2.fill(health);
            g2.setColor(Color.BLACK);
            g2.draw(health);
            g.dispose();
        }
    }

    public void move() {
        erase();
        spawnRandomLocation();
        draw();
    }

    public void erase() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(panel.getBackground());
        g2.fill(new Rectangle2D.Double(x, y, height + 10, width + 10));
        g.dispose();
    }

    public void spawnRandomLocation() {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        x = random.nextInt(panelWidth - width);
        y = random.nextInt(panelHeight - height);
    }

    @Override
    public void run() {
        isRunning = true;
        collectable = true;
        while (isRunning) {
            try {
                Thread.sleep(spawnrate);
                if (!collectable && gamePanel.roundStarted) {
                    spawnRandomLocation();
                    draw();
                    collectable = true;
                } else {
                    move();
                }
            } catch (Exception e) {
                System.out.println("Some error related to Spawning Health and Threads. (Change before production)");
            }
        }
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}