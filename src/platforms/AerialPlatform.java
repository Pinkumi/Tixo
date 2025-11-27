package platforms;
//import herramientas.*;

import javafx.scene.image.Image;

public class AerialPlatform extends Platform {
    private boolean breaking = false;
    private long breakingTime = 0;
    private long reparingTime = 0;
    private int maxSlide = 100;
    private final double initialX;
    private final double initialY;
    
    public AerialPlatform(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.tipo = "aerea";
        this.initialX = x;
        this.initialY = y;
        try { sprite = new Image("file:assets/images/PlatAerea.png"); } catch (Exception e) { sprite = null; }
        try { brokenSprite = new Image("file:assets/images/PlatRota.png"); } catch (Exception e) { brokenSprite = null; }
        
    }
    public void setVelX(double velX) {
        this.vel.x = velX;
    }
    public void setVelY(double velY) {
        this.vel.y = velY;
    }
    @Override
    public void startBreaking() {
        if (!isBroke && !breaking) {
            this.breaking = true;
            this.breakingTime = System.currentTimeMillis();
        }
    }

    public void setMaxSlide(int maxSlide) {
        this.maxSlide = maxSlide;
    }    
    @Override
    public void update() {
        pos.add(vel);
        if (Math.abs(pos.x - initialX) > maxSlide) {
            vel.x = -vel.x;
        }
        if (Math.abs(pos.y - initialY) > maxSlide) {
            vel.y = -vel.y;
        }   
        if (breaking) {
            System.out.println("breaking");
            if (System.currentTimeMillis()-breakingTime>1000) {
                this.destroy(); 
                breaking = false;
                reparingTime = System.currentTimeMillis();
            }
        }
        if (isBroke) {
            if (System.currentTimeMillis()-reparingTime>3000) {
                this.isBroke = false; 

    
            }      
        }
    }

}