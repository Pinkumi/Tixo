package entities;
public abstract class Enemy extends Entity {
    protected int maxSlide = 100;
    protected final double initialX;
    protected final double initialY;
    public Enemy(double x, double y, double width, double height, double velX) {
        super(x,y,width,height);
        initialX = x;
        initialY = y;
        this.vel.x = velX;
    }
    public void setVelX(double velX) {
        this.vel.x = velX;
    }
    public void setVelY(double velY) {
        this.vel.y = velY;
    }
    public void setMaxSlide(int maxSlide) {
        this.maxSlide = maxSlide;
    }   
}

