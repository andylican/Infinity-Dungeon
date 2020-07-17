/**
 * MeleeEnemy.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a Melee Enemy object, which is a subclass of Enemy. A melee enemy is an enemy that is short ranged with relatively high health.
 */


import java.awt.Rectangle;
class MeleeEnemy extends Enemy {
    private Rectangle attackBox;
    
    /**
     * MeleeEnemy
     * This constructor creates an melee enemy object that has certain attributes like health, damage, speed, position, size, range, and cooldown.
     * @param 8 integers representing the specifications of the enemy created and a double representing the enemy's attack cooldown.
     */
    MeleeEnemy (int health, int damage, int speed, int xPos, int yPos, int height, int width, int range, double cooldown) {
        super (health, damage, speed, xPos, yPos, height, width, range, cooldown, false);
    }
    
    /**
     * findPlayer
     * This method allows the enemy to choose a direction to travel in to get closer to the enemy and calls the movement methods.
     * @param A player representing the player that the enemy is trying to attack.
     */
    public void findPlayer(Player p) {
        int xDiff = this.getXPos()-p.getXPos();
        int yDiff = this.getYPos()-p.getYPos();
        double currentTime = System.nanoTime()/1000000000.0;
        double elapsedTime = currentTime - this.getMoveTime();
        this.setCanAttack(true);
        
        //Chooses which direction to go in
        if(Math.abs(xDiff) > Math.abs(yDiff)) {
            if(xDiff > 0) {
                
                //If the player is out of the enemy's attack range, move and do not attack
                if(xDiff-p.getWidth() > this.getRange()) {
                    this.moveLeft();
                    this.setCanAttack(false);
                }
                
                this.setDirection("left");
            } else{
                if((-1*xDiff)-p.getWidth() > this.getRange()) {
                    this.moveRight();
                    this.setCanAttack(false);
                }
                
                this.setDirection("right");
            }
        } else {
            if(yDiff > 0) {
                if(yDiff-p.getHeight() > this.getRange()) {
                    this.moveUp();
                    this.setCanAttack(false);
                }
                
                this.setDirection("up");
            } else {
                if((-1*yDiff)-p.getHeight() > this.getRange()) {
                    this.moveDown();
                    this.setCanAttack(false);
                }
                this.setDirection("down");
            }
        }
        
    }
    
    /**
     * attackLeft
     * This method causes the enemy to attack left and will damage the player if hit.
     * @param A player who the enemy is attacking.
     */
    public void attackLeft(Player p) {
        Rectangle playerBox = p.getHitbox();
        Rectangle myHitbox = this.getHitbox();
        this.attackBox = new Rectangle(((int)myHitbox.getX()-this.getRange()),((int)myHitbox.getY()),this.getRange(),((int)myHitbox.getHeight()));
        
        if(this.attackBox.intersects(playerBox)) {
            p.setHealth(p.getHealth()-this.getDamage());
        }
    } 
    
      /**
     * attackRight
     * This method causes the enemy to attack right and will damage the player if hit.
     * @param A player who the enemy is attacking.
     */
    public void attackRight(Player p) {
        Rectangle playerBox = p.getHitbox();
        Rectangle myHitbox = this.getHitbox();
        this.attackBox = new Rectangle(((int)(myHitbox.getX()+myHitbox.getWidth())),((int)myHitbox.getY()),this.getRange(),((int)myHitbox.getHeight()));
        if(this.attackBox.intersects(playerBox)) {
            p.setHealth(p.getHealth()-this.getDamage());
        }
    }
    
     /**
     * attackUp
     * This method causes the enemy to attack up and will damage the player if hit.
     * @param A player who the enemy is attacking.
     */
    public void attackUp(Player p) {
        Rectangle playerBox = p.getHitbox();
        Rectangle myHitbox = this.getHitbox();
        this.attackBox = new Rectangle(((int)(myHitbox.getX())),((int)myHitbox.getY()-this.getRange()),(int)(myHitbox.getWidth()),this.getRange());
        if(this.attackBox.intersects(playerBox)) {
            p.setHealth(p.getHealth()-this.getDamage());
        }
    }
    
    /**
     * attackDown
     * This method causes the enemy to attack down and will damage the player if hit.
     * @param A player who the enemy is attacking.
     */
     public void attackDown(Player p) {
        Rectangle playerBox = p.getHitbox();
        Rectangle myHitbox = this.getHitbox();
        this.attackBox = new Rectangle(((int)(myHitbox.getX())),((int)myHitbox.getY()+(int)myHitbox.getWidth()),(int)(myHitbox.getWidth()),this.getRange());
        if(this.attackBox.intersects(playerBox)) {
            p.setHealth(p.getHealth()-this.getDamage());
        }
        
    }
     
    /**
     * getAttackBox
     * This method is a getter for the private variable attackBox
     * @return A rectangle representing the attack hitbox of the enemy
     */
    public Rectangle getAttackBox() {
        return this.attackBox;
    }
    
    /**
     * setAttackBox
     * This method is a setter for the private variable attackBox
     * @return A rectangle representing the updated attack hitbox of the enemy
     */
    public void setAttackBox(Rectangle attackBox) {
        this.attackBox = attackBox;
    }
}