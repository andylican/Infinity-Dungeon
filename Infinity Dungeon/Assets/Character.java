/**
 * Character.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a character object (subclass). A character is is able to move and has many variables associated with it.
 * Examples include having health, damage, speed, and a position. It is abstract however so in order to create an object you must create one of its subclasses,
 * not just a general character object.
 */

import java.awt.Rectangle;
abstract class Character {
    private int health;
    private int damage;
    private int speed;
    private Rectangle hitbox;
    private int xPos;
    private int yPos;
    private int prevXPos;
    private int prevYPos;
    private String moveDirection;
    private String direction;
    private double moveTime;
    private double attackTime;
    private int graphic;
    private int width;
    private int height;
    private int maxHealth;
    private boolean attacking;
    
    /**
     * Character
     * This constructor creates a character object that has certain attributes like health, damage, speed, and position. 
     * It also has variables like attackTime and moveTime to store when they last moved or attacked.
     * @param 7 integers representing the specifications of the character created.
     */
    Character(int health, int damage, int speed, int xPos, int yPos, int height, int width, boolean attacking) {
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        prevXPos = xPos;
        prevYPos = yPos;
        this.hitbox = new Rectangle(xPos,yPos,height,width);
        this.moveDirection = "";
        this.direction = "down";
        this.moveTime = System.nanoTime()/1000000000.0;
        this.attackTime = System.nanoTime()/1000000000.0;
        this.graphic = 0;
        this.attacking = attacking;
        
    }
    
    /**
     * moveLeft
     * This method allows the character to move left by updating its positition and hitbox based on its speed.
     */
    public void moveLeft() {
        this.setXPos(this.getXPos()-this.getSpeed());
        //this.setXPos(this.getXPos()-1);
        this.getHitbox().setLocation(this.getXPos(), this.getYPos());
        this.setGraphic(this.getGraphic() + 1);
    }
     /**
     * moveRight
     * This method allows the character to move right by updating its positition and hitbox based on its speed.
     */
    public void moveRight() {
        this.setXPos(this.getXPos()+this.getSpeed());
        this.getHitbox().setLocation(this.getXPos(), this.getYPos());
        this.setGraphic(this.getGraphic() + 1);
    }
    
     /**
     * moveUp
     * This method allows the character to move up by updating its positition and hitbox based on its speed.
     */
    public void moveUp() {
        this.setYPos(this.getYPos()-this.getSpeed());
        //this.setYPos(this.getYPos()-1);
        this.getHitbox().setLocation(this.getXPos(), this.getYPos());
        this.setGraphic(this.getGraphic() + 1);
    }
    
     /**
     * moveDown
     * This method allows the character to move down by updating its positition and hitbox based on its speed.
     */
    public void moveDown() {
        this.setYPos(this.getYPos()+this.getSpeed());
        //this.setYPos(this.getYPos()+1);
        this.getHitbox().setLocation(this.getXPos(), this.getYPos());
        this.setGraphic(this.getGraphic() + 1);
    }
    
     
    /**
     * getHealth
     * This is a getter for the private variable health.
     * @return An integer representing the health of the character.
     */
    public int getHealth() {
        return this.health;
    }
    
     
    /**
     * setHealth
     * This is a setter for the private variable speedBuff.
     * @param An integer representing the updated health of the character.
     */
    public void setHealth(int health) {
        this.health = health;
    }
    
     /**
     * getDamage
     * This is a getter for the private variable damage.
     * @return An integer representing the damage the character deals.
     */
    public int getDamage() {
        return this.damage;
    }
    
     
    /**
     * setDamage
     * This is a setter for the private variable damage.
     * @param An integer representing the updated damage the character deals.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    /**
     * getSpeed
     * This is a getter for the private variable speed.
     * @return An integer representing the speed of the character.
     */
    public int getSpeed() {
        return this.speed;
    }
    
    /**
     * setSpeed
     * This is a setter for the private variable speed.
     * @param An integer representing the updated speed of the character.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * getXPos
     * This is a getter for the private variable xPos
     * @return An integer representing the X position of the character.
     */
    public int getXPos() {
        return this.xPos;
    }
    
     /**
     * setXPos
     * This is a setter for the private variable xPos.
     * @param An integer representing the updated X position of the character.
     */
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    
    /**
     * getYPos
     * This is a getter for the private variable yPos
     * @return An integer representing the Y position of the character.
     */
    public int getYPos() {
        return this.yPos;
    }
    
     /**
     * setYPos
     * This is a setter for the private variable yPos.
     * @param An integer representing the updated Y position of the character.
     */
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    
    /**
     * getPrevXPos
     * This is a getter for the private variable prevXPos
     * @return An integer representing the previous X position of the character.
     */
    public int getPrevXPos() {
        return this.prevXPos;
    }
    
    
     /**
     * setPrevXPos
     * This is a setter for the private variable prevXPos.
     * @param An integer representing the updated previous X position of the character.
     */
    public void setPrevXPos(int prevXPos) {
        this.prevXPos = prevXPos;
    }
    
     /**
     * getPrevYPos
     * This is a getter for the private variable prevYPos
     * @return An integer representing the previous Y position of the character.
     */
    public int getPrevYPos() {
        return this.prevYPos;
    }
    
    /**
     * setPrevYPos
     * This is a setter for the private variable prevYPos.
     * @param An integer representing the updated previous Y position of the character.
     */
    public void setPrevYPos(int prevYPos) {
        this.prevYPos = prevYPos;
    }
    
     /**
     * getHitbox
     * This is a getter for the private variable hitbox.
     * @return A Rectangle representing the hitbox of the character.
     */
    public Rectangle getHitbox () {
        return this.hitbox;
    }
    
     /**
     * setHitbox
     * This is a setter for the private variable hitbox.
     * @param A Rectangle representing the updated hitbox of the character.
     */
    public void setHitbox (Rectangle hitbox) {
        this.hitbox = hitbox;
    }
   
     /**
     * getDirection
     * This is a getter for the private variable direction.
     * @return A String representing the direction of the character.
     */
    public String getDirection() {
        return this.direction;
    }
    
    /**
     * setDirection
     * This is a setter for the private variable direction.
     * @param A String representing the updated direction of the character.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    /**
     * getMoveDirection
     * This is a getter for the private variable moveDirection.
     * @return A String representing the movement direction of the character.
     */
    public String getMoveDirection() {
        return this.moveDirection;
    }
    
    /**
     * setMoveDirection
     * This is a setter for the private variable moveDirection.
     * @param A String representing the updated movement direction of the character.
     */
    public void setMoveDirection(String moveDirection) {
        this.moveDirection = moveDirection;
    }
    
    
    /**
     * getMoveTime
     * This is a getter for the private variable moveTime.
     * @return A double representing the previous time the character moved.
     */
    public double getMoveTime() {
        return this.moveTime;
    }
    
    /**
     * setMoveTime
     * This is a setter for the private variable moveTime.
     * @param A double representing the updated previous time the character moved.
     */
    public void setMoveTime(double moveTime) {
        this.moveTime = moveTime;
    }
    
     /**
     * getAttackTime
     * This is a getter for the private variable attackTime.
     * @return A double representing the previous time the character attacked.
     */
    public double getAttackTime() {
        return this.attackTime;
    }
    
    /**
     * setAttackTime
     * This is a setter for the private variable attackTime.
     * @param A double representing the updated previous time the character attacked
     */
    public void setAttackTime(double attackTime) {
        this.attackTime = attackTime;
    }
    
    /**
     * getGraphic
     * This is a getter for the private variable graphic.
     * @return An integer representing the number for the correct picture to help animate the character.
     */
    public int getGraphic() {
        return this.graphic;
    }
    
    /**
     * setGraphic
     * This is a setter for the private variable graphic.
     * @param An integer representing the updated number for the correct picture to help animate the character.
     */
    public void setGraphic(int graphic) {
        this.graphic = graphic;
    }
    
     
    /**
     * getWidth
     * This is a getter for the private variable width.
     * @return An integer representing the width of the character.
     */
     public int getWidth() {
      return this.width;
     }
     
     /**
      * getHeight
      * This is a getter for the private variable height.
      * @return An integer representing the height of the character.
      */
     public int getHeight() {
         return this.height;
     }
     
     /**
      * getMaxHealth
      * This is a getter for the private variable maxHealth.
      * @return An integer representing the maximum health of the character to prevent overhealing.
      */
     public int getMaxHealth() {
         return this.maxHealth;
     }
     
     /**
      * getAttacking
      * This is a getter for the private variable attacking;
      * @return a boolean representing whether or not the character is attacking.
      */
     public boolean getAttacking() {
         return this.attacking;
     }
     
     /**
      * setAttacking
      * This is a getter for the private variable attacking.
      * @return a boolean representing whether or not the character is attacking.
      */
     public void setAttacking(boolean attacking) {
         this.attacking = attacking;
     }
     
}