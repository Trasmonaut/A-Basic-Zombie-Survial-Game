import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Health extends Thread {
    private int x;
    private int y ,height, width, spawnrate = 99999;
    private Color color = Color.decode("#f5e63d");
    public boolean collectable;


    private final JPanel panel;
    public boolean isRunning;
    private final Random random;

    public Health(JPanel p , PlayerEntity player){
        this.panel = p;
        height = 15;
        width = 10;
        random = new Random();

    }

    public void draw(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D.Double Health = new Rectangle2D.Double(x, y,width , height);
        g2.setColor(color);
        g2.fill(Health);
        g2.setColor(Color.BLACK);
        g2.draw(Health);
        g.dispose();

    }

    public void move(){
        erase();
        spawnRandomLocation();
        draw();
    }
    
    public void erase(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(panel.getBackground());
        g2.fill(new Rectangle2D.Double(x , y , height + 10, width + 10));
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
        collectable=true;
        move();
        while (isRunning){
            try{
                Thread.sleep(spawnrate);
                if(!collectable){
                    spawnRandomLocation();
                    draw();
                    collectable=true;
                }else{
                    move();
                }
            }catch (Exception e) {
                System.out.println("Some error related to Spawning Health and Threads. (Change before production)");
        }
        
            
        }
    
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
     }

    
}
