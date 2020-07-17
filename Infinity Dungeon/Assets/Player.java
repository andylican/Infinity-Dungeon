/**
 * Player.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a player object. A player is the character controled by the user who is playing the game 
 * and can move or attack by the user pressing buttons on the keyboard. It is the main character of the game and is unique from all other characters.
 */

import java.awt.Rectangle;
class Player extends Character {
    private Weapon weapon;
    private boolean hasKey;
    private boolean attacking;
    private double weaponSwitchTime;
    private Key key;
    
    /**
     * Player
     * This constructor creates a player object that has certain attributes like health, damage, speed, position, and size.
     * It also has variables like attacking to determine if the player is currently attacking or not, and weaponSwitchtime which represents the last 
     * time the player has switched weapons to prevent uncontrollable switchig.
     * @param 7 integers representing the specifications of the player created.
     */
    Player(int health, int damage, int speed, int xPos, int yPos, int height, int width) {
        super(health, damage, speed, xPos, yPos, height, width, false);
        this.attacking = false;
        this.weaponSwitchTime = System.nanoTime()/1000000000;
    }
    
    /**
     * enemyCollision
     * This method checks to see if the player is colliding with an enemy and will stop the player from moving and push it back if it is colliding.
     * @param An enemy representing the enemy the player is colliding with.
     */
    public void enemyCollision (Enemy e) {
        Rectangle playerBox = this.getHitbox();
        Rectangle enemyBox = e.getHitbox();
        
        //4 cases representing the 4 possible directions the player came from.
        if (playerBox.intersects(enemyBox)) {
            if(this.getXPos() < this.getPrevXPos()) {
                this.setXPos((int)(enemyBox.getX()+enemyBox.getWidth()));
            } else if(this.getXPos() > this.getPrevXPos()) {
                this.setXPos((int)(enemyBox.getX()-playerBox.getWidth()));
            } else if(this.getYPos() < this.getPrevYPos()) {
                this.setYPos((int)(enemyBox.getY()+enemyBox.getHeight()));
            } else {
                this.setYPos((int)(enemyBox.getY()-playerBox.getHeight()));
            }
            playerBox.setLocation(this.getXPos(), this.getYPos());
        }
    }
     
    /**
     * moveLeft
     * This method makes the player move left and sets its graphic number if it is over 9 to begin the next cycle of the animation.
     */
    public void moveLeft() {
        super.moveLeft();
        if (this.getGraphic() > 9) {
            this.setGraphic(0);
        }
    }
    
    /**
     * moveRight
     * This method makes the player move right and sets its graphic number if it is over 9 to begin the next cycle of the animation.
     */
    public void moveRight() {
        super.moveRight();
        if (this.getGraphic() > 9) {
            this.setGraphic(0);
        }
    }
    
    /**
     * moveUp
     * This method makes the player move up and sets its graphic number if it is over 9 to begin the next cycle of the animation.
     */
    public void moveUp() {
        super.moveUp();
        if (this.getGraphic() > 9) {
            this.setGraphic(0);
        }
    }
    
    /**
     * moveDown
     * This method makes the player move down and sets its graphic number if it is over 9 to begin the next cycle of the animation.
     */
    public void moveDown() {
        super.moveDown();
        if (this.getGraphic() > 9) {
            this.setGraphic(0);
        }
    }
    
    /**
     * getWeapon
     * This is a getter for the private variable weapon.
     * @return A weapon representing the weapon that the character has equipped.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    /**
     * setWeapon
     * This is a setter for the private variable weapon.
     * @param A weapon representing the updated weapon the player has equipped.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    /**
     * getAttacking
     * This is a getter for the private variable attacking.
     * @return A boolean representing if the character is currently attacking or not.
     */
    public boolean getAttacking() {
        return this.attacking;
    }
    
    /**
     * setAttacking
     * This is a setter for the private variable attacking.
     * @param A boolean representing if the character is currently attacking or not.
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
    
    /**
     * getWeaponSwitchTime
     * This is a getter for the private variable weaponSwitchTime.
     * @return A double representing the previous time the player has switched weapons.
     */
    public double getWeaponSwitchTime() {
        return this.weaponSwitchTime;
    }
    
    
    /**
     * setWeaponSwitchTime
     * This is a setter for the private variable weaponSwitchTime.
     * @param A double representing the previous time the player has switched weapons.
     */
    public void setWeaponSwitchTime(double weaponSwitchTime) {
        this.weaponSwitchTime = weaponSwitchTime;
    }
    
    
    /**
     * getKey
     * This is a getter for the private variable key.
     * @return A key representing the current key the player has equipped.
     */
    public Key getKey() {
        return this.key;
    }
    
    
    /**
     * setKey
     * This is a setter for the private variable key.
     * @param A key representing the updated key the player has equipped.
     */
    public void setKey(Key key) {
        this.key = key;
    }
}