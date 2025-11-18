import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entidad {
    protected double width, height;
    protected Vector2d pos;
    protected Vector2d vel;
    protected Vector2d acc;

    public Entidad(double x, double y, double width, double height) {
        this.width = width; this.height = height;
        this.pos = new Vector2d(x, y);
        this.vel = new Vector2d(0,0);
        this.acc = new Vector2d(0,0);
    }

    public abstract void update();
    public abstract void draw(GraphicsContext gc);

    public Rectangle2D getBounds() {
        return new Rectangle2D(pos.x, pos.y, width, height);
    }

    // getters
    public double getX() { return pos.x; }
    public double getY() { return pos.y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
