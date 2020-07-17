/**
 * Wall.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a wall object, which is a subclass of Terrain. A wall is a terrain object that prevents characters from moving through it, blocking their paths.
 */

import java.awt.Rectangle;
class Wall extends Terrain {
    private int graphic;
    
    
    /**
     * Wall
     * This constructor creates a Wall object with a location, a hitbox, and a graphic number to determine what picture will be displayed.
     * @param 3 integers representing the specifications of the trap object.
     */
    Wall (int row, int column, int graphic) {
        super(row, column);
        this.graphic = graphic;
    }
    
    /**
     * characterCollision
     * This is a method that pushes back the character that collides with the wall. It overrides the abstract method in the Terrain class.
     * @param A character representing the character that will be pushed back/blocked by the wall.
     */
    public void characterCollision (Character character) {
        Rectangle wallBox = this.getHitbox();
        Rectangle characterBox = character.getHitbox();
        if (wallBox.intersects(characterBox)) {
            if(character.getXPos() < character.getPrevXPos()) {
                character.setXPos((int)(wallBox.getX()+wallBox.getWidth()));
            } else if(character.getXPos() > character.getPrevXPos()) {
                character.setXPos((int)(wallBox.getX()-characterBox.getWidth()));
            } else if(character.getYPos() < character.getPrevYPos()) {
                character.setYPos((int)(wallBox.getY()+wallBox.getHeight()));
            } else {
                character.setYPos((int)(wallBox.getY()-characterBox.getHeight()));
            }
            character.getHitbox().setLocation(character.getXPos(), character.getYPos());
        }
    }
    
    
    /**
     * getGraphic
     * This is a getter for the private variable graphic.
     * @return An integer representing the number for the picture to display the wall as.
     */
    public int getGraphic () {
        return graphic;
    }
}