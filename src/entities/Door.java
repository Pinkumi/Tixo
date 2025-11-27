package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Door extends Entity {
    private boolean isOpen;
    private Image sprite;
    public Door(double x,double y) {
        super(x,y,70,100);
        sprite = new Image("file:assets/images/door.png");
        this.isOpen = false;
    }
    public void open() {
        this.isOpen = true;
    }
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(GraphicsContext gc,double camX, double camY) {
    
        gc.drawImage(sprite, pos.x-camX, pos.y-camY, width+10, height+15);
        
    }
}
