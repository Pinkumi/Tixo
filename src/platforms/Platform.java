package platforms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.*;

public class Platform {
    protected double width, height;
    protected String tipo;
    protected Vector2d vel;
    protected Vector2d pos;
    protected boolean isBroke;
    protected Image brokenSprite;
    protected  Image sprite;

    public Platform(double x, double y, double width, double height) {
        this.width = width; this.height = height;
        this.vel  = new Vector2d(0,0);
        this.pos = new Vector2d(x,y);
        this.tipo = "fija";
        this.isBroke = false;
        try { sprite = new Image("file:assets/images/plat1.png"); } catch (Exception e) { sprite = null; }
        try { brokenSprite = new Image("file:assets/images/PlatRota.png"); } catch (Exception e) { brokenSprite = null; }
    }
    public Rectangle2D getBounds() { return new Rectangle2D(pos.x,pos.y,width,height); }


    public void draw(GraphicsContext gc, double camX, double camY) {
        Image tex = isBroke ? brokenSprite : sprite;
        if (tex != null) {
            double screenX = pos.x - camX;
            double screenY = pos.y - camY;
            double texW = 50;
            double texH = 50;
            for (double yTile = 0; yTile < height; yTile += texH) {
                for (double xTile = 0; xTile < width; xTile += texW) {
                    gc.drawImage(tex,
                        screenX + xTile,
                        screenY + yTile,
                        texW,
                        texH
                    );
                }
            }
        } else {
            gc.setFill(Color.RED);
            gc.fillRect(pos.x - camX, pos.y - camY, width, height);
        }
    }

    public void setImage(Image sp, Image bsp){
        try { sprite = sp; } catch (Exception e) { sprite = null; }
        try { brokenSprite = bsp; } catch (Exception e) { brokenSprite = null; }
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
