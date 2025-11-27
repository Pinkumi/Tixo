package entities;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class EnemyFly extends Enemy {
    private double baseY;
    private Image sprite;
    


    public EnemyFly(double x, double y, double width, double height, double velX) {
        super(x,y,width,height,velX);
        this.baseY = y;
        try { sprite = new Image("file:assets/images/ana.png"); } catch (Exception e) { sprite = null; }
    }

    @Override
    public void update() {
        pos.x += vel.x;
        pos.y = baseY + Math.sin(pos.x * 0.05) * 20;
        pos.add(vel);
        if (Math.abs(pos.x - initialX) > maxSlide) {
            vel.x = -vel.x;
        }
        if (Math.abs(pos.y - initialY) > maxSlide) {
            vel.y = -vel.y;
        }   
    }

    @Override
    public void draw(GraphicsContext gc,double camX, double camY) {
        if (sprite != null) {
            if (vel.x > 0) {
                gc.drawImage(sprite, pos.x-camX, pos.y-camY, width, height);
            } 
            else {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, -(pos.x-camX)-width, pos.y-camY, (width), (height));
                gc.restore();
            }
        }
        else {
            gc.setFill(Color.RED);
            gc.fillRect(pos.x, pos.y, width, height);
        }
    }
}
