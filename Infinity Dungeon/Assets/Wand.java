/**
 * Wand.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a Wand object, which is a subclass of Weapon. 
 * A wand is the standard ranged weapon of the player and is able to shoot projectiles at enemies from a long distance.
 */

import java.awt.Rectangle;
import java.util.ArrayList;
class Wand extends Weapon {
    private int projectileSpeed;
    
    /**
     * Wand
     * This constructor creates a Wand object with certain specifications like position and size along with a preset damage, range, cooldown, and projectile speed.
     * @param 4 integers representing the specifications of the wand.
     */
    Wand(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width, 20, GameFrame.getGridToScreenRatio()*15, 0.75);
        this.projectileSpeed = GameFrame.getGridToScreenRatio();
    }
    
    /**
     * attackLeft
     * This method causes the player with this weapon equiped to attack left, shooting a projectile in that direction.
     * @param A player representing the player wielding the weapon and an arraylist of type projectile, representing the list of all the projectiles in the current room.
     */
    public void attackLeft(Player player, ArrayList<Projectile>projectiles) {
        
        //Updates the hitbox of the wand
        this.setXPos(player.getXPos());
        this.setYPos(player.getYPos());
        this.getHitbox().setLocation(this.getXPos(),this.getYPos());
        int range = this.getRange();
        int damage = this.getDamage();
        int xSpeed = -1*projectileSpeed;
        int ySpeed = 0;
        
        //Creates a projectile by adding it to the projectile list.
        projectiles.add(new Projectile (damage, this.getXPos()-player.getWidth()/3, this.getYPos()+player.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",false));
        
        
    }
    
    
    /**
     * attackRight
     * This method causes the player with this weapon equiped to attack right, shooting a projectile in that direction.
     * @param A player representing the player wielding the weapon and an arraylist of type projectile, representing the list of all the projectiles in the current room.
     */
    public void attackRight(Player player, ArrayList<Projectile>projectiles) {
        this.setXPos(player.getXPos());
        this.setYPos(player.getYPos());
        this.getHitbox().setLocation(this.getXPos(),this.getYPos());
        int range = this.getRange();
        int damage = this.getDamage();
        int xSpeed = projectileSpeed;
        int ySpeed = 0;
        projectiles.add(new Projectile (damage, this.getXPos()+player.getWidth(), this.getYPos()+player.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",false));
        
        
    }
    
    /**
     * attackUp
     * This method causes the player with this weapon equiped to attack up, shooting a projectile in that direction.
     * @param A player representing the player wielding the weapon and an arraylist of type projectile, representing the list of all the projectiles in the current room.
     */
    public void attackUp(Player player, ArrayList<Projectile>projectiles) {
        this.setXPos(player.getXPos());
        this.setYPos(player.getYPos());
        this.getHitbox().setLocation(this.getXPos(),this.getYPos());
        int range = this.getRange();
        int damage = this.getDamage();
        int xSpeed = 0;
        int ySpeed = -1*projectileSpeed;
        projectiles.add(new Projectile (damage, this.getXPos()+player.getWidth()/3, this.getYPos()-player.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",false));
    }
    
    /**
     * attackDown
     * This method causes the player with this weapon equiped to attack down, shooting a projectile in that direction.
     * @param A player representing the player wielding the weapon and an arraylist of type projectile, representing the list of all the projectiles in the current room.
     */
    public void attackDown(Player player, ArrayList<Projectile>projectiles) {
        this.setXPos(player.getXPos());
        this.setYPos(player.getYPos());
        this.getHitbox().setLocation(this.getXPos(),this.getYPos());
        int range = this.getRange();
        int damage = this.getDamage();
        int xSpeed = 0;
        int ySpeed = projectileSpeed;
        projectiles.add(new Projectile (damage, this.getXPos()+player.getWidth()/3, this.getYPos()+player.getHeight(), xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",false));
        
        
    }
    
    /**
     * getProjectileSpeed
     * This is a getter for the private variable projectileSpeed.
     * @return An integer representing the speed of the fired projectile.
     */
    public int getProjectileSpeed() {
        return this.projectileSpeed;
    }
    
    /**
     * setProjectileSpeed
     * This is a setter for the private variable projectileSpeed.
     * @param An integer representing the updated speed of the fired projectile.
     */
    public void setProjectileSpeed(int speed) {
        this.projectileSpeed = speed;
    }
}