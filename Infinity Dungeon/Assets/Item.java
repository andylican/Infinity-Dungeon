/**
 *Item.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create an Item object (subclass).
 * An item is an object that can be picked up by the player to give an effect or can be equiped by a player.
 * It is abstract however so in order to create an object you must create one of its subclasses, not just a general item object.
 */

import java.awt.Rectangle;


abstract class Item {
    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private Rectangle hitbox;
    
    /**
     * Item
     * This constructor creates an Item object with certain specifications like position and size.
     * @param 4 integers representing the specifications of the item.
     */
    Item(int xPos, int yPos, int height, int width) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.hitbox = new Rectangle (xPos,yPos, height, width);
    }
    
    
    /**
     * getXPos
     * This is a getter for the private variable xPos
     * @return An integer representing the X position of the item.
     */
    public int getXPos() {
        return this.xPos;
    }
    
    /**
     * setXPos
     * This is a setter for the private variable xPos.
     * @param An integer representing the updated X position of the item.
     */
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    
    /**
     * getYPos
     * This is a getter for the private variable yPos
     * @return An integer representing the Y position of the item.
     */
    public int getYPos() {
        return this.yPos;
    }
    
    /**
     * setYPos
     * This is a setter for the private variable yPos.
     * @param An integer representing the updated Y position of the item.
     */
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    
    /**
     * getHitbox
     * This is a getter for the private variable hitbox.
     * @return A Rectangle representing the hitbox of the item.
     */
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    
    /**
     * setHitbox
     * This is a setter for the private variable hitbox.
     * @param A Rectangle representing the updated hitbox of the item.
     */
    public void setHitBox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    
    /**
     * getWidth
     * This is a getter for the private variable width.
     * @return An integer representing the width of the item.
     */
    public int getWidth() {
        return this.width;
    }
    
    
    /**
     * getHeight
     * This is a getter for the private variable height.
     * @return An integer representing the height of the item.
     */
    public int getHeight() {
        return this.height;
    }
}