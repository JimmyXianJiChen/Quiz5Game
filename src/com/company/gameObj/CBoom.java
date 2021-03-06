package com.company.gameObj;

import com.company.controllers.ImageResourceController;
import com.company.controllers.SceneController;
import com.company.gametest9th.utils.Delay;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class CBoom extends com.company.gameObj.GameObject {
    public enum State {
        NORMAL,
        BOOM,
        REMOVED
    }

    public CBoom(int x, int y, int atk) {
        super(x, y, 50, 50);
        bulletType = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().boom());
        boomType = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().boom2());
        state = State.NORMAL;
        delay = new Delay(30);
        attack = atk;
    }

    private Image bulletType;
    private Image boomType;
    private State state;
    private Delay delay;
    private int attack;

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public void paintComponent(Graphics g) {

        switch (state) {
            case BOOM: {
                g.drawImage(boomType, painter().left(), painter().top(), painter().width(), painter().height(), null);
                break;
            }
            case NORMAL: {
                g.drawImage(bulletType, painter().left(), painter().top(), painter().width(), painter().height(), null);
                break;
            }
            case REMOVED:
            default:{
                break;
            }
        }
    }

    @Override
    public void update() {
        if (state == State.NORMAL) {
            translateY(-8);
        } else if (state == State.BOOM) {
            delay.play();
        }

        if (delay.count()) {
            state = State.REMOVED;
        }
    }

    public int getAttack() {
        return attack;
    }
}
