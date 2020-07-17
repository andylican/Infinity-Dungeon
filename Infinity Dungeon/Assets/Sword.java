/**
 * Sword.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a Sword object which is a subclass of Weapon. A Sword is the standard melee weapon of the player that is able to attack enemies at a short range.
 */

import java.awt.Rectangle;
import java.util.ArrayList;
class Sword extends Weapon{
    
    /**
     * Sword
     * This constructor creates a Sword object with certain specifications like position and size, along with preset damage, range, and cooldown stats.
     * @param 4 integers representing the specifications of the Sword
     */
    Sword(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width, 20, GameFrame.getGridToScreenRatio(), 0.75);
    }
    
     /**
     * attackLeft
     * This method causes the player with this weapon equiped to attack left, damaging any enemies in range.
     * @param A player representing the player wielding the weapon and an arraylist of type enemy representing the list of all the enemies in the current room.
     */
    public void attackLeft(Player player, ArrayList<Enemy>enemies) {
         
        //Sets the weapon's hitbox as its attack area
        Rectangle playerBox = player.getHitbox();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()-range),(int)(playerBox.getY()),range,(int)(playerBox.getHeight())));
        
        //Loops through all enemies to see if the enemy is in range of the weapon's attack and one of them is, they take damage.
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
            }
        }
    }
    
    /**
     * attackRight
     * This method causes the player with this weapon equiped to attack right, damaging any enemies in range.
     * @param A player representing the player wielding the weapon and an arraylist of type enemy representing the list of all the enemies in the current room.
     */
    public void attackRight(Player player, ArrayList<Enemy>enemies) {
        Rectangle playerBox = player.getHitbox();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()+playerBox.getWidth()),(int)(playerBox.getY()),range,(int)(playerBox.getHeight())));
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
            }
        }
    }
    
    /**
     * attackUp
     * This method causes the player with this weapon equiped to attack up, damaging any enemies in range.
     * @param A player representing the player wielding the weapon and an arraylist of type enemy representing the list of all the enemies in the current room.
     */
    public void attackUp(Player player, ArrayList<Enemy>enemies) {
        Rectangle playerBox = player.getHitbox();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()),(int)(playerBox.getY()-range),(int)(playerBox.getWidth()),range));
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
            }
        }
    }
    
    /**
     * attackDown
     * This method causes the player with this weapon equiped to attack down, damaging any enemies in range.
     * @param A player representing the player wielding the weapon and an arraylist of type enemy representing the list of all the enemies in the current room.
     */
    public void attackDown(Player player, ArrayList<Enemy>enemies) {
        Rectangle playerBox = player.getHitbox();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()),(int)(playerBox.getY()+playerBox.getHeight()),(int)(playerBox.getWidth()),range));
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
            }
        }
    }
}