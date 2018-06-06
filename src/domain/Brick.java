package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author hansel
 */
public class Brick {

    private int x;
    private int y;
    private int pixel;
    private int x1;
    private int x2;
    private String brickType;
    private ArrayList<Brick> brickArrayList;

    public Brick(int x, int y, int size, String brickType) {
        this.x = x;
        this.y = y;
        this.pixel = size;
        this.brickType = brickType;
        this.x1 = 0;
        this.x2 = 0;

    }//constructor

    public int getX() {
        return x;
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

    public int getPixel() {
        return pixel;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    public String getBrickType() {
        return brickType;
    }

    public void setBrickType(String type) {
        this.brickType = type;
    }

    public ArrayList<Brick> getBrickArray() {
        return brickArrayList;
    }

    public void setBrickArray(ArrayList<Brick> brick) {
        this.brickArrayList = brick;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public boolean getMouseValue(int getMouseX, int getMouseY) {
        if ((getMouseY >= this.y * this.pixel && getMouseY <= this.y * this.pixel + this.pixel) && (getMouseX >= this.x * this.pixel && getMouseX <= this.x * this.pixel + this.pixel)) {
            return true;
        }
        return false;
    }

    public boolean onBrick(int xMouse, int yMouse) {
        return (((xMouse >= this.x * pixel && xMouse < this.x * pixel + this.pixel) || (xMouse + pixel > this.x * pixel
                && xMouse + pixel < this.x * pixel + this.pixel)) && ((yMouse >= this.y * pixel && yMouse < this.y * pixel + this.pixel)
                || (yMouse + pixel >= this.y * pixel && yMouse + pixel < this.y * pixel + this.pixel)));
    }

}
