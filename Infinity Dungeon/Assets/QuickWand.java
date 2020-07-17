/**
 * QuickWand.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a QuickWand object, which is a subclass of Wand. A quick wand acts like a wand but has different stats
 * for damage, range and cooldown, notably attacking much faster while having a shorter range.
 */

class QuickWand extends Wand {
    
    /**
     * QuickWand
     * This constructor creates a QuickWand object with certain specifications like position and size along with a preset damage, range and cooldown different from a normal wand (superclass).
     * @param 4 integers representing the specifications of the quick wand.
     */
     QuickWand(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width);
        this.setDamage(20);
        this.setRange(GameFrame.getGridToScreenRatio()*10);
        this.setCooldown(0.2);
    }
}