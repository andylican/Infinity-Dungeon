/**
 * HeavySword
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a HeavySword object, which is a subclass of Sword. A heavy sword acts like a sword but has different stats
 * for damage and cooldown, and it will knock back enemies with its attack.
 */

import java.awt.Rectangle;
import java.util.ArrayList;
class HeavySword extends Sword {
    
    /**
     * HeavySword
     * This constructor creates a HeavySword object with certain specifications like position and size, along with preset damage, range, and cooldown stats.
     * @param 4 integers representing the specifications of the HeavySword
     */
    HeavySword(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width);
        this.setDamage(50);
        this.setCooldown(1.5);
    }
    
    /**
     * attackLeft
     * This method causes the player with this weapon equiped to attack left, damaging any enemies in range.
     * @param A player representing the player wielding the weapon and an arraylist of type enemy representing the list of all the enemies in the current room.
     */
    public void attackLeft(Player player, ArrayList<Enemy>enemies) {
        
        //Sets the weapon's hitbox as its attack area
        Rectangle playerBox = player.getHitbox();
        int GridToScreenRatio = GameFrame.getGridToScreenRatio();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()-range),(int)(playerBox.getY()),range,(int)(playerBox.getHeight())));
        
        //Loops through all enemies to see if the enemy is in range of the weapon's attack and one of them is, they take damage and get knocked back.
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
                e.setXPos(e.getXPos()-GridToScreenRatio);
                e.getHitbox().setLocation(e.getXPos(),e.getYPos());
               
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
        int GridToScreenRatio = GameFrame.getGridToScreenRatio();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()+playerBox.getWidth()),(int)(playerBox.getY()),range,(int)(playerBox.getHeight())));
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
                e.setXPos(e.getXPos()+GridToScreenRatio);
                e.getHitbox().setLocation(e.getXPos(),e.getYPos());
                
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
        int GridToScreenRatio = GameFrame.getGridToScreenRatio();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()),(int)(playerBox.getY()-range),(int)(playerBox.getWidth()),range));
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
                e.setYPos(e.getYPos()-GridToScreenRatio);
                e.getHitbox().setLocation(e.getXPos(),e.getYPos());
                
            
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
        int GridToScreenRatio = GameFrame.getGridToScreenRatio();
        int range = this.getRange();
        int damage = this.getDamage();
        this.setHitBox(new Rectangle((int)(playerBox.getX()),(int)(playerBox.getY()+playerBox.getHeight()),(int)(playerBox.getWidth()),range));
        for(Enemy e:enemies) {
            if(this.getHitbox().intersects(e.getHitbox())) {
                e.setHealth(e.getHealth()-damage-player.getDamage());
                e.setYPos(e.getYPos()+GridToScreenRatio);
                e.getHitbox().setLocation(e.getXPos(),e.getYPos());
                
            }
            
        }
    }
}