public abstract class Enemigo extends Entidad {
    public Enemigo(double x, double y, double width, double height, double velX) {
        super(x,y,width,height);
        this.vel.x = velX;
    }
}

