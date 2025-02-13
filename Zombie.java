import java.awt.geom.Rectangle2D;

public abstract class Zombie extends Thread{
    public abstract void draw();
    public abstract void erase();
    public abstract void track(int playerX,int playerY);
    public abstract void move(int targetX, int targetY);
    public  abstract boolean  collidesWithAttack();
    public abstract void hurtPlayer();
    public abstract void spawnRandomLocation();
    public abstract void checkDeath();
    public abstract Rectangle2D.Double getBoundingRectangle();

}
