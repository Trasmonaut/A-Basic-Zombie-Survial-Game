# A Basic Zombie Survial game made for Intro to Game Programming

## Game Description
A simple game about surviving waves of Zombies. Player has been given a sword and dropped into a hoard of 3 types of Zombies with one objective, reach as far as you can. Health Squares will be dropped randomly, but act fast, theyre quite rare.

## How to play/Score points

Use WASD or the Arrow Keys to control the player movement. 
Use the mouse to control the direction of the player, as indicated by a red line. 
Left click with the mouse to swing your sword in the direction the player is facing, as indicated by a red slash.

Interact with Health Sqaures by running into them to regain some health (Indicated in Game Panel)
Careful! Running into Zombies causes you to take damage.

KIll all Zombies to move on to a New Round. Rounds must be manually started to give players the oppportunity for a short break between rounds.

Points are scored for every round cleared (Killing all zombies in that round)

## How to run
Step 1 : Git Clone repository
Step 2 : In environment, run "Game Application.java" 

## Knowns Bugs:
### Concurrent Modification Exception Thread - 
Killing multiple zombies with one hit has a chance of throwing this exception. The soultion to this bug is known, but for the scope of the assiginment related project, this bug would not be fixed. In the current state of the game, each new entity (Player, zombie, health object) is a unqiue thread. As such, keeping track of each one is done individually, by adding them to a list, and runnning the same command over all of them. This is a horribly inefficent way of doing things, but for the scope of the assignment was what was necessary

### Fix
Convert game to run on a single thread, keepign track of entities in a loop.
