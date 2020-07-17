/**
 * RangedEnemy.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a Rajnged Enemy object, which is a subclass of Enemy. A ranged enemy is an enemy that is long ranged with relatively low health and shoots projectiles.
 */

import java.util.ArrayList;
class RangedEnemy extends Enemy {
    private int projectileSpeed;
    
    /**
     * RangedEnemy
     * This constructor creates a ranged enemy object that has certain attributes like health, damage, speed, position, size, range, cooldown, and projectile speed.
     * @param 9 integers representing the specifications of the enemy created and a double representing the enemy's attack cooldown.
     */
    RangedEnemy (int health, int damage, int speed, int xPos, int yPos, int height, int width, int range, double cooldown, int projectileSpeed) {
        super (health, damage, speed, xPos, yPos, height, width, range, cooldown, false);
        this.projectileSpeed = projectileSpeed;
    }
    
    /**
     * attack
     * This method causes the ranged enemy to shoot a projectile in the direction of the player if it is in range.
     * @param A player who is the target of the attack, and an arraylist of type Projectile representing all the projectiles in the room.
     */
    public void attack (Player player, ArrayList<Projectile> projectiles) {
        int xDiff = player.getXPos() -this.getXPos();
        int yDiff = player.getYPos()-this.getYPos();
        double distance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
        
        //Used similar triangles to find xSpeed and ySpeed
        double ratio = distance / projectileSpeed;
        int xSpeed = (int)(xDiff / ratio);
        int ySpeed = (int)(yDiff / ratio);
        if(distance <= this.getRange()) {
            projectiles.add(new Projectile (this.getDamage(), this.getXPos()+this.getWidth()/3, this.getYPos()+this.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,this.getRange(),"enemy",false));
        }
    }
    
    /**
     * findPlayer
     * This method causes the ranged enemy to travel in the direction of the player if it is too far away and backpedal if it is too close.
     * @param A player representing the enemy's target.
     */
    public void findPlayer (Player player) {
        int xDiff = this.getXPos() - player.getXPos();
        int yDiff = this.getYPos() - player.getYPos();
        double distance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
        double currentTime = System.nanoTime()/1000000000.0;
        double elapsedTime = currentTime - this.getMoveTime();
        this.setCanAttack(true);
        
        //4 possible directions to move
        if(Math.abs(xDiff) > Math.abs(yDiff)) {
            if(xDiff > 0) {
                
                //If player is outside the enemy's range, it will move towards the player
                //If the player is within 1/2 of the enemy's range, it will move away from the player.
                if(distance >= this.getRange()) {
                    this.moveLeft();
                    this.setCanAttack(false);
                } else if(distance <= this.getRange()/2) {
                    this.moveRight();
                    this.setCanAttack(false);
                }
                this.setDirection("left");
            } else{
                if(distance >= this.getRange()) {
                    this.moveRight();
                    this.setCanAttack(false);
                } else if(distance <= this.getRange()/2){
                    this.moveLeft();
                    this.setCanAttack(false);
                }
                this.setDirection("right");
            }
        } else {
            if(yDiff > 0) {
                if(distance>= this.getRange()) {
                    this.moveUp();
                    this.setCanAttack(false);
                } else if (yDiff <= this.getRange()/2){
                    this.moveDown();
                    this.setCanAttack(false);
                }
                this.setDirection("up");
            } else {
                if(distance >= this.getRange()) {
                    this.moveDown();
                    this.setCanAttack(false);
                } else if(distance <= this.getRange()/2) {
                    this.moveUp();
                    this.setCanAttack(false);
                }
                this.setDirection("down");
            }
        }
    }
    
    /**
     * getProjectileSpeed
     * This method is a getter for the private variable projectileSpeed.
     * @return An integer representing the ranged enemy's projectile speed.
     */
    public int getProjectileSpeed() {
        return this.projectileSpeed;
    }
    
    /**
     * setProjectileSpeed
     * This method is a setter for the private variable projectileSpeed.
     * @param An integer representing the ranged enemy's updated projectile speed.
     */
    public void setProjectileSpeed(int speed) {
        this.projectileSpeed = speed;
    }
    
}