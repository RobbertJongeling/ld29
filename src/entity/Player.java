/*
 * The MIT License
 *
 * Copyright 2014 David.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package entity;

/**
 *
 * @author David
 */
public class Player {

    public double gravity = 1.98;
    //not grid based
    private int x = 0;
    private int y = 0;

    private int speed = 2;
    private int width = 40;
    private double fallVelocity;
    private Direction direction = Direction.IDLE;

    public Player() {
        fallVelocity = gravity;
    }

    public int getX() {
        return x;
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }
    public double getFallVelocity()
    {
        return fallVelocity;
    }
    public double getGravity()
    {
        return gravity;
    }

    public void move(int x, int y) {
        this.direction = getPlayerDirection(x, y);

        this.x += x * speed;
        this.y += y * speed;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Convert a given x and y direction to a textual representation of a
     * direction in 9 directions.
     *
     * @param x
     * @param y
     * @return
     */
    public static Direction getPlayerDirection(int x, int y) {
        if (x != 0) {
            if (x > 0) {
                if (y > 0) {
                    return Direction.UPRIGHT;
                } else if (y < 0) {
                    return Direction.DOWNRIGHT;
                } else {
                    return Direction.RIGHT;
                }
            } else {
                if (y > 0) {
                    return Direction.UPLEFT;
                } else if (y < 0) {
                    return Direction.DOWNRIGHT;
                } else {
                    return Direction.LEFT;
                }
            }
        } else if (y != 0 && x == 0) {
            if (y > 0) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else {
            return Direction.IDLE;
        }
    }

    public enum Direction {
        IDLE, UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT
    }

    public void Fall(int limit)
    {
        if(limit != 0)
        {
            this.y -= limit;
            fallVelocity = gravity;
            return;
        }
        
        this.y -= fallVelocity;
        fallVelocity += gravity;
    }
}
