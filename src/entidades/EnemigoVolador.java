package entidades;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class EnemigoVolador extends Enemigo {
    private double baseY;
    private Image sprite;
    


    public EnemigoVolador(double x, double y, double width, double height, double velX) {
        super(x,y,width,height,velX);
        this.baseY = y;
        try { sprite = new Image("file:assets/images/enemy_fly.png"); } catch (Exception e) { sprite = null; }
    }

    @Override
    public void update() {
        pos.x += vel.x;
        pos.y = baseY + Math.sin(pos.x * 0.05) * 20;
        if (pos.x < -50) pos.x = 850;
        if (pos.x > 850) pos.x = -50;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (sprite != null) gc.drawImage(sprite, pos.x, pos.y, width, height);
        else {
            gc.setFill(Color.ORANGE);
            gc.fillOval(pos.x, pos.y, width, height);
        }
    }
}
