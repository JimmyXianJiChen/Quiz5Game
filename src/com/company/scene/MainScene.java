package com.company.scene;

import com.company.Global;
import com.company.controllers.SceneController;
import com.company.gameObj.*;
import com.company.gametest9th.utils.CommandSolver;
import com.company.gametest9th.utils.Delay;

import java.awt.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class MainScene extends Scene {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<CEnemy> enemies;
    private Aircraft plane;
    private ArrayList<CBoom> bullets;
    private double timePassed;
    private Image img;
    private int enemyHit;
    private ArrayList<Tool> tools;
    private int enemyNumLimit = 10;
    private double enemyGenerateRate = 0.05;
    private final Delay enemyGenerateDelay = new Delay(300);
    private final Delay enemyBloodImproveDelay = new Delay(5);
    private int enemyBloodMin = 1;
    private int enemyBloodMax = 3;
    private boolean released = false;

    @Override
    public void sceneBegin() {
        enemies = new ArrayList<>();
        plane = new Aircraft(10, 400);
        bullets = new ArrayList<>();
        timePassed = 0;
        enemyHit = 0;
        tools = new ArrayList<>();
        enemyGenerateDelay.loop();
        enemyBloodImproveDelay.loop();
    }

    @Override
    public void sceneEnd() {
//        enemies = null;
//        plane = null;
//        bullets = null;
        SceneController.getInstance().imageController().clear();
    }

    @Override
    public void paint(Graphics g) {
        plane.paint(g);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for(Tool tool : tools){
            tool.paint(g);
        }
        g.setColor(Color.RED);
        for(int i = 0; i<plane.getBlood(); i++){
            g.drawOval(10*(i+1), 10,5,5);
        }
        g.setColor(Color.GREEN);
        g.drawString(String.valueOf(df.format(timePassed)),750, 20);
        g.drawString(String.valueOf(enemyHit), 770,550);
    }

    @Override
    public void update() {
        timePassed += (double)1/(double)Global.UPDATE_TIMES_PER_SEC;
        plane.update();
        if(enemyGenerateDelay.count()){
            enemyGenerateRate = (enemyGenerateRate+0.05>1) ? 1 : enemyGenerateRate+ 0.05;
            enemyNumLimit += 5;
        }

        if (Global.random(1, 100) <= enemyGenerateRate*100 && enemies.size() < enemyNumLimit) {
            enemies.add(new CEnemy(Global.random(0, 600), 0, Global.random(-5, 5),Global.random(enemyBloodMin, enemyBloodMax)));
        }

        if(Global.random(0,14) == 0 && tools.size()<5){
            tools.add(new BulletImproveTool(Global.random(0, 600), 0));
        }

        for(Tool tool : tools){
            tool.update();
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).move()) {
                enemies.remove(i--);
                continue;
            }
            if (plane.isCollision(enemies.get(i))) {
                CEnemy enemy = enemies.get(i);
                plane.reduceBlood();
                CBoom newBullet = new CBoom(plane.painter().left(), plane.painter().top(), plane.getBulletAttack());
                newBullet.setState(CBoom.State.BOOM);
                bullets.add(newBullet);
                enemies.remove(i--);
                if(plane.isDead()){
                    SceneController.getInstance().change(new GameOverScene(timePassed,enemyHit));
                    break;
                }
                continue;
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            CBoom bullet = bullets.get(i);
            bullet.update();
            if (bullet.getState() != CBoom.State.NORMAL) {
                if (bullet.getState() == CBoom.State.REMOVED) {
                    bullets.remove(i--);
                }
                continue;
            }
            if (bullet.touchTop()) {
                bullets.remove(i--);
                continue;
            }
            for (int j = 0; j < enemies.size(); j++) {
                CEnemy enemy = enemies.get(j);
                if (bullet.isCollision(enemy)) {
                    System.out.println("Hit!");
                    System.out.println(enemy.getBlood());
                    System.out.println(bullet.getAttack());
                    bullet.setState(CBoom.State.BOOM);
                    enemy.reduceBlood(bullet.getAttack());
                    if(enemy.isDead()){
                        enemyHit++;
                        enemies.remove(j);
                    }
                    break;
                }
            }
        }

        for(int i=0; i<tools.size(); i++){
            Tool tool = tools.get(i);
            if(tool.touchBottom()){
                tools.remove(i--);
            }
            else if(plane.isCollision(tool)){
                plane.getTool(tool);
                tools.remove(i--);
                if(enemyBloodImproveDelay.count()){
                    enemyBloodMin += 1;
                    enemyBloodMax += 2;
                }
            }
        }
    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
//        return (e, state, trigTime) -> {
//            plane.mouseTrig(e, state, trigTime);
//            if (state == CommandSolver.MouseState.PRESSED) {
//                bullets.add(new CBoom(plane.painter().left(), plane.painter().top(), plane.getBulletAttack()));
//            }
//        };
        return null;
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return new CommandSolver.KeyListener() {

            @Override
            public void keyTyped(char c, long trigTime) {
            }

            @Override
            public void keyPressed(int commandCode, long trigTime) {
                if(commandCode == Global.LEFT && !plane.touchLeft()){
                    plane.moveLeft();
                }
                if(commandCode == Global.RIGHT && !plane.touchRight()){
                    plane.moveRight();
                }
                if(commandCode == Global.COMMAND_SPACE){
                    released = true;
                }

            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                if(commandCode == Global.COMMAND_SPACE){
                    if(released){
                        released = false;
                        bullets.add(new CBoom(plane.painter().left(), plane.painter().top(), plane.getBulletAttack()));
                    }
                }
            }
        };
    }
}
