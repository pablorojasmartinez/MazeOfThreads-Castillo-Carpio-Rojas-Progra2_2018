/*
 * To change this license header, choose License Headers onBrick Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template onBrick the editor.
 */
package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author hansel
 */
public class FastCharacter extends Thread {

    private Image image;
    private int x1;
    private int y1;
    private int x;
    private int y;
    private int size;
    private int speed;
    private int direction;
    private int count = 1;
    private int sleep = 900;
    private int auxMovement;
    private boolean flag1 = true;
    private boolean flag2 = true;
    private boolean crash1 = true;
    private boolean crash2 = true;
    private Brick currentBrick;
    private Brick nextBrick;
    String playerName;
    private Item sharedLocation;

    public FastCharacter(int size, Brick brick) throws FileNotFoundException {
//        this.image = new Image(new FileInputStream("src/Assets/pac1.png"));
        x1 = brick.getX();
        y1 = brick.getY();
        x = x1 * size;
        y = y1 * size;
        this.size = size;
        this.currentBrick = brick;
        this.playerName = "";
    }//constructor

    public FastCharacter(Item sharedLocation){
        this.sharedLocation=sharedLocation;
        
    }

    public boolean noWayOut() {
        int movementDirection;
        switch (auxMovement) {
            case 1:
                movementDirection = 3;
                break;
            case 2:
                movementDirection = 4;
                break;
            case 3:
                movementDirection = 1;
                break;
            default:
                movementDirection = 2;
                break;
        }
        int direction;
        if (movementDirection == 1 || movementDirection == 2) {
            direction = 1;
        } else {
            direction = -1;
        }

        if (this.currentBrick.getBrickArray().size() == 1) {
            if ((movementDirection == 2 || movementDirection == 4) && this.currentBrick.getBrickArray().get(0).getX() == x1 + direction) {
                direction = movementDirection;
                return true;
            } else if ((movementDirection == 3 || movementDirection == 1) && this.currentBrick.getBrickArray().get(0).getY() == y1 + direction) {
                direction = movementDirection;
                return true;
            }
        }
        return false;
    }

    public boolean searchDirection(int movementDirection) {
        if (!noWayOut() && ((auxMovement == 3 && movementDirection == 1) || (auxMovement == 1 && movementDirection == 3))) {
            return false;
        } else if (!noWayOut() && ((auxMovement == 4 && movementDirection == 2) || (movementDirection == 4 && auxMovement == 2))) {
            return false;
        }
        int direction;
        if (movementDirection == 1 || movementDirection == 2) {
            direction = 1;
        } else {
            direction = -1;
        }

        if (movementDirection == 1 || movementDirection == 3) {
            for (int i = 0; i < this.currentBrick.getBrickArray().size(); i++) {
                if (this.currentBrick.getBrickArray().get(i).getY() == y1 + direction) {
                    this.nextBrick = this.currentBrick.getBrickArray().get(i);
                    y1 += direction;
                    this.auxMovement = movementDirection;
                    return true;
                }
            }
        } else {
            for (int i = 0; i < this.currentBrick.getBrickArray().size(); i++) {
                if (this.currentBrick.getBrickArray().get(i).getX() == x1 + direction) {
                    this.nextBrick = this.currentBrick.getBrickArray().get(i);
                    x1 += direction;
                    this.auxMovement = movementDirection;
                    return true;

                }
            }
        }

        return false;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (crash2) {
                try {
                    if (count <= 4) {
                        count++;
                    } else if (count > 4) {
                        flag1 = false;
                    }
                    if (flag1) {
                        speed = 10;
                    } else {
                        flag1 = true;
                        count = 1;
                        speed = 5000;
                        sleep = speed;
                    }
                    Thread.sleep(sleep);
                    sleep = 1000;
                } catch (InterruptedException ex) {
                    Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    @Override
    public void run() {
        new Thread(runnable).start();
        while (flag2) {
            if (crash1) {
                direction = (int) (Math.random() * (5 - 1) + 1);
            }
            if (searchDirection(direction)) {
                try {
                    switch (direction) {
                        case 1:
                            while (currentBrick.onBrick(x, y)) {
                                y += 1;
                                this.image = new Image(new FileInputStream("src/Assets/amarilloAbajo.png"));
                                Thread.sleep(speed);
                            }
                        case 2:
                            while (currentBrick.onBrick(x, y)) {
                                x += 1;
                                this.image = new Image(new FileInputStream("src/Assets/amarilloDerecha.png"));
                                Thread.sleep(speed);
                            }
                        case 3:
                            while (currentBrick.onBrick(x, y)) {
                                y -= 1;
                                this.image = new Image(new FileInputStream("src/Assets/amarilloArriba.png"));
                                Thread.sleep(speed);
                            }
                        case 4:
                            while (currentBrick.onBrick(x, y)) {
                                x -= 1;
                                this.image = new Image(new FileInputStream("src/Assets/amarilloIzquierda.png"));
                                Thread.sleep(speed);

                            }

                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(FastCharacter.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
                }
                currentBrick = nextBrick;
            } else {
                crash1 = !crash1;
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String nombre) {
        this.playerName = nombre;
    }

    public void stopCrash() {
        crash2 = false;
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

    public Brick getCurrentBrick() {
        return currentBrick;
    }

    public void setCurrentBrick(Brick currentBrick) {
        this.currentBrick = currentBrick;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isCrash2() {
        return crash2;
    }

    public void setCrash2(boolean crash2) {
        this.crash2 = crash2;
    }

}
