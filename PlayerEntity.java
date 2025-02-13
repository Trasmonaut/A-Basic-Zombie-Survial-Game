import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class PlayerEntity {
    public int damage = 2;
    public int  health = 100;
    private JPanel panel;
    public int x;
    public int y;
    private int diameter = 20;
    private int stun = 10;
    private Color PlayerColor = Color.decode("#58d3ca");

    private int speed = 5;
  

    private Ellipse2D.Double player;

    private Color backgroundColour;
    private Dimension dimension;

    private int lastMouseX;
    private int lastMouseY;
    private double lastAngle; // Stores the angle for shooting direction

    public PlayerEntity(JPanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();

        backgroundColour = panel.getBackground();
        x = xPos;
        y = yPos;


    }
       

    public void draw() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        player = new Ellipse2D.Double(x, y, diameter, diameter);

        g2.setColor(PlayerColor);
        g2.fill(player);

        g2.setColor(Color.BLACK);
        g2.draw(player);

        g.dispose();
    }

    public void erase() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(backgroundColour);
        g2.fill(new Ellipse2D.Double(x-3, y-3, diameter + 5, diameter + 5));

        g.dispose();
    }

    public void marker(int mouseX, int mouseY) {
        eraseMarker();
        Graphics g = panel.getGraphics();
        Graphics2D g3 = (Graphics2D) g;

        double centerX = x + diameter / 2.0;
        double centerY = y + diameter / 2.0;

        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 5) {
            double ratio = 30 / distance;
            mouseX = (int) (centerX + dx * ratio);
            mouseY = (int) (centerY + dy * ratio);
        }

        g3.setColor(Color.RED);
        g3.drawLine((int) centerX, (int) centerY, mouseX, mouseY);

        lastMouseX = mouseX;
        lastMouseY = mouseY;

        // Calculate angle for shooting arc
        lastAngle =( Math.toDegrees(Math.atan2(dx, dy))-90);

        g.dispose();
    }

    public Rectangle2D.Double HitAreaRectangle() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
    
        double centerX = x + diameter / 2.0;
        double centerY = y + diameter / 2.0;
    
        int arcRadius = 30;
        int arcWidth = 50;
    
        double arcX = centerX - arcRadius;
        double arcY = centerY - arcRadius;
    
       
        Arc2D.Double arc = new Arc2D.Double(arcX, arcY, arcRadius * 2, arcRadius * 2, lastAngle - arcWidth / 2.0, arcWidth, Arc2D.OPEN);
    
   
        g2.setColor(Color.RED);
        g2.draw(arc);
    
        g.dispose();
    
        
        return new Rectangle2D.Double(arc.getBounds2D().getX(), arc.getBounds2D().getY(), arc.getBounds2D().getWidth(), arc.getBounds2D().getHeight());
    }
    


    public void eraseMarker() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(backgroundColour);
        g2.drawLine((int) (x + diameter / 2.0), (int) (y + diameter / 2.0), lastMouseX, lastMouseY);
        g.dispose();
    }


    public void heal(int amt) {
        health += amt;
        GameWindow.updateHealthDisplay(this.health);

        if( health < 1 ){
            player=null;

        }
    }

   
    public void move(int direction) {
        if (!panel.isVisible()) return;

        dimension = panel.getSize();

        switch (direction) {
            case 1: x -= speed; if (x < 0) x = 0; break; // Left
            case 2: x += speed; if (x + diameter > dimension.width) x = dimension.width - diameter; break; // Right
            case 3: y -= speed; if (y < 0) y = 0; break; // Up
            case 4: y += speed; if (y + diameter > dimension.height) y = dimension.height - diameter; break; // Down
            case 5: x += speed; if (x + diameter > dimension.width) x = dimension.width - diameter; y -= speed;if (y < 0) y = 0;break; // Up-right
            case 6: x -= speed; if (x < 0) x = 0; y -= speed;if (y < 0) y = 0; break; // Up-left
            case 7: x += speed; if (x + diameter > dimension.width) x = dimension.width - diameter; y += speed; if (y + diameter > dimension.height) y = dimension.height - diameter;break; // Down-right
            case 8: x -= speed;if (x < 0) x = 0;  y += speed;if (y + diameter > dimension.height) y = dimension.height - diameter; break; // Down-left
        }

        followMouse(lastMouseX, lastMouseY);
    }

    public void followMouse(int mouseX, int mouseY) {
        marker(mouseX, mouseY);
    }
 

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, diameter, diameter);
     }
  
    
}
