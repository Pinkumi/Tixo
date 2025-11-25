package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Door extends Entity {
    private boolean isOpen;
    public Door(double x,double y) {
        super(x,y,50,80);
        
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
        //System.out.println("Door status: " + (isOpen ? "Open" : "Closed"));
    }

    @Override
    public void draw(GraphicsContext gc) {
    
        gc.setFill(Color.BLUE);
        gc.fillRect(pos.x, pos.y, width, height);
        
    }
}
