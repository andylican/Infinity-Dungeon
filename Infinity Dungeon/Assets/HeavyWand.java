/**
 * HeavyWand
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a HeavyWand object, which is a subclass of Wand. A heavy wand acts like a wand but has different stats
 * for damage and cooldown, and it will knock back enemies with its attack.
 */

import java.awt.Rectangle;
import java.util.ArrayList;
class HeavyWand extends Wand {
    
    /**
     * HeavyWand
     * This constructor creates a HeavyWand object with certain specifications like position and size along with a preset damage and cooldown different from a normal wand (superclass).
     * @param 4 integers representing the specifications of the heavy wand.
     */
    HeavyWand(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width);
        this.setDamage(50);
        this.setCooldown(1.5);
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
        int projectileSpeed = this.getProjectileSpeed();
        int xSpeed = -1*projectileSpeed;
        int ySpeed = 0;
     
        //Creates a projectile by adding it to the projectile list, that can knock enemies back.
        projectiles.add(new Projectile (damage, this.getXPos()-player.getWidth()/3, this.getYPos()+player.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",true));
        
        
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
        int projectileSpeed = this.getProjectileSpeed();
        int xSpeed = projectileSpeed;
        int ySpeed = 0;    
        projectiles.add(new Projectile (damage, this.getXPos()+player.getWidth(), this.getYPos()+player.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",true));
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
        int projectileSpeed = this.getProjectileSpeed();
        int xSpeed = 0;
        int ySpeed = -1*projectileSpeed;
        projectiles.add(new Projectile (damage,this.getXPos()+player.getWidth()/3, this.getYPos()-player.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",true));
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
        int projectileSpeed = this.getProjectileSpeed();
        int xSpeed = 0;
        int ySpeed = projectileSpeed;
        projectiles.add(new Projectile (damage,this.getXPos()+player.getWidth()/3, this.getYPos()+player.getHeight(), xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,range,"player",true));
    }
}