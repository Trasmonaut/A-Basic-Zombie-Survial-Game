import java.awt.geom.Rectangle2D;


public abstract class Zombie extends Thread{
    public abstract void draw();
    public abstract void erase();
    public abstract void spawnRandomLocation();
    public abstract void move(int targetX, int targetY);
    public abstract void track(int playerX,int playerY);
    public abstract void randomMovement();
    public abstract void run();
    public  abstract boolean  collidesWithAttack();
    public abstract Rectangle2D.Double getBoundingRectangle();
    public abstract boolean collidesWithPlayer();
    public abstract boolean collidesWithZombie(int newx, int newy);
    public abstract void hurtPlayer();
    public abstract void checkDeath();
   public abstract void freezeZombie();
}
