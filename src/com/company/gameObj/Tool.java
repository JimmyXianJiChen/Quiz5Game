package com.company.gameObj;

public abstract class Tool extends GameObject{
    private int bloodEffect = 0;
    private int bulletEffect = 0;
    public Tool(int x, int y, int width, int height, int bloodEffect, int bulletEffect) {
        super(x, y, width, height);
        this.bloodEffect = bloodEffect;
        this.bulletEffect = bulletEffect;
    }

    public int getBloodEffect() {
        return bloodEffect;
    }

    public int getBulletEffect() {
        return bulletEffect;
    }

    public abstract void update();
}
