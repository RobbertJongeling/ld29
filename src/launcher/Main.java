/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher;

import entity.Player;
import entity.World;
import entity.block.Block;
import entity.block.Blocktype;
import java.awt.Color;
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
            int delta = getDelta();
            pollInput();

            draw();

            Display.update();
            updateFPS();
            Display.sync(60);
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
                tryMovePlayer(1,1);                
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
        } else {
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
        player = new Player();
        player.setX(100);
        player.setY(100);

        world.addBlock(1, 2, Blocktype.STONE);
        world.addBlock(1, 3, Blocktype.STONE);
        world.addBlock(1, 4, Blocktype.STONE);
        world.addBlock(1, 5, Blocktype.STONE);

        world.addBlock(3, 2, Blocktype.DIRT);
        world.addBlock(3, 3, Blocktype.DIRT);
        world.addBlock(3, 4, Blocktype.DIRT);
        world.addBlock(3, 5, Blocktype.DIRT);
        
        for(int i = 0 ; i < 10 ; i ++){
            for(int j = 6 ; j < 10 ; j ++){
                world.addBlock(i, j, Blocktype.AIR);
            }
        }        
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
        // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (Block block : world.getBlocks()) {
            byte[] c = block.getColor();
            GL11.glColor3ub(c[0], c[1], c[2]);
            
            // draw quad block thing
            GL11.glBegin(GL11.GL_QUADS);
            int w = world.getBlockWidth();
            GL11.glVertex2f(block.getX()*w, block.getY()*w);
            GL11.glVertex2f(block.getX()*w + w, block.getY()*w);
            GL11.glVertex2f(block.getX()*w + w, block.getY()*w + w);
            GL11.glVertex2f(block.getX()*w, block.getY()*w + w);
            GL11.glEnd();
        }
        drawPlayer();
    }

    private void drawPlayer() {
        //orangered, why not?
        GL11.glColor3ub((byte) 255, (byte) 69, (byte) 0);
        GL11.glBegin(GL11.GL_QUADS);
        int x = player.getX();
        int y = player.getY();
        int w = player.getWidth();
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + w, y);
        GL11.glVertex2f(x + w, y + w);
        GL11.glVertex2f(x, y + w);
        GL11.glEnd();

        //draw drill
        switch (player.getDirection()) {
            case IDLE:
                break;
            case LEFT:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x, y);
                GL11.glVertex2f(x, y + w);
                GL11.glVertex2f(x - w / 2, y + w / 2);
                GL11.glEnd();
                break;
            case RIGHT:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x + w, y);
                GL11.glVertex2f(x + w, y + w);
                GL11.glVertex2f(x + w * 1.5f, y + w / 2);
                GL11.glEnd();
                break;
            case UP:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x, y + w);
                GL11.glVertex2f(x + w, y + w);
                GL11.glVertex2f(x + w / 2, y + w * 1.5f);
                GL11.glEnd();
                break;
            case DOWN:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(x, y);
                GL11.glVertex2f(x + w, y);
                GL11.glVertex2f(x + w / 2, y - w / 2);
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
        Point p = world.getPlayerLocationInGrid(x*player.getSpeed(), y*player.getSpeed());
        Block b = world.getBlock(p.getX(),p.getY());
        if(b == null){
            player.move(x, y);
        }else if(b.getBlockType() == Block.Blocktype.AIR){
            player.move(x, y);
        }else{
            System.err.println("boom");
            b.destroy();
        }
    }
}
