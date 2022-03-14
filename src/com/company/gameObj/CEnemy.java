package com.company.gameObj;

import com.company.Global;
import com.company.controllers.ImageResourceController;
import com.company.controllers.SceneController;
import com.company.gameObj.GameObject;
import com.company.gametest9th.utils.Delay;
import com.company.gametest9th.utils.GameKernel;
import com.company.gametest9th.utils.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CEnemy extends GameObject implements GameKernel.GameInterface {
    private int d;
    private static Image img;
    private int blood;
    public CEnemy(int x, int y, int d, int blood) {
        super(x, y, 50, 50);
        this.d = d;
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().enemy());
        this.blood = blood;
    }

    public boolean move() {
        translateY(4);
        translateX(d);
        return painter().top() <= 600;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }

    public void reduceBlood(int harm){
        this.blood-=harm;
    }

    public boolean isDead(){
        return (this.blood<=0);
    }

    public int getBlood(){
        return this.blood;
    }
}
