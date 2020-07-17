/**
 * Boss.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create a Boss enemy object. It is a subclass of Ranged Enemy as it also shoots projectiles,
 * but it has additional attack patterns outside of shooting towards the Player.
 */

import java.util.ArrayList;
class Boss extends RangedEnemy{
    private boolean chainAttack;
    private int attackCount;
    /**
     * Boss
     * This constructor creates a boss enemy that has certain attributes like health, damage, speed, position, size, range, and cooldown.
     * @param 9 integers and 1 double representing the specifications for the boss including health, damage, cooldown, and projectile speed.
     */
    Boss (int health, int damage, int speed, int xPos, int yPos, int height, int width, int range, double cooldown, int projectileSpeed) {
        super (health, damage, speed, xPos, yPos, height, width, range, cooldown, projectileSpeed);
        chainAttack = false;
        attackCount = 0;
    }
    
    /**
     * multiAttack
     * This method is a special attack that shoots bullets in 16 directions around the boss
     * @param An ArrayList representing the bullets in the room.
     */
    public void multiAttack (ArrayList<Projectile> projectiles) {
        for (int i = 0; i < 18; i++) {
            double speed = this.getProjectileSpeed();
            double theta = Math.PI * 2 / 18 * i;
            int xSpeed = (int)(speed * Math.cos(theta));
            int ySpeed = (int)(speed * Math.sin(theta));
            projectiles.add(new Projectile (this.getDamage(), this.getXPos()+this.getWidth()/3, this.getYPos()+this.getHeight()/3, xSpeed, ySpeed, GameFrame.getGridToScreenRatio()/3, GameFrame.getGridToScreenRatio()/3,this.getRange(),"enemy",false));
        }
    }
    
    /**
     * getChainAttack
     * This is a getter for the private variable chainAttack.
     * @return An integer representing if the boss is executing its chain attack.
     */
    public boolean getChainAttack () {
        return chainAttack;
    }
    
    /**
     * setChainAttack
     * This is a setter for the private variable chainAttack.
     * @param An integer representing if the boss is executing its chain attack.
     */
    public void setChainAttack (boolean chainAttack) {
        this.chainAttack = chainAttack;
    }
    
    /**
     * getAttackCount
     * This is a getter for the private variable attackCount.
     * @return An integer representing the number of bullets the boss has shot during its chain attack;
     */
        public int getAttackCount () {
        return attackCount;
    }
        
    /**
     * setAttackCount
     * This is a setter for the private variable attackCount.
     * @param An integer representing the number of bullets the boss has shot during its chain attack;
     */
    public void setAttackCount (int attackCount) {
        this.attackCount = attackCount;
    }
    
}