import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Jugador extends Entidad {
    private double velY = 0;
    private double velX = 0;
    private boolean enSuelo = false;
    private int puntaje = 0;
    private boolean vivo = true;
    private Image sprite;
    private boolean chargingJump = false;
    private double chargePower = 0;
    private final double MAX_CHARGE = 15;
    private boolean lookingRight = true;


    public Jugador(double x, double y, double width, double height) {
        super(x,y,width,height);
        try {
            sprite = new Image("file:assets/images/player.png");
        } catch (Exception e) { sprite = null; }
    }
    public void startChargingJump() {
        if (enSuelo) {
            chargingJump = true;
            chargePower = 0;
        }
    }

    public void releaseChargedJump() {
        chargingJump = false;
        velY = -5-chargePower;
        if (lookingRight) velX += chargePower * 2;
        else velX -= chargePower * 2;
        enSuelo = false;
        chargePower = 0;
    }

    public void moverIzquierda() { 
        x -= 5; if (x < 0) x = 0;
        lookingRight = false;
     }
    public void moverDerecha() { 
        x += 5; 
        if (x + width > 800) x = 800 - width; 
        lookingRight = true;
    }
    public void saltar() { if (enSuelo) { velY = -10; enSuelo = false; } }

    public void applyGravity() {
        velY += 0.5;
        y += velY;
        if (y > 1000) { vivo = false; }
    }

    public void landOn(Plataforma p) {
        y = p.getY() - height;
        velY = 0;
        enSuelo = true;
    }

    @Override
    public void update() {
        if(chargingJump){
            chargePower +=0.3;
            if(chargePower>MAX_CHARGE){
                chargePower=MAX_CHARGE;
                System.out.println(chargePower);
            }
        } 
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, x, y, width, height);
        } else {
            gc.setFill(Color.BLUE);
            gc.fillRect(x,y,width,height);
        }
    }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int p) { this.puntaje = p; }
    public void addPuntaje(int v) { this.puntaje += v; }
    public void setEnSuelo(boolean v) { this.enSuelo = v; }
    public boolean isEnSuelo() { return enSuelo; }
    public void setVivo(boolean v) { this.vivo = v; }
    public boolean isVivo() { return vivo; }
    public void setVelY(double v) { this.velY = v; }
}
