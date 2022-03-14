package com.company.gameObj;

import com.company.controllers.SceneController;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class BulletImproveTool extends Tool{
    private final int speed = 5;
    private static final int width = 30;
    private static final int height = 30;
    private Image img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().aircraft());

    public BulletImproveTool(int x, int y) {
        super(x, y, width, height, 0, 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawOval(painter().left(),painter().top(),width,height);
    }


    @Override
    public void update() {
        translateY(speed);
    }
}
