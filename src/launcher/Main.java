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

    private static int HEIGHT = 600;
    private static int WIDTH = 800;

    private Player player;
    private List<Block> world;

    public static void main(String[] args) {
        System.out.println("Whoo!");
        Main m = new Main();
        m.start();
    }

    public void start() {
        initGame();
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        while (!Display.isCloseRequested()) {
            
            pollInput();

            draw();

            Display.update();
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
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            System.out.println("up");
            player.move(0, 1);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            System.out.println("down");
            player.move(0, -1);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            System.out.println("left");
            player.move(-1, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            System.out.println("right");
            player.move(1, 0);
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    System.out.println("A Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    System.out.println("S Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    System.out.println("D Key Pressed");
                }

                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    System.out.println("ruuuun");
                    Display.destroy();
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    System.out.println("A Key Released");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    System.out.println("S Key Released");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    System.out.println("D Key Released");
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
            GL11.glVertex2f(b.getX() + 50, b.getY());
            GL11.glVertex2f(b.getX() + 50, b.getY() + 50);
            GL11.glVertex2f(b.getX(), b.getY() + 50);
            GL11.glEnd();
        }
        drawPlayer();
    }

    private void drawPlayer() {
        //orangered, why not?
        GL11.glColor3ub((byte) 255, (byte) 69, (byte) 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(player.getX(), player.getY());
        GL11.glVertex2f(player.getX() + 50, player.getY());
        GL11.glVertex2f(player.getX() + 50, player.getY() + 50);
        GL11.glVertex2f(player.getX(), player.getY() + 50);
        GL11.glEnd();
    }

}
