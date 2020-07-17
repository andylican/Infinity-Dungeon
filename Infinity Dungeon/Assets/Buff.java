/**
 * Buff.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a buff object, which is a consumable item that increases the player's stats when picked up.
 * In the current game, they only restore health due to the game being unbalanced if otherwise but are able to give damage and speed buffs as well. 
 * It is also a subclass of the Item class.
 */
class Buff extends Item {
    private int dmgBuff;
    private int speedBuff;
    private int healthBuff;
    
    /**
     * Buff
     * This constructor creates a buff which boosts the players stats by a certain amount in a specific location.
     * @param 7 integers representing the specifications for the buff including position on the screen, item dimensions, and buff stats.
     */
    Buff(int xPos, int yPos, int height, int width, int dmgBuff, int speedBuff, int healthBuff) {
        super(xPos,yPos, height, width);
       
        this.dmgBuff = dmgBuff;
        this.speedBuff = speedBuff;
        this.healthBuff = healthBuff;
    }
    
    /**
     * getDmgBuff
     * This is a getter for the private variable dmgBuff.
     * @return An integer representing the damage boost of the buff.
     */
    public int getDmgBuff() {
        return this.dmgBuff;
    }
    
    /**
     * getSpeedBuff
     * This is a getter for the private variable speedBuff.
     * @return An integer representing the speed boost of the buff.
     */
    public int getSpeedBuff() {
        return this.speedBuff;
    }
    
    /**
     * getHealthBuff
     * This is a getter for the private variable healthBuff.
     * @return An integer representing the health gain of the buff.
     */
    public int getHealthBuff() {
        return this.healthBuff;
    }
}