/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher;

import entity.Block;
import entity.Blocktype;
import entity.Player;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author David
 */
public class Main {

    private final static int SCREEN_HEIGHT = 600;
    private final static int SCREEN_WIDTH = 800;

    private Player player;
    private List<Block> world;

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
                player.move(1, 1);
            }else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
                player.move(-1,1);
            }else{
                player.move(0, 1);
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                player.move(1, -1);
            }else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
                player.move(-1,-1);
            }else{
                player.move(0, -1);
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            System.out.println("left");
            player.move(-1, 0);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            System.out.println("right");
            player.move(1, 0);
        } else {
            player.move(0, 0);
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
        player = new Player();
        player.setX(100);
        player.setY(100);

        world = new ArrayList<>();

        world.add(new Block(10, 20, Blocktype.STONE));
        world.add(new Block(70, 20, Blocktype.STONE));
        world.add(new Block(70, 20, Blocktype.STONE));
        world.add(new Block(70 + 60, 20, Blocktype.STONE));
        world.add(new Block(70, 20 + 60, Blocktype.GROUND));
        world.add(new Block(10, 20 + 120, Blocktype.GROUND));

        world.add(new Block(100, 500, Blocktype.AIR));
        world.add(new Block(150, 500, Blocktype.AIR));
        world.add(new Block(200, 500, Blocktype.AIR));
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

        for (Block b : world) {
            switch (b.getBlockType()) {
                case AIR:
                    GL11.glColor3ub((byte) 65, (byte) 105, (byte) 225);//blueish
                    break;
                case GROUND:
                    GL11.glColor3ub((byte) 139, (byte) 69, (byte) 19);//brownish
                    break;
                case STONE:
                    GL11.glColor3ub((byte) 119, (byte) 136, (byte) 153);//grayish
                    break;
            }
            // draw quad block thing
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(b.getX(), b.getY());
            GL11.glVertex2f(b.getX() + b.getWidth(), b.getY());
            GL11.glVertex2f(b.getX() + b.getWidth(), b.getY() + b.getWidth());
            GL11.glVertex2f(b.getX(), b.getY() + b.getWidth());
            GL11.glEnd();
        }
        drawPlayer();
    }

    private void drawPlayer() {
        //orangered, why not?
        GL11.glColor3ub((byte) 255, (byte) 69, (byte) 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(player.getX(), player.getY());
        GL11.glVertex2f(player.getX() + player.getWidth(), player.getY());
        GL11.glVertex2f(player.getX() + player.getWidth(), player.getY() + player.getWidth());
        GL11.glVertex2f(player.getX(), player.getY() + player.getWidth());
        GL11.glEnd();

        //draw drill
        switch (player.getDirection()) {
            case IDLE:
                break;
            case LEFT:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(player.getX(), player.getY());
                GL11.glVertex2f(player.getX(), player.getY() + player.getWidth());
                GL11.glVertex2f(player.getX() - player.getWidth() / 2, player.getY() + player.getWidth() / 2);
                GL11.glEnd();
                break;
            case RIGHT:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(player.getX() + player.getWidth(), player.getY());
                GL11.glVertex2f(player.getX() + player.getWidth(), player.getY() + player.getWidth());
                GL11.glVertex2f(player.getX() + player.getWidth() * 1.5f, player.getY() + player.getWidth() / 2);
                GL11.glEnd();
                break;
            case UP:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(player.getX(), player.getY() + player.getWidth());
                GL11.glVertex2f(player.getX() + player.getWidth(), player.getY() + player.getWidth());
                GL11.glVertex2f(player.getX() + player.getWidth() / 2, player.getY() + player.getWidth() * 1.5f);
                GL11.glEnd();
                break;
            case DOWN:
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(player.getX(), player.getY());
                GL11.glVertex2f(player.getX() + player.getWidth(), player.getY());
                GL11.glVertex2f(player.getX() + player.getWidth() / 2, player.getY() - player.getWidth() / 2);
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
}
