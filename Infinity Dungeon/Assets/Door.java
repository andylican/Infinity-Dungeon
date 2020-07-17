/**
 * Door.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a door object, which is a subclass of the Terrain class.
 * A door object will transport the player to the adjacent room if the player collides with its hitbox(which protrudes slightly outwards for ease of collision),
 * and assuming all conditions are met (eg. all enemies are killed or player needs a key if it is a key door).
 */

import java.awt.Rectangle;
import java.util.ArrayList;
class Door extends Terrain {
    private String direction;
    private int roomRow;
    private int roomColumn;
    
    /**
     * Door
     * This constructor creates a door object with certain parameters like location, the location of its room, and the direction it leads to.
     * @param 5 integers representing the specifications of the door.
     */
    Door (int row, int column,int roomRow, int roomColumn, String direction) {
        super(row,column);
        this.roomRow = roomRow;
        this.roomColumn = roomColumn;
        this.direction = direction;
        Rectangle doorBox = this.getHitbox();
        int xPos = (int)doorBox.getX();
        int yPos = (int)doorBox.getY();
        int GridToScreenRatio = GameFrame.getGridToScreenRatio();
        
        //These statements are there to make the hitbox of the door protrude outwards slightly to ensure the player collides with the door instead of the wall beside it if the player walks to the door.
        //However, this extended hitbox is invisible to preserve asthetic appeal.
        if(direction.equals("left")) {
            doorBox.setSize((int)(GridToScreenRatio*1.5),GridToScreenRatio);
        } else if(direction.equals("right")) {
            doorBox.setLocation((int)(xPos-GridToScreenRatio/2),yPos);
            doorBox.setSize((int)(GridToScreenRatio*1.5),GridToScreenRatio);
        } else if(direction.equals("up")) {
            doorBox.setSize(GridToScreenRatio,(int)(GridToScreenRatio*1.5));
        } else {
            doorBox.setSize(GridToScreenRatio,(int)(GridToScreenRatio*1.5));
            doorBox.setLocation(xPos,(int)(yPos-GridToScreenRatio/2));
        }
    }
    
    /**
     * characterCollision
     * This method is called when a character (the character will always be a player when called from GameFrame but this is a inherited method from Terrain) collides with the door.
     * The player will be teleported to the adjacent room assuming conditions are met.
     * @param A Character representing the character that collided with the door.
     */
    public void characterCollision (Character character) {
        Rectangle doorBox = this.getHitbox();
        Rectangle characterBox = character.getHitbox();
        Player player = (Player)character;
        
        if(doorBox.intersects(characterBox)) {
            Map map = GameFrame.getMap();
            ArrayList<Enemy>enemies = GameFrame.getRoom().getEnemies();
            int GridToScreenRatio = GameFrame.getGridToScreenRatio();
            int[][] walls = map.getWalls();
            Room[][] rooms = map.getRooms();
            boolean[][] visited = map.getVisited();
            
            //Checks which direction the door will lead to and chooses the adjacent room accordingly
            if(direction.equals("right")) {
                
                //Every door is locked until all enemies in the current room are defeated. In order for the player to go through the door,
                //the door must either be a normal door (marked by 1 on the wall array in the Map class), or the player must have a key from the same room if the door is a "key unlock" door (marked by 2 on the wall array).
                //The code inside each direction functions the same way, but for a different direction
                if((enemies.isEmpty()) && ((walls[roomRow][roomColumn+1] == 1) || ((walls[roomRow][roomColumn+1] == 2) && (player.getKey() != null) && (player.getKey().getRoomRow() == roomRow) && (player.getKey().getRoomColumn() == roomColumn)))) {
                    GameFrame.setRoom(rooms[roomRow][roomColumn+2]);
                    character.setXPos(GridToScreenRatio*3);
                    
                    //Gets rid of the key if it was used to unlock the door
                    if(walls[roomRow][roomColumn+1] == 2) {
                        player.setKey(null);
                    }
                    
                    //Converts the "key unlock" door to a normal door that doesnt require a key to unlcock anymore (key only needs to be used once).
                    walls[roomRow][roomColumn+1] = 1;
                    
                    //Counts the amount of rooms visited and marks the new room as visited.
                    if(!visited[roomRow][roomColumn+2]) {
                        visited[roomRow][roomColumn+2] = true;
                        map.setVisitedCount(map.getVisitedCount()+1);
                    }
                } else {
                    
                    //Push the character back to the front of the door
                    character.setXPos((int)(doorBox.getX()-characterBox.getWidth()));
                }
            } else if (direction.equals("left")) {
                if((enemies.isEmpty()) && ((walls[roomRow][roomColumn-1] == 1) || ((walls[roomRow][roomColumn-1] == 2) && (player.getKey() != null) && (player.getKey().getRoomRow() == roomRow) && (player.getKey().getRoomColumn() == roomColumn)))) {
                    GameFrame.setRoom(rooms[roomRow][roomColumn-2]);
                    character.setXPos(GridToScreenRatio*(rooms[roomRow][roomColumn].getColumnDimension()-4));
                    if(walls[roomRow][roomColumn-1] == 2) {
                        player.setKey(null);
                    }
                    walls[roomRow][roomColumn-1] = 1;
                    if(!visited[roomRow][roomColumn-2]) {
                        visited[roomRow][roomColumn-2] = true;
                        map.setVisitedCount(map.getVisitedCount()+1);
                    }
                } else {
                    character.setXPos((int)(doorBox.getX()+doorBox.getWidth()));
                }
            } else if (direction.equals("up")) {
                if((enemies.isEmpty()) && ((walls[roomRow-1][roomColumn] == 1) || ((walls[roomRow-1][roomColumn] == 2) && (player.getKey() != null) && (player.getKey().getRoomRow() == roomRow) && (player.getKey().getRoomColumn() == roomColumn)))) {
                    GameFrame.setRoom(rooms[roomRow-2][roomColumn]);
                    character.setYPos(GridToScreenRatio*(rooms[roomRow][roomColumn].getRowDimension()-5));
                    if(walls[roomRow-1][roomColumn] == 2) {
                        player.setKey(null);
                    }
                    walls[roomRow-1][roomColumn] = 1;
                    if(!visited[roomRow-2][roomColumn]) {
                        visited[roomRow-2][roomColumn] = true;
                        map.setVisitedCount(map.getVisitedCount()+1);
                    }
                } else {
                    character.setYPos((int)(doorBox.getY()+doorBox.getHeight()));
                }
            } else {
                if((enemies.isEmpty()) && ((walls[roomRow+1][roomColumn] == 1) || ((walls[roomRow+1][roomColumn] == 2) &&  (player.getKey() != null) && (player.getKey().getRoomRow() == roomRow) && (player.getKey().getRoomColumn() == roomColumn)))) {
                    GameFrame.setRoom(rooms[roomRow+2][roomColumn]);
                    character.setYPos(GridToScreenRatio*3);
                    if(walls[roomRow+1][roomColumn] == 2) {
                        player.setKey(null);
                    }
                    walls[roomRow+1][roomColumn] = 1;
                    if(!visited[roomRow+2][roomColumn]) {
                        visited[roomRow+2][roomColumn] = true;
                        map.setVisitedCount(map.getVisitedCount()+1);
                    }
                } else {
                    character.setYPos((int)(doorBox.getY()-characterBox.getHeight()));
                }
            }
            characterBox.setLocation(character.getXPos(), character.getYPos());
            
        }
        
    }
    
    
    /**
     * getRoomRow
     * This is a getter for the private variable roomRow.
     * @return An integer representing the row coordinate of the room that contains this door.
     */
    public int getRoomRow () {
            return roomRow;
    }
    
    /**
     * getRoomColumn
     * This is a getter for the private variable roomColumn.
     * @return An integer representing the column coordinate of the room that contains this door.
     */
    public int getRoomColumn () {
        return roomColumn;
    }
    
    /**
     * getDirection
     * This is a getter for the private variable direction.
     * @return A string representing the direction this door leads to.
     */
    public String getDirection () {
        return direction;
    }
}