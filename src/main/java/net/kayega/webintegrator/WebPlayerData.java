package net.kayega.webintegrator;

import java.io.Serializable;

public class WebPlayerData implements Serializable {
    public double health;
    public int food;
    public double xp;
    public float level;

    public double getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public double getXp() {
        return xp;
    }

    public float getLevel() {
        return level;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public void setLevel(float level) {
        this.level = level;
    }
}
