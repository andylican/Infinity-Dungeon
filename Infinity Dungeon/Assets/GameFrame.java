/**
 * GameFrame.java
 * Version 1
 * @author Mangat, edited by Andy Li and Bryan Zhang
 * January 20, 2019
 * This class contains the main game window and contains the game loop that updates the display every time and is the class that creates all the objects needed in the game.
 * Our game is essentially a dungeon exploration game where the player has to navigate through a randomly generated map and kill all enemies in order to visited all the rooms and escape. 
 * At the end, there is a boss the player must fight to win the game, and there are also keys which are required to unlock certain rooms.
 */

//Graphics &GUI imports
import javax.imageio.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

//Other imports
import java.util.ArrayList;
import java.awt.Rectangle;
import java.io.File;
import javax.sound.sampled.*;
class GameFrame extends JFrame { 
    
    //static variables
    private static Clip clip;
    static GameAreaPanel gamePanel;
    
    //class variable (non-static)
    private static int GridToScreenRatio;
    private int maxX, maxY;
    private static Map map;
    private static Room room;
    private Player player;
    private double playerMoveTime;
    private double playerAttackTime;
    private double currentTime;
    private double gameStartTime;
    private boolean instructions;
    private boolean deathScreen;
    private boolean boss;
    private boolean victoryScreen;
    
    //Constructor - this runs first
    GameFrame() { 
        
        super("My Game");  
        // Set the frame to full screen 
        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / (room.getRowDimension());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        // this.setUndecorated(true);  //Set to true to remove title bar
        //frame.setResizable(false);
        
        
        
        //Set up the game panel (where we put our graphics)
        gamePanel = new GameAreaPanel();
        this.add(new GameAreaPanel());
        
        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);
        
        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);
        
        this.requestFocusInWindow(); //make sure the frame has focus   
        
        this.setVisible(true);
        
        //Create player, map, and get the first room, and initialize variables
        player = new Player(100, 0, GridToScreenRatio/5,GridToScreenRatio*3, GridToScreenRatio*3, GridToScreenRatio, GridToScreenRatio);
        map = new Map(5,5,player);
        room = map.getRoom(1,1);
        instructions = true;
        deathScreen = false;
        boss = false;
        victoryScreen = false;
        gameStartTime = System.nanoTime()/1000000000.0;
        
        //Start the game loop in a separate thread
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
        
        //Plays the audio file for the game music
        try {
            File audioFile = new File("backgroundMusic.WAV");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();
            clip.addLineListener(new MusicListener());
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    } //End of Constructor
    
     
    /**
     * getGridToScreenRatio
     * This is a getter for the private variable GridToScreenRatio.
     * @return An integer representing the grid to screen ratio (size of every object in the game, excluding the boss).
     */
    public static int getGridToScreenRatio () {
        return GridToScreenRatio;
    }
    
     /**
     * getMap
     * This is a getter for the private variable map.
     * @return A map representing the map of the game.
     */
    public static Map getMap() {
        return GameFrame.map;
    }
    
     /**
     * getRoom
     * This is a getter for the private variable room.
     * @return A room representing the current room the player is in.
     */
    public static Room getRoom() {
        return GameFrame.room;
    }
    
     /**
     * setRoom
     * This is a setter for the private variable room.
     * @param A room representing the current room the player is in.
     */
    public static void setRoom(Room room) {
        GameFrame.room = room;
    }
    
   
    public void animate() { 
        
        while(true){
            
            try{ Thread.sleep(20);} catch (Exception exc){}  //delay
            this.repaint();
        }    
    }
    
    
    /** --------- INNER CLASSES ------------- **/
    
    // Inner class for the the game area - This is where all the drawing of the screen occurs
    private class GameAreaPanel extends JPanel {
        private double startTime = System.nanoTime()/1000000000.0;
        
        //The main gameloop that also repaints the screen
        public void paintComponent(Graphics g) {   
            super.paintComponent(g); //required
            setDoubleBuffered(true); 
            
            //If the insctructions screen needs to be displayed
            if(instructions) {
                g.setColor(Color.BLACK);
                g.fillRect(0,0,32*GridToScreenRatio,18*GridToScreenRatio);
                g.setColor(Color.GRAY);
                g.fillRect(GridToScreenRatio*14,GridToScreenRatio*12,GridToScreenRatio*2,GridToScreenRatio*1);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.PLAIN,30));
                g.drawString("Instructions ",GridToScreenRatio*14,GridToScreenRatio*2);
                g.setFont(new Font("Arial",Font.PLAIN,24));
                g.drawString("You have been banished by your teacher to a dungeon because you failed his computer science course.",GridToScreenRatio*6,GridToScreenRatio*3);
                g.drawString("In order to escape the dungeon, you must visit all 25 rooms and kill all the enemies.",GridToScreenRatio*7,GridToScreenRatio*4);
                g.drawString("You start without a weapon and you must pick up a weapon to kill the enemies.",GridToScreenRatio*8,GridToScreenRatio*5);
                g.drawString("Use WASD to move, and space to use your weapon to attack.",GridToScreenRatio*10,GridToScreenRatio*6);
                g.drawString("You cannot go through any door until you kill all the enemies in your room.",GridToScreenRatio*8,GridToScreenRatio*7);
                g.drawString("Some rooms will have a key lying on the ground, and you must use it to open the locked door in your room.",GridToScreenRatio*6,GridToScreenRatio*8);
                g.drawString("You can only use the key to open locked doors in your room and you cannot pick up multiple keys at once.",GridToScreenRatio*6,GridToScreenRatio*9);
                g.drawString("You have a limited amount of health that can only be restored through health packs lying on the ground. Do not get hit too much!", GridToScreenRatio*4,GridToScreenRatio*10);
                g.drawString("Good luck, and remember to not get lost or else you will have a hard time retracing your steps.",GridToScreenRatio*6,GridToScreenRatio*11);
                g.drawString("Play",(int)(GridToScreenRatio*14.5),(int)(GridToScreenRatio*12.5));
            } else if (deathScreen){
                
                //If the death screen needs to be displayed
                g.setColor(Color.BLACK);
                g.fillRect(0,0,32*GridToScreenRatio,18*GridToScreenRatio);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial",Font.PLAIN,72));
                g.drawString("YOU DIED!",GridToScreenRatio*13,GridToScreenRatio*3);
                g.setFont(new Font("Arial",Font.PLAIN,30));
                g.drawString("Better luck next time...",(int)(GridToScreenRatio*13.5),GridToScreenRatio*6);
                g.setColor(Color.GRAY);
                g.fillRect(GridToScreenRatio*10,GridToScreenRatio*9,GridToScreenRatio*2,GridToScreenRatio*1);
                g.fillRect(GridToScreenRatio*20,GridToScreenRatio*9,GridToScreenRatio*2,GridToScreenRatio*1);
                g.setColor(Color.WHITE);
                g.drawString("Respawn",GridToScreenRatio*10,(int)(GridToScreenRatio*9.5));
                g.drawString("Quit",(int)(GridToScreenRatio*20.5),(int)(GridToScreenRatio*9.5));
            } else if(victoryScreen) {
                
                //If the victory screen needs to be displayed
                g.setColor(Color.BLACK);
                g.fillRect(0,0,32*GridToScreenRatio,18*GridToScreenRatio);
                g.setColor(Color.PINK);
                g.setFont(new Font("Arial",Font.PLAIN,72));
                g.drawString("You have escaped!",GridToScreenRatio*11,GridToScreenRatio*3);
                g.setFont(new Font("Arial",Font.PLAIN,30));
                g.drawString("You have done the impossible, and escaped the infinity dungeon. Now you are free!",(int)(GridToScreenRatio*7.5),GridToScreenRatio*6);
                g.drawString("Come on, hurry up already! You have a computer science assigment due the next day!",GridToScreenRatio*7,GridToScreenRatio*7);
                g.drawString("Don't make your teacher banish you here again!",GridToScreenRatio*11,GridToScreenRatio*8);
                g.setColor(Color.GRAY);
                g.fillRect((int)(GridToScreenRatio*14.5),GridToScreenRatio*10,GridToScreenRatio*2,GridToScreenRatio);
                g.setColor(Color.WHITE);
                g.drawString("Quit",GridToScreenRatio*15,(int)(GridToScreenRatio*10.5));
            } else {
                
                //When the actual game needs to be displayed
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, 32 * GridToScreenRatio, 18 * GridToScreenRatio);
                
                //Declare important variables
                player = map.getPlayer();
                ArrayList<Enemy>enemies = room.getEnemies();
                ArrayList<Item>items = room.getItems();
                ArrayList<Projectile>projectiles = room.getProjectiles();
                Weapon weapon = player.getWeapon();
                currentTime = System.nanoTime()/1000000000;
                
                //If the player has visited all rooms and kiled all enemies, a boss spawns in that room
                if((map.getVisitedCount() == 25) && (enemies.isEmpty())){
                    if(!boss) {
                        player.setHealth(player.getMaxHealth());
                        Boss b = new Boss(1000,30,GridToScreenRatio/7,GridToScreenRatio*3,GridToScreenRatio*3,GridToScreenRatio*2,GridToScreenRatio*2,GridToScreenRatio * 10,5, GridToScreenRatio/5);
                        enemies.add(b);
                        map.setBoss(b);
                        gameStartTime = System.nanoTime()/1000000000;
                        boss = true;
                    }
                }
                
                //If the boss is dead, go to the victory screen
                if((boss) && (map.getBoss().getHealth() <= 0)) {
                    boss = false;
                    victoryScreen = true;
                }
                
              
                //Loop through all items and display their images
                for(int i=0; i<items.size(); i++) {
                    Item item = items.get(i);
                    Image itemImage = Toolkit.getDefaultToolkit().getImage("Misc/sword.png");
                    if (item instanceof Buff) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/buff.png");
                    }
                    if(item instanceof Sword) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/sword.png");
                    }
                    if(item instanceof HeavySword) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/heavySword.png");
                    }
                    if(item instanceof QuickSword) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/quickSword.png");
                    }
                    if(item instanceof Wand) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/wand.png");
                    }
                    if(item instanceof HeavyWand) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/heavyWand.png");
                    }
                    if (item instanceof QuickWand) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/quickWand.png");
                    }
                    if(item instanceof Key) {
                        itemImage = Toolkit.getDefaultToolkit().getImage("Misc/key.png");
                    }
                    g.drawImage(itemImage, item.getXPos(), item.getYPos(), GridToScreenRatio, GridToScreenRatio, this);
                    
                    //If player walks over item
                    if(item.getHitbox().intersects(player.getHitbox())) {
                        
                        //Apply buff to player
                        if(item instanceof Buff) {
                            player.setDamage(player.getDamage()+((Buff)item).getDmgBuff());
                            player.setHealth(Math.min(player.getHealth()+((Buff)item).getHealthBuff(),player.getMaxHealth()));
                            player.setSpeed(player.getSpeed()+((Buff)item).getSpeedBuff());
                            items.remove(i);
                            i--;
                        }
                        
                        //Switch weapons
                        if(item instanceof Weapon && currentTime-player.getWeaponSwitchTime() > 2) {
                            player.setWeaponSwitchTime(currentTime);
                            if(player.getWeapon() != null) {
                                Weapon oldWeapon = player.getWeapon();
                                oldWeapon.setXPos(item.getXPos());
                                oldWeapon.setYPos(item.getYPos());
                                oldWeapon.setHitBox(new Rectangle(item.getXPos(), item.getYPos(), GridToScreenRatio, GridToScreenRatio));
                                items.set(i,oldWeapon);
                            } else {
                                items.remove(i);
                                i--;
                            }
                            player.setWeapon((Weapon)item);   
                        }
                        
                        //Pick up key
                        if(item instanceof Key && player.getKey() == null) {
                            player.setKey((Key)item);
                            items.remove(i);
                            i--;
                        }
                    }
                }
                
                //Loop through projectiles
                for(int i=0; i<projectiles.size(); i++) {
                    Projectile p = projectiles.get(i);
                    Rectangle pBox = p.getHitbox();
                    Image projectileImage = Toolkit.getDefaultToolkit().getImage("Misc/" + p.getType() + "Projectile.png");
                    g.drawImage(projectileImage, (int)pBox.getX(), (int)pBox.getY(),(int)pBox.getWidth(), (int)pBox.getHeight(), this);
                    
                    //If enemy projectile hits player
                    if(p.getType().equals("enemy") && p.characterCollision(player,player)) {
                        projectiles.remove(i);
                        i--;
                    }else if(p.wallCollision(room.getTerrain())) {
                        
                        //If projectile hits wall
                        projectiles.remove(i);
                        i--;
                    } else if(Math.sqrt((p.getXPos()-p.getXStart())*(p.getXPos()-p.getXStart())+(p.getYPos()-p.getYStart())*(p.getYPos()-p.getYStart())) > p.getRange()) {
                        
                        //If projectile reaches its maximum range
                        projectiles.remove(i);
                        i--;
                    } else if(p.getType().equals("player")) {
                        
                        //If projectile is from the player, check if it hits an enemy
                        boolean collided = false;
                        for(Enemy e:enemies) {
                            if(p.characterCollision(e,player)) {
                                projectiles.remove(i);
                                i--;
                                collided = true;
                            }
                        }
                        
                        //If projectile didn't hit the enemy, move it
                        if(!collided) {
                            p.move();
                            projectiles.set(i,p);
                        }
                    } else {
                        
                        //Move the projectile
                        p.move();
                        projectiles.set(i,p);
                    }
                    
                    
                }
                currentTime = System.nanoTime()/1000000000.0;
                
                //If player can move (a key is pressed to set a movement direction and cooldown is satisfied)
                if((!player.getMoveDirection().equals("")) && ((currentTime-player.getMoveTime()) >= 0.05)) {
                    
                    //If player is done attacking (to let attack animation finish before the player can move)
                    if(currentTime-player.getAttackTime() > 0.5) {
                        
                        //Move the player
                        player.setMoveTime(currentTime);
                        player.setPrevXPos(player.getXPos());
                        player.setPrevYPos(player.getYPos());
                        if(player.getMoveDirection().equals("left")) {
                            player.moveLeft();
                        } else if(player.getMoveDirection().equals("right")) {
                            player.moveRight();
                        } else if(player.getMoveDirection().equals("up")) {
                            player.moveUp();
                        } else {
                            player.moveDown();
                        }
                        
                        //Check if player is colliding with an enemy
                        for(Enemy e: enemies) {
                            player.enemyCollision(e);
                        }
                    }
                }
                
                //Loop through all terrain objects
                for (int i = 0; i < room.getRowDimension(); i++) {
                    for (int j = 0; j < room.getColumnDimension(); j++) {
                        Terrain terrain = room.getTerrain()[i][j];
                        
                        //If terrain is wall, display it and check for characters colliding into it
                        if (terrain instanceof Wall) {
                            Image wall = Toolkit.getDefaultToolkit().getImage("Misc/wall" + ((Wall)terrain).getGraphic() + ".png");
                            g.drawImage(wall,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
                            terrain.characterCollision(map.getPlayer());
                            for(Enemy e:enemies) {
                                terrain.characterCollision(e);
                            }
                            
                        }
                        
                        //If terrain is trap, display it and check for characters colliding into it and get rid of it if it damaged a character.
                        if(terrain instanceof Trap) {
                            Image wall = Toolkit.getDefaultToolkit().getImage("Misc/trap.png");
                            g.drawImage(wall,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
                            terrain.characterCollision(map.getPlayer());
                            if(!((Trap)terrain).getIsActive()) {
                                room.getTerrain()[i][j] = null;
                            }
                            for(Enemy e:enemies) {
                                terrain.characterCollision(e);
                                
                            }
                            
                        }
                        
                        //If terrain is door, display it and check for the player colliding into it.
                        if(terrain instanceof Door) {
                            Rectangle doorBox = terrain.getHitbox();
                            g.setColor(Color.BLACK);
                            g.fillRect(GridToScreenRatio * j, GridToScreenRatio * i, GridToScreenRatio, GridToScreenRatio);
                            int[][] walls = map.getWalls();
                            
                            //Check which direction the door leads to
                            if ((walls[((Door)terrain).getRoomRow() + 1][((Door)terrain).getRoomColumn()] == 2) && (((Door)terrain).getDirection().equals("down"))) {
                                Image keyhole = Toolkit.getDefaultToolkit().getImage("Misc/keyhole.png");
                                g.drawImage(keyhole, GridToScreenRatio * j, GridToScreenRatio * i, GridToScreenRatio, GridToScreenRatio, this);
                            }
                            if ((walls[((Door)terrain).getRoomRow() - 1][((Door)terrain).getRoomColumn()] == 2) && (((Door)terrain).getDirection().equals("up"))) {
                                Image keyhole = Toolkit.getDefaultToolkit().getImage("Misc/keyhole.png");
                                g.drawImage(keyhole, GridToScreenRatio * j, GridToScreenRatio * i, GridToScreenRatio, GridToScreenRatio, this);
                            }
                            if ((walls[((Door)terrain).getRoomRow()][((Door)terrain).getRoomColumn() + 1] == 2) && (((Door)terrain).getDirection().equals("right"))) {
                                Image keyhole = Toolkit.getDefaultToolkit().getImage("Misc/keyhole.png");
                                g.drawImage(keyhole, GridToScreenRatio * j, GridToScreenRatio * i, GridToScreenRatio, GridToScreenRatio, this);
                            }
                            if ((walls[((Door)terrain).getRoomRow()][((Door)terrain).getRoomColumn() - 1] == 2) && (((Door)terrain).getDirection().equals("left"))) {
                                Image keyhole = Toolkit.getDefaultToolkit().getImage("Misc/keyhole.png");
                                g.drawImage(keyhole, GridToScreenRatio * j, GridToScreenRatio * i, GridToScreenRatio, GridToScreenRatio, this);
                            }
                            terrain.characterCollision(map.getPlayer());
                            
                        }
                        
                    }
                }
                
                //Generate the player image to animate the player and their weapon and attack animation
                Image playerImage = Toolkit.getDefaultToolkit().getImage("Player Sprite/Sword/playerSword" + 0 + ".png");
                int graphicNumber = 0;
                String weaponType = "";
                if (player.getDirection() == "right") {
                    graphicNumber += 10;
                } else if (player.getDirection() == "up") {
                    graphicNumber += 20;
                } else if (player.getDirection() == "down") {
                    graphicNumber += 30;
                }
                if (player.getAttacking()) {
                    graphicNumber += 40;
                    graphicNumber += player.getGraphic() / 2;
                } else {
                    graphicNumber += player.getGraphic();
                }
                if (weapon instanceof Sword) {
                    weaponType = "Sword";
                } else if (weapon instanceof Wand) {
                    weaponType = "Wand";
                } else {
                    weaponType = "Empty";
                }
                playerImage = Toolkit.getDefaultToolkit().getImage("Player Sprite/player" + weaponType + graphicNumber + ".png");

                g.drawImage(playerImage, player.getXPos(), player.getYPos(), GridToScreenRatio, GridToScreenRatio, this);
                
                
                //Loop through enemies
                for(Enemy e:enemies) {
                    
                    //Display health bars of enemies
                    if (e instanceof Boss) {
                        g.setColor(Color.BLACK);
                        g.fillRect(e.getXPos(), e.getYPos(), GridToScreenRatio * 2, GridToScreenRatio/5);
                        g.setColor(Color.RED);
                        g.fillRect(e.getXPos(), e.getYPos(), (int)(GridToScreenRatio * 2 * (e.getHealth() / (double)e.getMaxHealth())), GridToScreenRatio/5);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(e.getXPos(), e.getYPos(), GridToScreenRatio, GridToScreenRatio/5);
                        g.setColor(Color.RED);
                        g.fillRect(e.getXPos(), e.getYPos(), (int)(GridToScreenRatio * (e.getHealth() / (double)e.getMaxHealth())), GridToScreenRatio/5);
                    }
                    
                    //If it is a melle enemy
                    if (e instanceof MeleeEnemy) {
                        MeleeEnemy me = (MeleeEnemy)e;
                        currentTime = System.nanoTime()/1000000000.0;
                        
                        //Update grpahic number for the appropriate picture
                        graphicNumber = 0;
                        if (me.getDirection().equals("left")) {
                            graphicNumber += 7;
                        } else if (me.getDirection().equals("up")) {
                            graphicNumber += 14;
                        } else if (me.getDirection().equals("down")) {
                            graphicNumber += 21;
                        }
                        if (me.getAttacking()) {
                            me.setGraphic(me.getGraphic() + 1);
                            graphicNumber += me.getGraphic() / 3;
                            if (me.getGraphic() > 20) {
                                me.setGraphic(0);
                                me.setAttacking(false);
                            }
                        }
                        Image enemyImage = Toolkit.getDefaultToolkit().getImage("Enemy/meleeEnemy" + graphicNumber + ".png");
                        g.drawImage(enemyImage, (int)me.getXPos(), (int)me.getYPos(), (int)(me.getWidth()), (int)(me.getHeight()), this);
                        
                        //If enemy is able to move, move and check for collisions
                        if((currentTime-me.getMoveTime() >= 0.05) && !(me.getAttacking())) {
                            me.setMoveTime(currentTime);
                            me.setPrevXPos(me.getXPos());
                            me.setPrevYPos(me.getYPos());
                            me.findPlayer(player);
                            me.characterCollision(player);
                            for(Enemy e2:enemies) {
                                if(e2 != me) {
                                    me.characterCollision(e2);
                                }
                                
                            }
                        }
                        
                        //If enemy is able to attack
                        if(currentTime-me.getAttackTime() >= me.getCooldown()) {
                            if(me.getCanAttack()) {
                                me.setAttacking(true);
                                me.setAttackTime(currentTime);
                                if(me.getDirection().equals("left")) {
                                    me.attackLeft(player);
                                    
                                } else if(me.getDirection().equals("right")) {
                                    me.attackRight(player);
                                    
                                } else if(me.getDirection().equals("up")) {
                                    me.attackUp(player);
                                    
                                } else {
                                    me.attackDown(player); 
                                }
                            }
                        }
                    } else {
                        
                        //If enemy is ranged enemy
                        RangedEnemy re = (RangedEnemy)e;
                        
                        //Update graphic number for appropriate image
                        graphicNumber = 0;
                        if (re.getDirection().equals("left")) {
                            graphicNumber += 7;
                        } else if (re.getDirection().equals("up")) {
                            graphicNumber += 14;
                        } else if (re.getDirection().equals("down")) {
                            graphicNumber += 21;
                        }
                        if (re.getAttacking()) {
                            re.setGraphic(re.getGraphic() + 1);
                            graphicNumber += re.getGraphic() / 3;
                            if (re.getGraphic() > 20) {
                                re.setGraphic(0);
                                re.setAttacking(false);
                            }
                        }
                        Image enemyImage = Toolkit.getDefaultToolkit().getImage("Enemy/rangedEnemy" + graphicNumber + ".png");
                        g.drawImage(enemyImage, (int)re.getXPos(), (int)re.getYPos(), (int)(re.getWidth()), (int)(re.getHeight()), this);
                        currentTime = System.nanoTime()/1000000000.0;
                        
                        //If the enemy can move, move and check for collisions
                        if((currentTime-re.getMoveTime() >= 0.05) && (!(re.getAttacking()))) {
                            re.setMoveTime(currentTime);
                            re.setPrevXPos(re.getXPos());
                            re.setPrevYPos(re.getYPos());
                            re.findPlayer(player);
                            re.characterCollision(player);
                            
                            //Allows boss to attack while moving
                            if (re instanceof Boss) {
                                re.setCanAttack(true);
                            }
                            for(Enemy e2:enemies) {
                                if(e2 != re) {
                                    re.characterCollision(e2);
                                }
                            }
                        }
                        
                        //If enemy can attack;
                        if(currentTime-re.getAttackTime() >= re.getCooldown()) {
                            if(re.getCanAttack()) {
                                re.setAttacking(true);
                                re.setAttackTime(currentTime);
                                
                                //If enemy is boss, use one of the two attack methods randomly
                                if (e instanceof Boss) {
                                    int random = (int)(Math.random() * 2.0);
                                    if (random == 0) {
                                        ((Boss)e).multiAttack(projectiles);
                                    } else {
                                        ((Boss)e).setChainAttack(true);
                                    }
                                } else {
                                    
                                    //The ranged enemy attack
                                    re.attack(player,projectiles);
                                }
                            }
                        }
                        
                        //If the boss is chain attacking, update the projectile count and stop attack if 100 projctiles are shot
                        if ((re instanceof Boss) && ((Boss)re).getChainAttack()) {
                            re.attack(player,projectiles);
                            ((Boss)re).setAttackCount(((Boss)e).getAttackCount() + 1);
                            if (((Boss)re).getAttackCount() > 100) {
                                ((Boss)re).setChainAttack(false);
                                ((Boss)re).setAttackCount(0);
                            }
                        }
                    }
                }
                
                //Position of attack animation
                int attackX = 0;
                int attackY = 0;
                if (weapon instanceof Wand) {
                    weaponType = "wand";
                } else if (weapon instanceof Sword) {
                    weaponType = "sword";
                }
                Image attack = Toolkit.getDefaultToolkit().getImage("Attack/swordAtt" + 0 + ".png");
                
                //If player is attacking, draw the attack animation
                if (player.getAttacking()) {
                    graphicNumber = 0;
                    if (player.getDirection() == "right") {
                        graphicNumber += 10;
                        attackX = player.getXPos() + player.getWidth();
                        attackY = player.getYPos();
                    } else if (player.getDirection() == "up") {
                        graphicNumber += 20;
                        attackX = player.getXPos();
                        attackY = player.getYPos() - player.getWidth();
                    } else if (player.getDirection() == "down") {
                        graphicNumber += 30;
                        attackX = player.getXPos();
                        attackY = player.getYPos() + player.getWidth();
                    } else {
                        attackX = player.getXPos() - player.getWidth();
                        attackY = player.getYPos();
                    }
                    graphicNumber += player.getGraphic() / 2;
                    attack = Toolkit.getDefaultToolkit().getImage("Attack/" + weaponType + "Att" + graphicNumber + ".png");
                    if (player.getGraphic() > 16) {
                        player.setGraphic(0);
                        player.setAttacking(false);
                    }
                    player.setGraphic(player.getGraphic() + 1);
                }
                g.drawImage(attack, attackX, attackY, GridToScreenRatio, GridToScreenRatio, this);
                
                currentTime = System.nanoTime()/1000000000.0;
                
                //If the player is attacking, perform the attack 
                if((player.getAttacking()) && (currentTime-player.getAttackTime() >= weapon.getCooldown()) && (player.getGraphic() > 6)) {
                    player.setAttackTime(currentTime);
                    if(weapon instanceof Sword) {
                        Sword sword = (Sword)weapon;
                        if(player.getDirection().equals("left")) {
                            sword.attackLeft(player,enemies);
                        } else if(player.getDirection().equals("right")) {
                            sword.attackRight(player,enemies);
                        } else if(player.getDirection().equals("up")) {
                            sword.attackUp(player,enemies);
                        } else {
                            sword.attackDown(player,enemies);
                        }
                        
                    } else if(weapon instanceof Wand) {
                        Wand wand = (Wand)weapon;
                        if(player.getDirection().equals("left")) {
                            wand.attackLeft(player,projectiles);
                        } else if(player.getDirection().equals("right")) {
                            wand.attackRight(player,projectiles);
                        } else if(player.getDirection().equals("up")) {
                            wand.attackUp(player,projectiles);
                        } else {
                            wand.attackDown(player,projectiles);
                        }
                    }
                }
                
                //Display player health bar
                g.setColor(Color.BLACK);
                g.fillRect(GridToScreenRatio * 6, GridToScreenRatio * 31/2, GridToScreenRatio*8, GridToScreenRatio*3/2);
                g.setColor(Color.RED);
                g.fillRect(GridToScreenRatio * 6, GridToScreenRatio * 31/2, (int)(GridToScreenRatio*8 * (player.getHealth() / (double)player.getMaxHealth())), GridToScreenRatio*3/2);
                
                //Display rooms visited
                g.setColor(Color.GRAY);
                g.fillRect(0,0,GridToScreenRatio*2,GridToScreenRatio);
                g.setColor(Color.WHITE);
                g.drawString("Rooms Visited: "+map.getVisitedCount()+"/"+((map.getRows()-1)/2*(map.getColumns()-1))/2,10,40);
                
                //Display the weapon and key the player currently has equipped
                Image itemBox = Toolkit.getDefaultToolkit().getImage("Misc/itemBox.png");
                g.drawImage(itemBox, GridToScreenRatio * 2, GridToScreenRatio * 31/2, GridToScreenRatio * 3/2, GridToScreenRatio * 3/2, this);
                g.drawImage(itemBox, GridToScreenRatio * 4, GridToScreenRatio * 31/2, GridToScreenRatio * 3/2, GridToScreenRatio * 3/2, this);
                if (player.getKey() != null) {
                    Image keyImage = Toolkit.getDefaultToolkit().getImage("Misc/key.png");
                    g.drawImage(keyImage, GridToScreenRatio * 2, GridToScreenRatio * 31/2, GridToScreenRatio * 3/2, GridToScreenRatio * 3/2, this);
                }
                if (weapon != null) {
                    Image weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/sword.png");
                    if(weapon instanceof Sword) {
                        weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/sword.png");
                    }
                    if(weapon instanceof HeavySword) {
                        weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/heavySword.png");
                    }
                    if(weapon instanceof QuickSword) {
                        weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/quickSword.png");
                    }
                    if(weapon instanceof Wand) {
                        weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/wand.png");
                    }
                    if(weapon instanceof HeavyWand) {
                        weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/heavyWand.png");
                    }
                    if (weapon instanceof QuickWand) {
                        weaponImage = Toolkit.getDefaultToolkit().getImage("Misc/quickWand.png");
                    }
                    g.drawImage(weaponImage, GridToScreenRatio * 4, GridToScreenRatio * 31/2, GridToScreenRatio * 3/2, GridToScreenRatio * 3/2, this);
                }
                
                
                //If player health below 0, display the death screen
                if(player.getHealth() <= 0) {
                    deathScreen = true;
                    player = null;
                    room.setProjectiles(new ArrayList<Projectile>());
                }
                
                //If an enemy has health below 0, remove it from the arraylist
                for(int i=0; i<enemies.size(); i++) {
                    if(enemies.get(i).getHealth() <=0) {
                        enemies.remove(i);
                        i--;
                    }
                }
                
            }
        }
    }
    
    // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
    private class MyKeyListener implements KeyListener {
        
        public void keyTyped(KeyEvent e) {  
            
        }   
        
        
        public void keyPressed(KeyEvent e) {
            
            
            Player player = GameFrame.getMap().getPlayer();
            Weapon weapon = player.getWeapon();
            
            //Sets the moveDirection and the direction of the player when WASD keys are pressed. This is to prevent the delay from holding down keys which would happen if the movement was directly called here.
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
                player.setDirection("up");
                player.setMoveDirection("up");
            } 
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  
                player.setDirection("left");
                player.setMoveDirection("left");
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  
                player.setDirection("down");
                player.setMoveDirection("down");
            } 
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  
                player.setDirection("right");
                player.setMoveDirection("right");
            } 
            
            //If the player has a weapon and they are able to attack (satsify weapon cooldown)
            if ((weapon != null) && (e.getKeyCode() == KeyEvent.VK_SPACE) && !(player.getAttacking()) && (currentTime-player.getAttackTime() >= weapon.getCooldown())) { 
                if(player.getWeapon() != null) {
                    player.setAttacking(true);
                    player.setGraphic(0);
                }
            }
            
            //Close game is esc is pressed
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            
            
            
            
        }
        
        public void keyReleased(KeyEvent e) {
            Player player = GameFrame.getMap().getPlayer();
            
            //Gets rid of the movement direction when a key is released, assuming the current movement directinon correspands to teh key that is released
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
                if(player.getMoveDirection().equals("up")) {
                    player.setMoveDirection("");
                    player.setGraphic(0);
                }
            } 
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  
                if(player.getMoveDirection().equals("left")) {
                    player.setMoveDirection("");
                    player.setGraphic(0);
                }
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  
                if(player.getMoveDirection().equals("down")) {
                    player.setMoveDirection("");
                    player.setGraphic(0);
                }
            } 
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  
                if(player.getMoveDirection().equals("right")) {
                    player.setMoveDirection("");
                    player.setGraphic(0);
                }
            } 
        }
    } //end of keyboard listener
    
    // -----------  Inner class for the mouse listener - This detects mouse movement & clicks and runs the corresponding methods 
    private class MyMouseListener implements MouseListener {
        
        public void mouseClicked(MouseEvent e) {
            int xPos = e.getX();
            int yPos = e.getY();
            
            //If the mouse is clicked and the location is on one of the buttons on the intstructions, death, or victory screen, perform the corresponding action. Also, the top border is around 35 pixels thick
            //and the side border is around 5 pixels thick. Those numbers were added to the positions as the mouse click measurements include those borders.
            if(instructions && xPos >= GridToScreenRatio*14+5 && xPos <= GridToScreenRatio*16+5 && yPos >= GridToScreenRatio*12+35 && yPos <= GridToScreenRatio*13+35) {
                instructions = false;
            } else if(deathScreen) {
                if(xPos >= GridToScreenRatio*10+5 && xPos <= GridToScreenRatio*12+5 && yPos >= GridToScreenRatio*9+35 && yPos <= GridToScreenRatio*10+35) {
                    
                    //If respawn button is clicked, set room the the starting room and recreate the player with full health and no items equipped
                    deathScreen = false;
                    room = map.getRoom(1,1);
                    map.setPlayer(new Player(100, 0, GridToScreenRatio/5,GridToScreenRatio*3, GridToScreenRatio*3, GridToScreenRatio, GridToScreenRatio));
                    
                } else if(xPos >= GridToScreenRatio*20+5 && xPos <= GridToScreenRatio*22+5 && yPos >= GridToScreenRatio*9+35 && yPos <= GridToScreenRatio*10+35) {
                    
                    //If quit button is pressed
                    System.exit(0);
                }
            } else if(victoryScreen) {
                
                //If quit button is pressed
                if(xPos >= (int)(GridToScreenRatio*14.5+5) && xPos <= GridToScreenRatio*17+5 && yPos >= GridToScreenRatio*10+35 && yPos <= GridToScreenRatio*11+35) {
                    System.exit(0);
                }
            }
        }
        
        public void mousePressed(MouseEvent e) {
        }
        
        public void mouseReleased(MouseEvent e) {
        }
        
        public void mouseEntered(MouseEvent e) {
        }
        
        public void mouseExited(MouseEvent e) {
        }
    } //end of mouselistener
    
    // -----------  Inner class for the Music listener - This detects if music file has ended and runs corresponding methods
    static class MusicListener implements LineListener {
        
        public void update(LineEvent event) {
            
            //If music has stopped, close the line, access and play the audio file and add another listener
            if (event.getType() == LineEvent.Type.STOP) {
                event.getLine().close(); 
                try {
                    File audioFile = new File("backgroundMusic.WAV");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    clip.open(audioStream);
                    clip.start();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}