package entities;
public abstract class Enemy extends Entity {
    public Enemy(double x, double y, double width, double height, double velX) {
        super(x,y,width,height);
        this.vel.x = velX;
    }
}

