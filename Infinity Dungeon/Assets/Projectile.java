/**
 * Projectile.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a Projectle object, which can move and deal damage to players or enemies.
 * They are fired from ranged enemies and from the ranged player weapons
 */

import java.awt.Rectangle;
class Projectile {
    private int damage;
    private int xPos;
    private int yPos;
    private int xSpeed;
    private int ySpeed;
    private int range;
    private int xStart;
    private int yStart;
    private String type;
    private boolean heavy;
    private Rectangle hitbox;
    
    /**
     * Projectile
     * This constructor creates a projectile with certain specifications like damage, position, speed, size, range, type, and if it is heavy or not.
     * @param 8 integers representing the specifications for the projectile, a string representing who the projectile was fired from (player or enemy)
     * and a boolean representing if the projectile is heavy thus having a knockback or not.
     */
    Projectile (int damage, int xPos, int yPos, int xSpeed, int ySpeed, int width, int height, int range, String type, boolean heavy) {
        this.damage = damage;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.range = range;
        this.xStart = xPos;
        this.yStart = yPos;
        this.type = type;
        this.heavy = heavy;
        hitbox = new Rectangle(xPos, yPos, width, height);
    }
    
    /**
     * move
     * This method moves teh projectile by updating its position and hitbox based on its speed.
     */
    public void move () {
        xPos += xSpeed;
        yPos += ySpeed;
        hitbox.setLocation(xPos, yPos);
    }
    
    /**
     * wallCollision
     * This method loops through the terrain array and checks to see if the projectile has collided with a wall.
     * @param A 2d array of type Terrain representing the terrain map of the room.
     * @return a boolean representing if the projectile has collided with a wall or not, thus destroying it in GameFrame.
     */
    public boolean wallCollision (Terrain[][] terrain) {
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[0].length; j++) {
                if ((terrain[i][j] instanceof Wall) && (terrain[i][j].getHitbox().intersects(this.hitbox))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * characterCollision
     * This method checks to see if the projectile has collided with a certain character and will deal damage if it does.
     * @param A character representing the character that the projectile hit, and a player who must be passed in to access its base damage to add on to the projectile damage.
     * @return a boolean representing if the projectile collided with a character or not, thus destroying it in GameFrame.
     */
    public boolean characterCollision(Character c, Player p) {
        Rectangle characterBox = c.getHitbox();
        if(this.hitbox.intersects(characterBox)) {
            c.setHealth(c.getHealth()-this.damage);
            
            //If the projectile was shot from a player, apply additional damage by adding the player's base damage.
            if(type.equals("player")) {
                c.setHealth(c.getHealth()-p.getDamage());
            }
            
            //If projectile is heavy, apply a knockback
            if(heavy){
                c.setXPos(c.getXPos()+xSpeed);
                c.setYPos(c.getYPos()+ySpeed);
                characterBox.setLocation(c.getXPos(),c.getYPos());
            }
            return true;
        }
        return false;
    }
    
     /**
     * getDamage
     * This is a getter for the private variable damage.
     * @return An integer representing the damage the projectile deals.
     */
    public int getDamage () {
        return this.damage;
    }
    
     /**
     * setDamage
     * This is a setter for the private variable damage.
     * @param An integer representing the updated damage the projectile deals.
     */
    public void setDamage (int damage) {
        this.damage = damage;
    }
    
     /**
     * getXPos
     * This is a getter for the private variable xPos
     * @return An integer representing the X position of the projectile.
     */
    public int getXPos () {
        return this.xPos;
    }
    
     /**
     * setXPos
     * This is a setter for the private variable xPos.
     * @param An integer representing the updated X position of the projectile.
     */
    public void setXPos (int xPos) {
        this.xPos = xPos;
    }
    
    /**
     * getYPos
     * This is a getter for the private variable yPos
     * @return An integer representing the Y position of the projectile.
     */
    public int getYPos () {
        return this.yPos;
    }
    
    
     /**
     * setYPos
     * This is a setter for the private variable yPos.
     * @param An integer representing the updated Y position of the projectile.
     */
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    
     /**
     * getXSpeed
     * This is a getter for the private variable xSpeed.
     * @return An integer representing the horizontal speed of the projectile.
     */
    public int getXSpeed () {
        return this.xSpeed;
    }
    
    /**
     * setXSpeed
     * This is a setter for the private variable xSpeed.
     * @param An integer representing the updated horizontal speed of the projectile.
     */
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    
     /**
     * getYSpeed
     * This is a getter for the private variable ySpeed.
     * @return An integer representing the vertical speed of the projectile.
     */
    public int getYSpeed () {
        return this.ySpeed;
    }
    
     /**
     * setYSpeed
     * This is a setter for the private variable ySpeed.
     * @param An integer representing the updated vertical speed of the projectile.
     */
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    
     /**
     * getHitbox
     * This is a getter for the private variable hitbox.
     * @return A Rectangle representing the hitbox of the projectile.
     */
    public Rectangle getHitbox () {
        return this.hitbox;
    }
    
    /**
     * setHitbox
     * This is a setter for the private variable hitbox.
     * @param A Rectangle representing the updated hitbox of the projectile.
     */
    public void setHitbox (Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    
     /**
     * getRange
     * This is a getter for the private variable range.
     * @return An integer representing the range of the projectile.
     */
     public int getRange() {
        return this.range;
    }
     
     /**
      * setRange
      * This is a setter for the private variable range.
      * @param An integer representing the updated range of the projectile.
      */
    public void setRange(int range) {
        this.range = range;
    }
    
    /**
     * getXStart
     * This is a getter for the private variable xStart.
     * @return An integer representing the starting X position of the projectile.
     */
    public int getXStart () {
        return this.xStart;
    }
    
    /**
     * setXStart
     * This is a setter for the private variable xStart.
     * @param An integer representing the updated starting X position of the projectile.
     */
    public void setXStart (int xStart) {
        this.xStart = xStart;
    }
    
     /**
     * getYStart
     * This is a getter for the private variable yStart.
     * @return An integer representing the starting Y position of the projectile.
     */
    public int getYStart () {
        return this.yStart;
    }
    
    /**
     * setYStart
     * This is a setter for the private variable yStart.
     * @param An integer representing the updated starting Y position of the projectile.
     */
    public void setYStart(int yStart) {
        this.yStart = yStart;
    }
    
     /**
     * getType
     * This is a getter for the private variable type.
     * @return A string representing from whom the projectle was shot from (player or enemy).
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * setType
     * This is a setter for the private variable type.
     * @param A string representing from whom the projectile was shot from.
     */
    public void setType(String type) {
        this.type = type;
    }
    
     /**
     * getHeavy
     * This is a getter for the private variable heavy.
     * @return A boolean representing if the projectile is heavy or not.
     */
    public boolean getHeavy() {
        return this.heavy;
    }
    
    /**
     * setHeavy
     * This is a setter for the private variable heavy.
     * @param A boolean representing if the projectile is heavy or not.
     */
    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }
}