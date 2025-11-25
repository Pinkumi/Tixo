package platforms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tools.*;

public class Platform {
    protected double width, height;
    protected String tipo;
    protected Vector2d vel;
    protected Vector2d pos;
    protected boolean isBroke;

    public Platform(double x, double y, double width, double height) {
        this.width = width; this.height = height;
        this.vel  = new Vector2d(0,0);
        this.pos = new Vector2d(x,y);
        this.tipo = "fija";
        this.isBroke = false;
    }
    public Rectangle2D getBounds() { return new Rectangle2D(pos.x,pos.y,width,height); }
    public void draw(GraphicsContext gc) {
    if(isBroke) gc.setGlobalAlpha(0.5);
    gc.setFill(Color.DARKGRAY);
    gc.fillRect(pos.x, pos.y, width, height);
    gc.setGlobalAlpha(1.0); 
    }
    public void update() {
        //es fija.
    }
    public double getY() { return pos.y; }
    public double getX() { return pos.x; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getVelX() { return vel.x; }
    public double getVelY() { return vel.y; }

    public void startBreaking() {
        //solo es para que se pueda romper la aerea, ya que es una misma instanca de Plataforma
    }

    public void destroy() {
        isBroke = true;
    }
    
    public boolean isDestroyed() {
        return isBroke;
    }
    public String getTipe() {
        return tipo;
    }
    
}
