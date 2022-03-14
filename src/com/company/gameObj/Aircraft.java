package com.company.gameObj;

import com.company.Global;
import com.company.controllers.SceneController;
import com.company.gametest9th.utils.CommandSolver;
import com.company.gametest9th.utils.GameKernel;
import com.company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Aircraft extends GameObject implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {

    public Aircraft(int x, int y) {
        super(x, y, 55, 55, x, y, 50, 50);
        dir = Global.Direction.RIGHT;
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().aircraft());
    }

    private Image img;
    private Global.Direction dir;
    private int blood = 3;
    private int bulletAttack = 1;
    private final int SPEED = 4;

    public void move() {
        if (dir == Global.Direction.RIGHT && !touchRight()) {
            translateX(SPEED);
        } else if (dir == Global.Direction.LEFT && !touchLeft()) {
            translateX(-1 * SPEED);
        }
    }

    public void changeDir(int x) {
        if (painter().left() > x) {
            dir = Global.Direction.LEFT;
        } else {
            dir = Global.Direction.RIGHT;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {
//        move();
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        if (state == CommandSolver.MouseState.MOVED) {
            changeDir(e.getX());
        }
    }

    public void reduceBlood(){
        this.blood--;
    }

    public void setBlood(int blood){
        this.blood = blood;
    }

    public int getBlood(){
        return this.blood;
    }

    public void setBulletAttack(int bulletAttack) {
        this.bulletAttack = bulletAttack;
    }

    public int getBulletAttack() {
        return bulletAttack;
    }

    public boolean isDead(){
        return (this.blood<=0);
    }


    public void getTool(Tool tool){
        setBlood(getBlood()+tool.getBloodEffect());
        setBulletAttack(getBulletAttack()+tool.getBulletEffect());
    }

    public void moveLeft(){
        translateX(-1 * SPEED);
    }

    public void moveRight(){
        translateX(SPEED);
    }

}
