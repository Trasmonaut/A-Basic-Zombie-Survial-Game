import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class GiantZombie extends Zombie implements Runnable {
    // Zombie Stats
    private int health = 10;
    private final int damage = 10;
    private final int speed = 4;
   
    private final int diameter = 30;
    private final Color color = Color.decode("#051a03");
    private int movementcounter = 150;

    private int rmove;

    private JPanel panel;
    private int x;
    private int y;
    public boolean isRunning;
    private Random random;

    private PlayerEntity player;
    
    public GiantZombie(JPanel p, PlayerEntity player) {
        this.panel = p;
        this.random = new Random();
        spawnRandomLocation();
        this.player = player;
    }

    @Override
    public void draw() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        Ellipse2D.Double zombie = new Ellipse2D.Double(x, y, diameter, diameter);
        g2.setColor(color);
        g2.fill(zombie);
        g2.setColor(Color.BLACK);
        g2.draw(zombie);
        g.dispose();
    }

    @Override
    public void erase() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(panel.getBackground());
        g2.fill(new Ellipse2D.Double(x - 3, y - 3, diameter + 5, diameter + 5));
        g.dispose();
    }

    public void spawnRandomLocation() {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        x = random.nextInt(panelWidth - diameter);
        y = random.nextInt(panelHeight - diameter);
    }

    public void move(int targetX, int targetY) {
        erase();
        int dx = Integer.compare(targetX, x) * speed;
        int dy = Integer.compare(targetY, y) * speed;

        int newX = x + dx;
        int newY = y + dy;

        if (newX < 0) newX = 0;
        if (newX > panel.getWidth() - diameter) newX = panel.getWidth() - diameter;
        if (newY < 0) newY = 0;
        if (newY > panel.getHeight() - diameter) newY = panel.getHeight() - diameter;

        boolean collisionWithPlayer = collidesWithPlayer();
        boolean collisionWithZombie = collidesWithZombie(newX, newY);

        if (collisionWithPlayer) {
           
         
                hurtPlayer();
                draw();
            
        } else if (!collisionWithZombie) {
            x = newX;
            y = newY;
            draw();
            randomMovement();
        } else {
            draw();
        }
    }

    @Override
    public void track(int playerX, int playerY) {
        move(playerX, playerY);
    }

    public void randomMovement() {
        int targetX = x + (random.nextInt(3) - 1) * speed * 5;
        int targetY = y + (random.nextInt(3) - 1) * speed * 5;
        for (int i = 0; i < 5; i++) {
            move(targetX, targetY);
            try {
                Thread.currentThread().sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
public void run() {
    isRunning = true;
    spawnRandomLocation();
    draw();

    try {
        while (isRunning) {
            randomMovement();
            while (this.player != null && this.health > 0 && isRunning) {
                track(player.x, player.y);
                Thread.sleep(movementcounter);
                rmove = random.nextInt(50);
                if (rmove == 1) {
                    randomMovement();
                }

                if (player.health == 0) player = null;
            }
            isRunning = false;
        }
        checkDeath();
        erase();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
    @Override
    public boolean collidesWithAttack() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double hitRect = player.HitAreaRectangle();
        
        if (myRect.intersects(hitRect)) {
            try {
                health -= player.damage;
                System.out.println(this.health);
                
                if (health < 1) {
                    isRunning = false;
                }
                Thread.currentThread().sleep(600);
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, diameter, diameter);
    }
    
    public boolean collidesWithPlayer() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();
        
        return myRect.intersects(playerRect); 
    }

    public boolean collidesWithZombie(int newX, int newY) {
        Rectangle2D.Double myRect = new Rectangle2D.Double(newX, newY, diameter, diameter);
        for (Zombie zombie : ((GamePanel) panel).allZombies) {
            if (zombie != this && myRect.intersects(zombie.getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void hurtPlayer() {
        player.heal(-damage);
    }

    @Override
    public void checkDeath() {
        ((GamePanel) panel).removeDeadZombie(this); 
        Thread.currentThread().interrupt(); 
        isRunning = false;
    }
}