/**
 * Trap.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a trap object, which is a subclass of Terrain. A trap is a terrain object that deals damage when a character collides with it.
 * It gets used up and disappears afterwards.
 */

import java.awt.Rectangle;
class Trap extends Terrain {
    private int damage;
    private boolean isActive;
    
    /**
     * Trap
     * This constructor creates a Trap object with a location, a hitbox, and the damage it deals.
     * @param 3 integers representing the specifications of the trap object.
     */
    Trap (int row, int column, int damage) {
        super(row, column);
        this.damage = damage;
        this.isActive = true;
    }
    
    /**
     * characterCollision
     * This is a method that damages the character that collides with the trap. It overrides the abstract method in the Terrain class.
     * @param A character representing the character that will recieve the damage dealt by the trap.
     */
    public void characterCollision (Character character) {
        Rectangle trapBox = this.getHitbox();
        Rectangle characterBox = character.getHitbox();
        if (trapBox.intersects(characterBox)) {
            character.setHealth(character.getHealth()-damage);
            this.isActive = false;
        }
    }
    
    /**
     * getIsActive
     * This is a getter for the private variable isActive.
     * @return A boolean, representing if the trap is active or not. A non active trap will be removed from the terrain array in GameFrame.
     */
    public boolean getIsActive() {
        return this.isActive;
    }
}
