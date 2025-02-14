import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    PlayerEntity player;
    Health healthUp;
    private int round;
    private int totalZombies;
    private int aliveZombies, spawnedZombies;
    private int points = 0;
    public boolean roundStarted = false;
    private int smallZCount, runnerZCount, giantZCount, spawnDelay = 300;
    public List<Zombie> allZombies = new ArrayList<>();

    public GamePanel() {
        player = null;
    }

    public void createGameEntities() {
        if (player == null) {
            player = new PlayerEntity(this, 300, 300);
            GameWindow.updateHealthDisplay(player.health);
            spawnHealth();
            points = 0;
            round = 0;
        } else if (aliveZombies == 0) {
            System.out.println("Restarting Game...");
            resetGame();
        }
    }

    private void resetGame() {
        player.erase();
        player.eraseMarker();
        player.health = 25;
        roundStarted = false;
        GameWindow.updateHealthDisplay(player.health);
        spawnHealth();
        points = 0;
        round = 0;
        player = new PlayerEntity(this, 300, 300);
    }

    public void drawGameEntities() {
        if (player != null) {
            player.draw();
        }
    }

    public void spawnHealth() {
        if (healthUp == null) {
            healthUp = new Health(this, player, this);
            healthUp.start();
        }
    }

    public void updateGameEntities(int direction) {
        if (player != null) {
            player.erase();
            player.eraseMarker();
            player.move(direction);
            GameWindow.updateHealthDisplay(player.health);
            player.draw();

            // Handle health pickup
            if (player != null && healthUp != null && healthUp.collectable
                    && player.getBoundingRectangle().intersects(healthUp.getBoundingRectangle())) {
                player.heal(10);
                healthUp.erase();
                healthUp.collectable = false;
            }

           
            }

            // End game if player is dead
            if (player.health < 1) {
                killAllZombies();
            }
        
    }

    public void updateMousePointer(int mouseX, int mouseY) {
        player.marker(mouseX, mouseY);
        player.draw();
    }

    public void playerAttack(int mouseX, int mouseY) {
        for (Zombie zombie : allZombies) {
            if (zombie.collidesWithAttack()) {
                break;
            }
        }
    }

    private void spawnSmallZombies() {
        for (int i = 0; i < smallZCount; i++) {
            Zombie smallZombie = new SmallZombie(this, player);
            allZombies.add(smallZombie);
            smallZombie.start();
            aliveZombies++;
        }
    }

    private void spawnRunnerZombies() {
        for (int i = 0; i < runnerZCount; i++) {
            Zombie runnerZombie = new RunnerZombie(this, player);
            allZombies.add(runnerZombie);
            runnerZombie.start();
            aliveZombies++;
        }
    }

    private void spawnGiantZombies() {
        for (int i = 0; i < giantZCount; i++) {
            Zombie giantZombie = new GiantZombie(this, player);
            allZombies.add(giantZombie);
            giantZombie.start();
            aliveZombies++;
        }
    }

    public void startNewRound() {
        if (player.health > 0 && aliveZombies == 0 && round <= 10 && !roundStarted) {
            round++;
            First10Rounds(round);
            GameWindow.updateRoundDisplay(this.round);
            System.out.println("Round " + round + " spawned.");

            spawnSmallZombies();
            spawnRunnerZombies();
            spawnGiantZombies();

            roundStarted = true;
            GameWindow.updatePointsDisplay(points);
        } else if (aliveZombies > 0) {
            System.out.println("Round in Progress. Kill all Alive Zombies First");
        } else if (round > 10) {
            System.out.println("Congratulations! You've beat the game! Endless mode coming in Future Update");
        } else if (player.health < 1) {
            System.out.println("Cannot Spawn because Player died");
        } else {
            System.out.println("Complete current Round First");
        }
    }

    public void addZombie(Zombie zombie) {
        if (zombie != null) {
            allZombies.add(zombie);
        }
    }

    public void removeDeadZombie(Zombie zombie) {
        if (allZombies.remove(zombie)) {
            aliveZombies--;
            System.out.println("Zombie removed. Alive zombies: " + aliveZombies);
        }

        if (roundStarted && aliveZombies == 0 && player.health > 0) {
            points++;
            roundStarted = false;
            GameWindow.updatePointsDisplay(this.points);
        }
    }

    public void killAllZombies() {
        Iterator<Zombie> iterator = allZombies.iterator();
        while (iterator.hasNext()) {
            Zombie zombie = iterator.next();
            if (zombie instanceof SmallZombie) {
                ((SmallZombie) zombie).isRunning = false;
            } else if (zombie instanceof RunnerZombie) {
                ((RunnerZombie) zombie).isRunning = false;
            } else if (zombie instanceof GiantZombie) {
                ((GiantZombie) zombie).isRunning = false;
            }
            zombie.erase();
            zombie.checkDeath();
            iterator.remove();
        }
        aliveZombies = 0;
    }

    public void First10Rounds(int roundNum) {
        switch (roundNum) {
            case 1:
                smallZCount = 5;
                giantZCount = 0;
                runnerZCount = 1;
                break;
            case 2:
                smallZCount = 15;
                giantZCount = 0;
                runnerZCount = 3;
                break;
            case 3:
                smallZCount = 15;
                giantZCount = 0;
                runnerZCount = 10;
                break;
            case 4:
                smallZCount = 5;
                giantZCount = 1;
                runnerZCount = 5;
                break;
            case 5:
                smallZCount = 0;
                giantZCount = 5;
                runnerZCount = 10;
                break;
            case 6:
                smallZCount = 15;
                giantZCount = 3;
                runnerZCount = 15;
                break;
            case 7:
                smallZCount = 35;
                giantZCount = 1;
                runnerZCount = 15;
                break;
            case 8:
                smallZCount = 50;
                giantZCount = 0;
                runnerZCount = 0;
                break;
            case 9:
                smallZCount = 0;
                giantZCount = 0;
                runnerZCount = 30;
                break;
            case 10:
                smallZCount = 15;
                giantZCount = 10;
                runnerZCount = 15;
                break;
        }
        totalZombies = smallZCount + giantZCount + runnerZCount;
    }
}