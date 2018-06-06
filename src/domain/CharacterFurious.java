/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Pablo Castillo
 */
public class CharacterFurious extends Thread {
     private Image image;
    ;

    
     private int cont = 1;
    private int sleep = 1000;
    private boolean state = true;
    protected int xPos, yPos, x, y, size, speed;
    protected Block currentBlock, nextBlock;
    protected int direction, dirAux;
    protected boolean crash = true;
        protected boolean crash2 = true;
String nombre;
    public CharacterFurious(int size, Block start) throws FileNotFoundException {
//        this.image = new Image(new FileInputStream("src/Assets/pac1.png"));
        xPos = start.getX();
        yPos = start.getY();
        x = xPos * size;
        y = yPos * size;
        this.size = size;
        this.currentBlock = start;
this.nombre="";
    }

    Boolean flag = true;

    public boolean isCrash2() {
        return crash2;
    }

    public void setCrash2(boolean crash2) {
        this.crash2 = crash2;
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

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
    }

//    }
   
    public boolean next(int dir) {

        if (((dir == 1 && dirAux == 3) || (dirAux == 1 && dir == 3)) && !encerrado()) {
            return false;
        } else if (((dir == 2 && dirAux == 4) || (dirAux == 2 && dir == 4)) && !encerrado()) {
            return false;
        }
        int aux;
        if (dir == 1 || dir == 2) {
            aux = 1;
        } else {
            aux = -1;
        }

        if (dir == 1 || dir == 3) {
            for (int i = 0; i < this.currentBlock.getNext().size(); i++) {
                if (this.currentBlock.getNext().get(i).getY() == yPos + aux) {
                    this.nextBlock = this.currentBlock.getNext().get(i);
                    yPos += aux;
                    this.dirAux = dir;
                    return true;

                }
            }
        } else {
            for (int i = 0; i < this.currentBlock.getNext().size(); i++) {
                if (this.currentBlock.getNext().get(i).getX() == xPos + aux) {

                    this.nextBlock = this.currentBlock.getNext().get(i);
                    xPos += aux;
                    this.dirAux = dir;
                    return true;

                }
            }
        }

        return false;
    }

    public boolean encerrado() {
        int dir;
        switch (dirAux) {
            case 1:
                dir = 3;
                break;
            case 2:
                dir = 4;
                break;
            case 3:
                dir = 1;
                break;
            default:
                dir = 2;
                break;
        }
        int aux;
        if (dir == 1 || dir == 2) {
            aux = 1;
        } else {
            aux = -1;
        }

        if (this.currentBlock.getNext().size() == 1) {
            if ((dir == 1 || dir == 3) && this.currentBlock.getNext().get(0).getY() == yPos + aux) {
                direction = dir;
                return true;
            } else if ((dir == 2 || dir == 4) && this.currentBlock.getNext().get(0).getX() == xPos + aux) {
                direction = dir;
                return true;
            }
        }
        return false;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            while (crash2) {

                try {
                    if (cont <= 2) {
                        cont++;

                    } else if (cont > 2) {
                        state = false;
                    }

                    if (state) {
                        speed = 40;
                    } 
                       
                    System.err.println("Cont:" + cont + " Sleep:" + sleep + "State:" + state);

                    Thread.sleep(sleep);
                    sleep = 1000;
                } catch (InterruptedException ex) {
                    Logger.getLogger(CharacterFast.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    };

    public void run() {
        new Thread(timer).start();
        while (flag) {
            if (crash) {
                direction = (int) (Math.random() * (5 - 1) + 1);
            }
            if (next(direction)) {
                try {
                    switch (direction) {
                        case 1:
                            while (currentBlock.in(x, y)) {
                                y += 1;
                                       this.image = new Image(new FileInputStream("src/Assets/pacabajo.png"));
                                Thread.sleep(speed);
                            }
                        case 2:
                            while (currentBlock.in(x, y)) {
                                x += 1;
                                       this.image = new Image(new FileInputStream("src/Assets/pacDerecha.png"));
                                Thread.sleep(speed);
                            }
                        case 3:
                            while (currentBlock.in(x, y)) {
                                y -= 1;
                                       this.image = new Image(new FileInputStream("src/Assets/pacArriba.png"));
                                Thread.sleep(speed);
                            }
                        case 4:
                            while (currentBlock.in(x, y)) {
                                x -= 1;
                                       this.image = new Image(new FileInputStream("src/Assets/pacIzquierda.png"));
                                Thread.sleep(speed);

                            }

                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(CharacterFast.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CharacterFast.class.getName()).log(Level.SEVERE, null, ex);
                }
                currentBlock = nextBlock;
            } else {
                crash = !crash;
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

        public void parar()  {
   crash2=false;
}
    
}
