package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Key extends Entity {
    private Image sprite;
    private boolean taken = false;
    public Key(int x, int y) {
        super(x,y,50,50);
        sprite = new Image("file:assets/images/key.png");
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(GraphicsContext gc,double camX, double camY) {
       // gc.setFill(Color.YELLOW);
        gc.drawImage(sprite, pos.x-camX, pos.y-camY, width+10, height+5);
    }
    public void take() {
        taken = true;
    }
    public boolean isTaken() {
        return taken;
    }


}
