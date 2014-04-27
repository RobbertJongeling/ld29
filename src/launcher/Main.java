/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher;

import entity.Player;
import entity.Player.Direction;
import entity.World;
import entity.block.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

/**
 *
 * @author David
 */
public class Main {

    private final static int SCREEN_HEIGHT = 600;
    private final static int SCREEN_WIDTH = 800;

    private Player player;
    private World world;

    private long lastFrame;
    private int fps;
    private long lastFPS;

    public static void main(String[] args) {
        System.out.println("Whoo!");
        Main m = new Main();
        m.start();
    }

    public void start() {
        initGame();
        try {
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        while (!Display.isCloseRequested()) {
            Update();
        }

        Display.destroy();
    }

    private void pollInput() {
        if (Mouse.isButtonDown(0)) {
            int x = Mouse.getX();
            int y = Mouse.getY();

            System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            System.out.println("SPACE KEY IS DOWN");
        } else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                tryMovePlayer(1, 1);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                tryMovePlayer(-1, 1);
            } else {
                tryMovePlayer(0, 1);
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                tryMovePlayer(1, -1);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                tryMovePlayer(-1, -1);
            } else {
                tryMovePlayer(0, -1);
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            tryMovePlayer(-1, 0);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            tryMovePlayer(1, 0);
        } 
            else {
            tryMovePlayer(0, 0);
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    System.out.println("ruuuun");
                    Display.destroy();
                }
            }
        }
    }

    private void initGame() {
        world = new World();
        world = Worldgenerator.generateWorld();
        player = new Player();
        player.setX(125);
        player.setY(525);
        world.changeBlock(
                (int) Math.floor(((player.getX()+player.getWidth()*.5))/50),
                (int) Math.floor(((player.getY()+player.getWidth()*.5)-1)/50), 
                new AirBlock());
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    private void draw() {
        int widthFit = (SCREEN_WIDTH / world.getBlockWidth()) + 1;
        int heightFit = (SCREEN_HEIGHT / world.getBlockWidth()) + 1;

        int cX = Math.min(widthFit, world.getBlocks().size());
        int cY = Math.min(heightFit, world.getBlocks().get(0).size());//assuming the world is not a jagged grid...
        // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < cX; i++) {
            for (int j = 0; j < cY; j++) {
                Block block = world.getBlocks().get(i).get(j);
                byte[] c = block.getColor();
                GL11.glColor3ub(c[0], c[1], c[2]);

                // draw quad block thing
                GL11.glBegin(GL11.GL_QUADS);
                int w = world.getBlockWidth();
                GL11.glVertex2f(i * w, j * w);
                GL11.glVertex2f(i * w + w, j * w);
                GL11.glVertex2f(i * w + w, j * w + w);
                GL11.glVertex2f(i * w, j * w + w);
                GL11.glEnd();
                
                int damage = block.getDamage();
                
                if(damage > 90 ||damage <0){
                
                }else if (damage > 50){
                }else if (damage > 20){
                
                }
            }
        }
        drawPlayer();
    }

    private void drawPlayer() {
        //orangered, why not?
        GL11.glColor3ub((byte) 255, (byte) 69, (byte) 0);
        GL11.glBegin(GL11.GL_QUADS);
        int x = player.getX();
        int y = player.getY();
        int w = player.getWidth() / 2;
        GL11.glVertex2f(x-w,y-w);
        GL11.glVertex2f(x+w,y-w);
        GL11.glVertex2f(x+w,y+w);
        GL11.glVertex2f(x-w,y+w);
        GL11.glEnd();

        //draw drill
        switch (player.getDirection()) {
            case IDLE:
                break;
            case LEFT:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x + w / 2, y + w / 2);
                GL11.glVertex2f(x + w / 2, y + w * 1.5f);
                GL11.glVertex2f(x, y + w);
                GL11.glEnd();
                break;
            case RIGHT:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x + w * 1.5f, y + w / 2);
                GL11.glVertex2f(x + w * 1.5f, y + w * 1.5f);
                GL11.glVertex2f(x + w * 2, y + w);
                GL11.glEnd();
                break;
            case UP:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x + w / 2, y + w * 1.5f);
                GL11.glVertex2f(x + w * 1.5f, y + w * 1.5f);
                GL11.glVertex2f(x + w, y + w * 2);
                GL11.glEnd();
                break;
            case DOWN:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x + w / 2, y + w / 2);
                GL11.glVertex2f(x + w * 1.5f, y + w / 2);
                GL11.glVertex2f(x + w, y);
                GL11.glEnd();
                break;
        }
    }

    /**
     * Get the time in milliseconds
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    //  delta t in ms
    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    private void tryMovePlayer(int x, int y) {
        Point playerTargetPoint = new Point(
                (int)Math.floor((player.getX()+(x*(player.getSpeed()+player.getWidth()*.5)))/50), 
                (int)Math.floor((player.getY()+(y*(player.getSpeed()+player.getWidth()*.5)))/50));
       
        Block targetBlock = world.getBlock(playerTargetPoint.getX(), playerTargetPoint.getY());

        if (targetBlock == null) {
            player.move(x, y);
        } else if (targetBlock instanceof AirBlock) {
            player.move(x, y);
        } else {
            world.damageBlock(playerTargetPoint.getX(), playerTargetPoint.getY());
        }
    }
    
    private void applyGravity()
    {
        if (player.getDirection() == Direction.UP ||
            player.getDirection() == Direction.UPLEFT ||
            player.getDirection() == Direction.UPRIGHT)
            return;
        
        Block targetBlockMin = world.getBlock(
                (int) Math.floor(player.getX()/50),
                (int) Math.floor(((player.getY()-player.getWidth()*.5)-1)/50));
        
        Block targetBlockMax = world.getBlock(
                (int) Math.floor(player.getX()/50),
                (int) Math.floor(((player.getY()-player.getWidth()*.5)-player.getFallVelocity())/50));

        if((targetBlockMax instanceof AirBlock) && targetBlockMin instanceof AirBlock)
            player.Fall(0);
        else if (targetBlockMin instanceof AirBlock)
            player.Fall(1/*blockje zijn Y +- halve hoogte?*/);
            
    }
    
    private void Update()
    {
        int delta = getDelta();
        pollInput();

        applyGravity();
        
        
        draw();

        Display.update();
        updateFPS();
        Display.sync(60);
    }
}
