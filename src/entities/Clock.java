package entities;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Clock extends Entity {

    private boolean taken = false;
    public Clock(double x, double y) {
        super(x, y, 20,  20);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(pos.x, pos.y, width, height);
    }
    public void take() {
        taken = true;
    }
    public boolean isTaken() {
        return taken;
    }



}
