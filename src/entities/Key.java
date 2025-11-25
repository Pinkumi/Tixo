package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Key extends Entity {
    
    private boolean taken = false;
    public Key(int x, int y) {
        super(x,y,20,20);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillRect(pos.x, pos.y, width, height);
    }
    public void take() {
        taken = true;
    }
    public boolean isTaken() {
        return taken;
    }


}
