/**
 * QuickSword.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a QuickSword object, which is a subclass of Sword. A quick sword acts like a sword but has different stats
 * for damage, range and cooldown, notably attacking much faster while having a much lower range.
 */

import java.awt.Rectangle;
import java.util.ArrayList;
class QuickSword extends Sword {
    
    /**
     * QuickSword
     * This constructor creates a QuickSword object with certain specifications like location and size, with preset damage, range, and cooldown.
     * @param 4 integers representing the specifications of the sword.
     */
    QuickSword(int xPos, int yPos, int height, int width) {
        super(xPos, yPos, height, width);
        this.setDamage(20);
        this.setRange((int)(GameFrame.getGridToScreenRatio()*0.5));
        this.setCooldown(0.2);
    }
}
