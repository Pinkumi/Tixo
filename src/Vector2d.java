public class Vector2d {

    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void add(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

}