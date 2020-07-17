/**
 * Terrain.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a terrain object (subclass). A terrian object is located on a 2d array that acts as a tile map just for terrain.
 * A terrain object has an abastreact method called character collision that will be called to decide what happens when a character collides with it.
 * It is abstract however so in order to create an object of this class you must create an instance of one of its subclasses, not just a general Terrain object.
 */

import java.awt.Rectangle;
abstract class Terrain {
    private Rectangle hitbox;
    
    /**
     * Terrain
     * This constructor creates a Terrain object with a location and a hitbox.
     * @param 2 integers representing the location of the Terrain object on the 2d terrain tile map in the room class.
     */
    Terrain (int row, int column) {
        hitbox = new Rectangle(column * GameFrame.getGridToScreenRatio(), row * GameFrame.getGridToScreenRatio(), GameFrame.getGridToScreenRatio(), GameFrame.getGridToScreenRatio());
    }
    
    /**
     * getHitbox
     * This is a getter for the private variable hitbox.
     * @return A Rectangle representing the hitbox of the terrain.
     */
    public Rectangle getHitbox () {
        return hitbox;
    }
    
    /**
     * characterCollision
     * This is an abstract method that is called to decide what happens when a character collides with the Terrain object.
     * @param A character representing the character that is checked to see if it is colliding with the Terrain object.
     */
    public abstract void characterCollision (Character character);
}