package com.company.scene;

import com.company.Global;
import com.company.gametest9th.utils.CommandSolver;

import java.awt.*;
import java.text.DecimalFormat;

public class GameOverScene extends Scene {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private double timePassed;
    private int enemyHit;

    public GameOverScene(double timePassed, int enemyHit){
        this.timePassed = timePassed;
        this.enemyHit = enemyHit;
    }
    @Override
    public void sceneBegin() {

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Game Over", Global.SCREEN_X / 2, Global.SCREEN_Y / 2 -20);
        g.drawString("Total time used: " + String.valueOf(df.format(timePassed)),Global.SCREEN_X / 2, Global.SCREEN_Y / 2);
        g.drawString("You hit " + String.valueOf(enemyHit) + " enemies!", Global.SCREEN_X / 2, Global.SCREEN_Y / 2 + 20);
    }

    @Override
    public void update() {

    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return null;
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }
}
