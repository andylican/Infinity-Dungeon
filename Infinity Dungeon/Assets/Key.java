/**
 *Key.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 19, 2019
 * This class is used as a template to create an Key object, which is a sublass of Item.
 * An key is an item that can be picked up by the player to unlocked a locked door (that has a keyhole on it) which will consume the key.
 */

class Key extends Item {
    private int roomRow;
    private int roomColumn;
    
    /**
     * Key
     * This constructor creates a Key object with certain specifications like position, size, and the location of its room.
     * @param 6 integers representing the specifications of the key.
     */
    Key(int xPos, int yPos, int height, int width, int roomRow, int roomColumn) {
        super(xPos, yPos, height, width);
        this.roomRow = roomRow;
        this.roomColumn = roomColumn;
    }
    
    /**
     * getRoomRow
     * This is a getter for the private variable roomRow.
     * @return An integer representing the row position of the room the key is located in.
     */
    public int getRoomRow() {
        return this.roomRow;
    }
    
    /**
     * getRoomColumn
     * This is a getter for the private variable roomColumn.
     * @return An integer representing the column position of the room the key is located in.
     */
    public int getRoomColumn() {
        return this.roomColumn;
    }
}