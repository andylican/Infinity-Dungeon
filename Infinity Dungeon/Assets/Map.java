/**
 * Map.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a map object, which represents the map of the game including all its rooms, walls, and doors.
 */

import java.util.ArrayList;
class Map {
    private int rows;
    private int columns;
    private int visitedCount;
    private Player player;
    private Room[][]rooms;
    private int[][]walls;
    private boolean[][]visited;
    private Boss boss;
    
    /**
     * Map
     * This constructor creates a map of a certain size and with a player.
     * @param 2 integers representing the size of the map and 1 player representing the player present in the map.
     */
    Map(int rows, int columns, Player player) {
        
        //The map consists of rooms every 2 coordinates with walls and doors seperating every room
        //Therefore, the total map size is twice the size of the rooms dimensions + 1, as it includes all the walls and doors.
        this.rows = rows*2+1;;
        this.columns = columns*2+1;
        this.visitedCount = 1;
        this.rooms = new Room[rows*2+1][columns*2+1];
        this.walls = new int[rows*2+1][columns*2+1];
        this.visited = new boolean[rows*2+1][columns*2+1];
        visited[1][1] = true;
        this.player= player;
        generateRooms();
    }
    
    /**
     * generateRooms
     * This method loops through the 2d rooms array and generates a room in each coordinate, with some random rooms having keys and some rooms being surrounded by walls.
     */
    public void generateRooms() {
        
        //Loop through all the rooms in the map
        for(int i=1; i<rows; i+=2) {
            for(int j=1; j<columns; j+=2) {
                if(rooms[i][j] == null) {
                    
                    //Creates a new room in that position with no key
                    rooms[i][j] = new Room(i,j,false);
                    
                    //Two 2d arrays to store all possible directions to traverse from room to room and room to door respectively.
                    int[][]directions = {{0,2},{2,0},{-2,0},{0,-2}};
                    int[][]doorDirections = {{0,1},{1,0},{-1,0},{0,-1}};
                    
                    //Arraylist to store all directions that lead to a new spot (no room there yet) in order for that spot to be made into a locked room requiring a key to acces.
                    ArrayList<Integer>availableDirections = new ArrayList<>();
                    for(int k=0; k<4;k++) {
                        int newRow = directions[k][0]+i;
                        int newColumn = directions[k][1]+j;
                        if((inBounds(newRow,newColumn)) &&  (rooms[newRow][newColumn] == null)) {
                            availableDirections.add(k);
                            
                            //Creates a door in that new direction (represented by 1 on the walls array).
                            walls[doorDirections[k][0]+i][doorDirections[k][1]+j] = 1;
                        }
                    }
                    
                    //If there is more than one place to create that room (to prevent the current room from being blocked off)
                    if(availableDirections.size() > 1) {
                        
                        //A third chance of the current room spawning a key
                        int hasKey = (int)(Math.random()*3);
                        if(hasKey == 2) {
                            
                            //Makes the current room a new room tht has a key.
                            rooms[i][j] = new Room(i,j,true);
                            
                            //Generates a random available direction to create the locked room in and creates its coordinates along with the coordinates of the locked door seperating them that requires a key to open.
                            int direction = (int)(Math.random()*availableDirections.size());
                            int newRow = directions[availableDirections.get(direction)][0]+i;
                            int newColumn = directions[availableDirections.get(direction)][1]+j;
                            int lockedDoorRow = doorDirections[availableDirections.get(direction)][0]+i;
                            int lockedDoorColumn = doorDirections[availableDirections.get(direction)][1]+j;
                            
                            //A boolean to check if the locked room will block off another room beside it or not.
                            boolean isBlocking = false;
                            
                            //Loop through all 4 directions of the locked room 
                            for(int k=0; k<4; k++) {
                                
                                //Stores coordinates of the connecting doors and adjacent rooms
                                int adjRow = directions[k][0]+newRow;
                                int adjColumn = directions[k][1]+newColumn;
                                int connectingDoorRow = doorDirections[k][0]+newRow;
                                int connectingDoorColumn = doorDirections[k][0]+newColumn;
                                
                                //Checks the room beside the locked room if it is connected to the locked roomm by a door as the locked room is always going to be closed off by walls from all side expect for on the side of the locked door.
                                //This can elimitate previously generated doors of other rooms,.
                                if((inBounds(adjRow,adjColumn)) && (walls[connectingDoorRow][connectingDoorColumn] != 0)) {
                                    
                                    //Counts the amount of connections the adjacent room has,
                                    int directionCount = 0;
                                    for(int l=0; l<4; l++) {
                                        int adjDoorRow = doorDirections[l][0]+adjRow;
                                        int adjDoorColumn = doorDirections[l][1]+adjColumn;
                                        if(walls[adjDoorRow][adjDoorColumn] != 0) {
                                            directionCount += 1;
                                        }
                                    }
                                    
                                    //If the adjacent room has less then 2 coonnections, which means that its only connection is to the locked room, this means that the locked room is blocking off another room and cannot be generated
                                    if(directionCount < 2) {
                                        isBlocking = true;
                                    }
                                }
                            }
                            
                            //If the locked room isn't blocking off any other rooms, make all of its connections walls except for the side with the locked door.
                            if(!isBlocking) {
                                for(int k=0; k<4;k++) {
                                    int doorRow = doorDirections[k][0]+newRow;
                                    int doorColumn = doorDirections[k][1]+newColumn;
                                    walls[doorRow][doorColumn] = 0;
                                }
                                //Creates the locked room as a room without a key
                                rooms[newRow][newColumn] = new Room(newRow,newColumn,false);
                                walls[lockedDoorRow][lockedDoorColumn] = 2;
                            } else {
                                
                                //If the locked room is blokcing off another room, the current room that was supposed to be a key room is now a room without a key, and with no locked door that leads to a locked room.
                                rooms[i][j] = new Room(i,j,false);
                            }
                        }
                    }
                } 
            }
        }
        
        //Spawn the doors in each room in all 4 directions
        for(int i=1; i<rows; i+=2) {
            for(int j=1; j<columns; j+=2) {
                Room room = rooms[i][j];
                int rowDimension = room.getRowDimension();
                int columnDimension = room.getColumnDimension();
                Terrain[][] terrain = room.getTerrain();
                if(walls[i][j-1] != 0) {
                    terrain[rowDimension/2][1] = new Door(rowDimension/2,1,i,j,"left");
                } 
                if(walls[i][j+1] != 0) {
                    terrain[rowDimension/2][columnDimension-2] = new Door(rowDimension/2,columnDimension-2,i,j,"right");
                    
                } 
                if(walls[i-1][j] != 0) {
                    terrain[1][columnDimension/2] = new Door(1,columnDimension/2,i,j,"up");
                    
                } 
                if(walls[i+1][j] != 0) {
                    terrain[rowDimension-3][columnDimension/2] = new Door(rowDimension-3,columnDimension/2,i,j,"down");
                }
            }
        }
    }
    
    /**
     * inBounds
     * This method checks to see if a row and column coordinate is within the boundaries of the map or not.
     * @param 2 integers representing the row and column coordinates.
     * @return A boolean that is true if the given coordinates are within the map boundaries.
     */
    public boolean inBounds(int row, int column) {
        return (row>=0) && (column>=0) && (row<rows) && (column<columns);
    }
     
     /**
     * getRoom
     * This is a getter for the private variable room.
     * @param 2 integers representing the row and column of the specified room.
     * @return A room representing the room at a specific row and column.
     */
    public Room getRoom(int row, int column) {
        return rooms[row][column];
    }
    
    /**
     * getRooms
     * This is a getter for the private variable rooms.
     * @return A 2d array of type room representing a map of all the rooms in the game.
     */
    public Room[][] getRooms() {
        return this.rooms;
    }
    
    /**
     * getWalls
     * This is a getter for the private variable walls.
     * @return A 2d array of integers representing a map of all the walls and doors in the game.
     */
    public int[][] getWalls() {
        return this.walls;
    }
    
    /**
     * getPlayer
     * This is a getter for the private variable player.
     * @return A player representing the player character of the game inside the map.
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * setPlayer
     * This is a setter for the private variable player.
     * @param A player representing the updated player character of the game inside the map.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * getVisited
     * This is a getter for the private variable visited.
     * @return A 2d array of booleans representing if each room has been visited or not.
     */
    public boolean[][] getVisited() {
        return this.visited;
    }
    
     /**
     * setVisited
     * This is a setter for the private variable visited.
     * @param A 2d array of booleans representing if each room has been visited or not.
     */
    public void setVisited(boolean[][] visited) {
        this.visited = visited;
    }
    
     /**
     * getVisitedCount
     * This is a getter for the private variable visitedCount.
     * @return An integer representing the amount of rooms that have been visited by the player.
     */
    public int getVisitedCount() {
        return this.visitedCount;
    }
    
     /**
     * setVisitedCount
     * This is a setter for the private variable visitedCount.
     * @param An representing the updated amount of rooms that have been visited by the player.
     */
    public void setVisitedCount(int visitedCount) {
        this.visitedCount = visitedCount;
    }
    
    /**
     * getRows
     * This is a getter for the private variable rows.
     * @return An integer representing the row dimension of the map.
     */
    public int getRows() {
        return this.rows;
    }
    
    
    /**
     * getColumns
     * This is a getter for the private variable columns.
     * @return An integer representing the column dimension of the map.
     */
    public int getColumns() {
        return this.columns;
    }
    
    /**
     * getBoss
     * This is a getter for the private variable boss.
     * @return A boss representing the final boss of the game.
     */
    public Boss getBoss() {
        return this.boss;
    }
    
    
    /**
     * setBoss
     * This is a setter for the private variable boss.
     * @param A boss representing the final boss of the game.
     */
    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}