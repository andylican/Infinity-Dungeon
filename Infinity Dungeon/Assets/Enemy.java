/**
 * Enemy.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create an enemy object (subclass). An enemy is a character tha attacks the player and is what the player needs to kill.
 * Enemies can come in 2 varieties: melee or ranged. It is abstract however so in order to create an object you must create one of its subclasses,
 * not just a general enemy object.
 */


import java.awt.Rectangle;
abstract class Enemy extends Character {
    private int range;
    private double cooldown;
    private double moveTime;
    private boolean canAttack;
    
     /**
     * Enemy
     * This constructor creates an enemy object that has certain attributes like health, damage, speed, position, size, range, and cooldown.
     * @param 8 integers representing the specifications of the enemy created, a double representing the enemy's attack cooldown, and a boolean that is true if the enemy is currently attacking or not.
     */
    Enemy(int health, int damage, int speed, int xPos, int yPos, int height, int width, int range, double cooldown, boolean attacking) {
        super(health, damage, speed, xPos, yPos, height, width, false);
        this.range = range;
        this.moveTime = System.nanoTime()/1000000000.0;
        this.cooldown = cooldown;
        canAttack = true;
    }
    
    /**
     * characterCollision
     * This method checks to see if the enemy is colliding with another character and if it is it will push the enemy back.
     * @param A character representing the character that the enemy is colliding with.
     */
    public void characterCollision (Character c) {
        Rectangle characterBox = c.getHitbox();
        Rectangle enemyBox = this.getHitbox();
        if (characterBox.intersects(enemyBox)) {
            if(this.getXPos() < this.getPrevXPos()) {
                this.setXPos((int)(characterBox.getX()+characterBox.getWidth()));
            } else if(this.getXPos() > this.getPrevXPos()) {
                this.setXPos((int)(characterBox.getX()-characterBox.getWidth()));
            } else if(this.getYPos() < this.getPrevYPos()) {
                this.setYPos((int)(characterBox.getY()+characterBox.getHeight()));
            } else {
                this.setYPos((int)(characterBox.getY()-enemyBox.getHeight()));
            }
            enemyBox.setLocation(this.getXPos(), this.getYPos());
        }
    }
    
       
    /**
     * getCooldown
     * This is a getter for the private variable cooldown.
     * @return A double representing the attack cooldown of the enemy.
     */
    public double getCooldown() {
        return this.cooldown;
    }
    
    /**
     * setCooldown
     * This is a setter for the private variable cooldown.
     * @param A double representing the updated attack cooldown of the enemy.
     */
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }
    
       
    /**
     * getRange
     * This is a getter for the private variable range.
     * @return An integer representing the attack range of the enemy.
     */
    public int getRange() {
        return this.range;
    }
    
    /**
     * setRange
     * This is a setter for the private variable range.
     * @param An integer representing the updated attack range of the enemy.
     */
    public void setRange(int range) {
        this.range = range;
    }
    
     /**
     * getCanAttack
     * This is a getter for the private variable canAttack.
     * @return A boolean that is true if the enemy is able to attack.
     */
    public boolean getCanAttack() {
        return this.canAttack;
    }
    
    /**
     * setCanAttack
     * This is a setter for the private variable canAttack.
     * @param A boolean that is true if the enemy is able to attack.
     */
    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }
    
}
