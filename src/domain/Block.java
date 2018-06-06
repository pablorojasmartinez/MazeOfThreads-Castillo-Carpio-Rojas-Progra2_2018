package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author hansel
 */
public class Block {

    private int x, y, pixelSize;
    private String type;
    private ArrayList<Block> arrayListBlock;
    private int posX,posY;
//    private BufferedImage image;

    public Block(int x, int y, int size, String type) {
//        this.image = image;
        this.x = x;
        this.y = y;
        this.pixelSize = size;
        this.type = type;
        this.posX=0;
   this.posY=0;
   
    }

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

    public String getType() {
        return type;
    }

    public ArrayList<Block> getNext() {
        return arrayListBlock;
    }

    public void setNext(ArrayList<Block> next) {
        this.arrayListBlock = next;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    
    public boolean in(int xMouse, int yMouse) {

        return (((xMouse >= this.x*pixelSize && xMouse < this.x*pixelSize + this.pixelSize)||(xMouse+pixelSize > this.x*pixelSize && 
                xMouse+pixelSize < this.x*pixelSize + this.pixelSize)) && ((yMouse >= this.y*pixelSize && yMouse < this.y*pixelSize + this.pixelSize)
                ||(yMouse+pixelSize >= this.y*pixelSize && yMouse+pixelSize < this.y*pixelSize + this.pixelSize)));
    }
    
    public boolean pressMouse(int xMouse, int yMouse) {
        if ((xMouse >= this.x * this.pixelSize && xMouse <= this.x * this.pixelSize + this.pixelSize)
                && (yMouse >= this.y * this.pixelSize && yMouse <= this.y * this.pixelSize + this.pixelSize)) {
            return true;
        }
        return false;
    }

    public void setType(String type) {
        this.type = type;
    }

}
