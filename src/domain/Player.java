/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Pablo Castillo
 */
public class Player {
    String name;
    CharacterFast []vectFast;
    CharacterFurious []vectFurius;
    CharacterSmart []vectSmarts;

    public Player() {
        this.name="";
        this.vectFast=null;
        this.vectFurius=null;
        this.vectSmarts=null;
    }

    public Player(String name, CharacterFast[] vectFast, CharacterFurious[] vectFurius, CharacterSmart[] vectSmarts) {
        this.name = name;
        this.vectFast = vectFast;
        this.vectFurius = vectFurius;
        this.vectSmarts = vectSmarts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacterFast[] getVectFast() {
        return vectFast;
    }

    public void setVectFast(CharacterFast[] vectFast) {
        this.vectFast = vectFast;
    }

    public CharacterFurious[] getVectFurius() {
        return vectFurius;
    }

    public void setVectFurius(CharacterFurious[] vectFurius) {
        this.vectFurius = vectFurius;
    }

    public CharacterSmart[] getVectSmarts() {
        return vectSmarts;
    }

    public void setVectSmarts(CharacterSmart[] vectSmarts) {
        this.vectSmarts = vectSmarts;
    }
    
}
