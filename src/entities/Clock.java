package entities;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Clock extends Entity {
    private int extraTime;
    private boolean taken = false;
    private Image sprite;
    public Clock(double x, double y) {
        super(x, y, 50,  50);
        this.extraTime = 5;
        sprite = new Image("file:assets/images/reloj.png");
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsContext gc, double camX, double camY) {
        if(taken) return;
        gc.drawImage(sprite, pos.x-camX, pos.y-camY, width+10, height+10);
    }

    @Override
    public Rectangle2D getBounds() {
        if(taken)return (new Rectangle2D(0,0,0,0));
        return (new Rectangle2D(pos.x, pos.y, width, height));
    }
    public void take() {
        taken = true;
    }
    public void setExtraTime(int extraTime) {
        this.extraTime = extraTime;
    }
    public int getExtraTime() {
        return extraTime;
    }
    public boolean isTaken() {
        return taken;
    }



}
