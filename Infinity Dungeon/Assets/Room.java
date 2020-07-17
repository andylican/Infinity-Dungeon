/**
 * Room.java
 * Version 1
 * @authors Andy Li and Bryan Zhang
 * January 20, 2019
 * This class is used as a template to create a room object, which is a room that contains all sort of objects like enemies, items, and terrain.
 * Each room is located in a different position on the map.
 */

import java.util.ArrayList;
class Room {
    private static int rowDimension = 18;
    private static int columnDimension = 32;
    private ArrayList<Enemy>enemies;
    private ArrayList<Item>items;
    private ArrayList<Projectile>projectiles = new ArrayList<>();
    private Terrain[][] terrain = new Terrain[rowDimension][columnDimension];
    private int GridToScreenRatio;
    private Player player;
    private boolean hasKey;
    private int row;
    private int column;
    
    /**
     * Room
     * This constructor creates a room with a certain position on the map and may have a key inside it.
     * @param 2 integers representing the position of the room and 1 boolean that is true if the room has a key inside of it.
     */
    Room (int row, int column, boolean hasKey) {
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        this.hasKey = hasKey;
        this.row = row;
        this.column = column;
        GridToScreenRatio = GameFrame.getGridToScreenRatio();
       
        //Assigns graphic number to each wall (1 out of 3 possible images) and creates the walls of the room.
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                int random = (int)(Math.random() * 10);
                int graphic;
                if (random < 1) {
                    graphic = 1;
                } else if (random < 4) {
                    graphic = 2;
                } else {
                    graphic = 0;
                }
                terrain[i][j] = new Wall(i, j, graphic);
            }
        }
        
        //Makes the middle empty, so only the borders have walls.
        for (int i = 2; i < rowDimension - 3; i++) {
            for (int j = 2; j < columnDimension - 2; j++) {
                terrain[i][j] = null;
            }
        }
        
        //Generate random amount of weapons in the room
        int weaponCount = (int)(Math.random()*3);
        
        //If it is the first room, there is garunteed to be a weapon
        if(weaponCount == 0 && row == 1 && column == 1) {
            weaponCount = 1;
        }
        
        //Generate random positions and random weapon type for each weapon.
        for(int i=0; i<weaponCount; i++) {
            int xPos = (int)(Math.random()*(GridToScreenRatio*(columnDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            int yPos = (int)(Math.random()*(GridToScreenRatio*(rowDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            int weaponType = (int)(Math.random()*6);
            
            if(weaponType == 0) {
                items.add(new Sword(xPos,yPos,GridToScreenRatio,GridToScreenRatio));
            } else if(weaponType == 1) {
                items.add(new HeavySword(xPos,yPos,GridToScreenRatio,GridToScreenRatio));
            } else if(weaponType == 2) {
                items.add(new QuickSword(xPos,yPos,GridToScreenRatio,GridToScreenRatio));
            } else if(weaponType == 3) {
                items.add(new Wand(xPos,yPos,GridToScreenRatio,GridToScreenRatio));
            } else if(weaponType == 4) {
                items.add(new HeavyWand(xPos,yPos,GridToScreenRatio,GridToScreenRatio));
            } else if(weaponType == 5) {
                items.add(new QuickWand(xPos,yPos,GridToScreenRatio,GridToScreenRatio));
            }            
        }
        
        //Generate random amount of buffs with random amounts of healing
        int buffCount = (int)(Math.random()*2);
        int healthRestored = (int)(Math.random()*21+10);
        for(int i=0; i<buffCount; i++) {
            int xPos = (int)(Math.random()*(GridToScreenRatio*(columnDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            int yPos = (int)(Math.random()*(GridToScreenRatio*(rowDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            items.add(new Buff(xPos,yPos,GridToScreenRatio,GridToScreenRatio,0,0,healthRestored));
        }
        
        //Generates a random amount of enemies
        int enemyCount = (int)(Math.random()*3+2);
        
        //No enemies in first/spawn room
        if(row == 1 && column == 1) {
            enemyCount = 0;
        }
        
        //Generates random enemy type, position, and stats for each enemy
        for(int i=0; i<enemyCount; i++) {
            int enemyType = (int)(Math.random()*2);
            int xPos = (int)(Math.random()*(GridToScreenRatio*(columnDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            int yPos = (int)(Math.random()*(GridToScreenRatio*(rowDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            int speed = GridToScreenRatio/10;
            double cooldown = Math.random()*0.2+0.9;
            if(enemyType == 0) {
                int health = (int)(Math.random()*51+100);
                int damage = (int)(Math.random()*6+5);
                int range = GridToScreenRatio/20;
                enemies.add(new MeleeEnemy(health,damage,speed,xPos,yPos,GridToScreenRatio,GridToScreenRatio,range,cooldown));
            }
            if(enemyType == 1) {
                int health = (int)(Math.random()*26+25);
                int damage = (int)(Math.random()*6+10);
                int range = GridToScreenRatio*6;
                enemies.add(new RangedEnemy(health,damage,speed,xPos,yPos,GridToScreenRatio,GridToScreenRatio,range,cooldown,GridToScreenRatio/8));
            }
        }
        
        
        //Generates random amount of traps, their coordinates, and their damages in the middle of the map
        int trapCount = (int)(Math.random()*4);
        int spawnRow = (int)(Math.random()*(rowDimension-9)+4);
        int spawnColumn = (int)(Math.random()*(columnDimension-8)+4);
        for(int i=0; i<trapCount; i++) {
            int damage = (int)(Math.random()*41+10);
            
            //While the randomly generated square is occupied by another terrain object, generate another set of random coordinates
            while(terrain[spawnRow][spawnColumn] != null) {
                spawnRow = (int)(Math.random()*(rowDimension-9)+4);
                spawnColumn = (int)(Math.random()*(columnDimension-8)+4);
            }
            terrain[spawnRow][spawnColumn] = new Trap(spawnRow,spawnColumn,damage);
        }
        
        //Generates random amount of walls and their positions in the middle of the map
        int wallCount = (int)(Math.random()*10);
        spawnRow = (int)(Math.random()*(rowDimension-9)+4);
        spawnColumn = (int)(Math.random()*(columnDimension-8)+4);
        for(int i=0; i<wallCount; i++) {
            while(terrain[spawnRow][spawnColumn] != null) {
                spawnRow = (int)(Math.random()*(rowDimension-9)+4);
                spawnColumn = (int)(Math.random()*(columnDimension-8)+4);
            }
            int random = (int)(Math.random() * 10);
            int graphic;
            if (random < 1) {
                graphic = 1;
            } else if (random < 4) {
                graphic = 2;
            } else {
                graphic = 0;
            }
            terrain[spawnRow][spawnColumn] = new Wall(spawnRow,spawnColumn,graphic);
        }
        
        //If room has a key, spawn one in a random location in the room
        if(hasKey) {
            int xPos = (int)(Math.random()*(GridToScreenRatio*(columnDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            int yPos = (int)(Math.random()*(GridToScreenRatio*(rowDimension-4)-GridToScreenRatio*2+1)+GridToScreenRatio*2);
            items.add(new Key(xPos,yPos,GridToScreenRatio,GridToScreenRatio,row,column));
        }
    }
    
    /**
     * getTerrain
     * This is a getter for the private variable terrain.
     * @return A 2d array of type terrain representing a map of all the terrain in the room.
     */
    public Terrain[][] getTerrain () {
        return terrain;
    }
    
    /**
     * getRowDimension
     * This is a getter for the private variable rowDimension.
     * @return An integer representing the row dimension of the room.
     */
    public static int getRowDimension () {
        return rowDimension;
    }
     
    /**
     * getColumnDimension
     * This is a getter for the private variable columnDimension.
     * @return An integer representing the column dimension of the room.
     */
    public static int getColumnDimension () {
        return columnDimension;
    }

    /**
     * getPlayer
     * This is a getter for the private variable player.
     * @return A player representing the player in the room.
     */
    public Player getPlayer() {
        return this.player;
    }
    
    
    /**
     * setPlayer
     * This is a setter for the private variable player.
     * @param A player representing the player in the room.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
     /**
     * getEnemies
     * This is a getter for the private variable enemies.
     * @return An arraylist of type enemy representing the enemies in the room.
     */
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
    
    /**
     * setEnemies
     * This is a setter for the private variable enemies.
     * @param An arraylist of type enemy representing the updated enemies in the room.
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    /**
     * getItems
     * This is a getter for the private variable items.
     * @return An arraylist of type item representing the items in the room.
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }
    
    /**
     * setItems
     * This is a setter for the private variable items.
     * @param An arraylist of type item representing the updated items in the room.
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
     /**
     * getProjectiles
     * This is a getter for the private variable projectiles.
     * @return An arraylist of type Projectile representing the projectiles in the room.
     */
    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }
    
    /**
     * setProjectiles
     * This is a setter for the private variable projectiles.
     * @param An arraylist of type projectile representing the updated projectiles in the room.
     */
    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
    
    
     /**
     * getHasKey
     * This is a getter for the private variable hasKey.
     * @return A boolean that is true if the room has a key inside it.
     */
    public boolean getHasKey() {
        return this.hasKey;
    }
    
     /**
     * setHasKey
     * This is a setter for the private variable hasKey.
     * @param A boolean that is true if the room has a key inside it.
     */
    public void setHasKey(boolean hasKey) {
      this.hasKey = hasKey;
    }
    
     /**
     * getRow
     * This is a getter for the private variable row.
     * @return An integer that is the row coordinate of the room.
     */
    public int getRow() {
        return this.row;
    }
    
     /**
     * getColumn
     * This is a getter for the private variable column.
     * @return An integer that is the column coordinate of the room.
     */
    public int getColumn() {
        return this.column;
    }
}