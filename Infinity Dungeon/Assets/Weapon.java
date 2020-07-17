/**
 * Weapon.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a Weapon object (subclass), and is a subclass of Item. 
 * A weapon is an item that the player must pick up and use to attack and thus deal damage to an enemy.
 * It is abstract however so in order to create an object you must create one of its subclasses, not just a general weapon object.
 */

import java.util.ArrayList;
import java.awt.Rectangle;
abstract class Weapon extends Item {
    private int damage;
    private int range;
    private double cooldown;
    private boolean attack;
    
     /**
     * Weapon
     * This constructor creates a Weapon object with certain specifications like position, size, damage, range, and cooldown.
     * @param 7 integers representing the specifications of the weapon.
     */
    Weapon(int xPos, int yPos, int height, int width, int damage, int range, double cooldown){
        super(xPos,yPos,height,width);
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.attack = false;
    }
    
    /**
     * getRange
     * This is a getter for the private variable range.
     * @return An integer representing the range of the weapon.
     */
    public int getRange() {
        return this.range;
    }
    
    /**
     * setRange
     * This is a setter for the private variable range.
     * @param An integer representing the updated range of the weapon.
     */
    public void setRange(int range) {
        this.range = range;
    }
    
    /**
     * getDamage
     * This is a getter for the private variable damage.
     * @return An integer representing the damage the weapon deals.
     */
    public int getDamage() {
        return this.damage;
    }
    
    /**
     * setDamage
     * This is a setter for the private variable damage.
     * @param An integer representing the updated damage the weapon deals.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    
    /**
     * getCooldown
     * This is a getter for the private variable cooldown.
     * @return A double representing the time interval for which the weapon cannot attack after its previous attack.
     */
    public double getCooldown() {
        return this.cooldown;
    }
    
     /**
     * setCooldown
     * This is a setter for the private variable cooldown.
     * @return A double representing the updated time interval for which the weapon cannot attack after its previous attack.
     */
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }
}
