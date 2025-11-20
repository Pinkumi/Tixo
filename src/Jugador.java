import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Jugador extends Entidad {
    private boolean enSuelo = false;
    private int puntaje = 0;
    private boolean vivo = true;
    private Image sprite;
    private boolean chargingJump = false;
    private double chargePower = 0;
    private final double MAX_CHARGE = 10;
    private boolean lookingRight = true;
    private int jumpImpulse = 0;
    private boolean chargeJumpDirectionRight = true;

    public Jugador(double x, double y, double width, double height) {
        super(x,y,width,height);
        try {
            sprite = new Image("file:assets/images/player.png");
        } catch (Exception e) { sprite = null; }
    }
    public void startChargingJump() {
        if (enSuelo) {
            chargingJump = true;
            if(lookingRight) chargeJumpDirectionRight = true;
            else chargeJumpDirectionRight = false;
            chargePower = 0;
        }
    }

    public void releaseChargedJump() {
        chargingJump = false;
        enSuelo = false;
        vel.y =-(5+ chargePower);
        if(chargeJumpDirectionRight) vel.x += chargePower*1.5;
        else vel.x -= chargePower*1.5;
        chargePower = 0;
    }

    public void moverIzquierda() { 
        acc.x -= 1;
        lookingRight = false;
     }
    public void moverDerecha() { 
        acc.x += 1; 
        lookingRight = true;
    }
    public void saltar() {  if(enSuelo) { vel.y = -11; enSuelo = false; } }

    public void applyGravity() {
        acc.y = 0.5;
        if (pos.y > 1000) { vivo = false; }
    }

    public void landOn(Plataforma p) {
        pos.y = p.getY() - height;
        vel.y = 0;
        enSuelo = true;
    }

    @Override
    public void update() {
        
        applyGravity();
        // if (jumpImpulse > 0) {
        //     if (chargeJumpDirectionRight) acc.x += jumpImpulse;
        //     else acc.x -= jumpImpulse;
        //     jumpImpulse--;
        // }
        vel.add(acc);
        pos.add(vel);
        acc.set(0,0);
        if(enSuelo)vel.x *= 0.8;
        else vel.x *= 0.99;
        

        if (pos.x + width > 800) pos.x = 800 - width;
        if (pos.x < 0) pos.x = 0;
        if(chargingJump){
            chargePower +=0.3;
            if(chargePower>MAX_CHARGE)chargePower=MAX_CHARGE;System.out.println(chargePower);
        } 
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, pos.x, pos.y, width, height);
        } else {
            gc.setFill(Color.BLUE);
            gc.fillRect(pos.x, pos.y, width, height);
        }
    }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int p) { this.puntaje = p; }
    public void addPuntaje(int v) { this.puntaje += v; }
    public void setEnSuelo(boolean v) { this.enSuelo = v; }
    public boolean isEnSuelo() { return enSuelo; }
    public void setVivo(boolean v) { this.vivo = v; }
    public boolean isVivo() { return vivo; }
    public void setVelY(double v) { this.vel.y = v; }
}
