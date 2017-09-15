package com.edumobile.edumobiledev.minipong;

/**
 * Created by edumobiledev on 7/10/15.
 */
public class Velocity {
    private float x, y;

    public Velocity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    float getX() {
        return x;
    }

    void setX(float x) {
        this.x = x;
    }

    float getY() {
        return y;
    }

    void setY(float y) {
        this.y = y;
    }
}
