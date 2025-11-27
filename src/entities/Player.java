package entities;

import tools.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import platforms.*;

public class Player extends Entity {
    private boolean onGround = false;
    private int score = 0;
    private boolean alive = true;
    private boolean dying = false;  
    private Image sprite;
    private boolean chargingJump = false;
    private double chargePower = 0;
    private final double MAX_CHARGE = 10;
    private boolean lookingRight = true;
    private int jumpImpulse = 0;
    private boolean chargeJumpDirectionRight = true;
    private double Ypas;
    private double XPas;
    private Platform actualPlatform;
    private boolean haveKey;
    private Image[] sprites;
    private int currentSprite = 0;
    private double spriteTimer = 0;
    private int lives = 5;
    public boolean invincible;
    private double invincibleTime = 0;


    public Player(double x, double y, double width, double height) {
        super(x,y,width,height);
        haveKey = false;
        sprites = new Image[13];
        invincible = false;
        Image spriteSheet = new Image("file:assets/images/playerss.png");
        for (int i = 0; i < 13; i++) {
            sprites[i] = new WritableImage(spriteSheet.getPixelReader(), i * 400, 0, 400, 600);
        }
        try {
            sprite = new Image("file:assets/images/player.png");
        } catch (Exception e) { sprite = null; }
        actualPlatform = null;

    }
    public void startChargingJump() {
        if(dying) return;
        if (onGround){
            Sound.playchargejump();
            chargingJump = true;
            chargePower = 0;
        }
    }

    public void releaseChargedJump() {
        if(dying) return;
        if(onGround){
            Sound.playJump();
            chargingJump = false;
            onGround= false;
            vel.y =-(8+ chargePower);
            if(lookingRight)  vel.x += chargePower*1.5;
            else vel.x -= chargePower*1.5;
            chargePower = 0;
        }

    }
    public boolean hasKey() { return haveKey; }
    public void takeKey() { haveKey = true; }

    public void moveLeft() {

        if(dying) return;
        if(chargingJump){lookingRight=false;return;}
        if(onGround) acc.x -= 1; 
        else acc.x -= 0.3;
        lookingRight = false;
     }
    public void moveRight() { 
        if(dying) return;
        if(chargingJump){lookingRight=true;return;}
        if(onGround) acc.x += 1; 
        else acc.x += 0.3;
        lookingRight = true;
    }
    public void jump() {
        if(chargingJump){return;}
        if(dying) return;
        if(onGround) { 
            Sound.playJump();
            vel.y = -11; 
            onGround= false; 
            chargingJump = false;
        } 
    }
    public void applyGravity() {
        if(dying) return;
        acc.y = 0.5;
        //if (pos.y > 1000) { getHit(); }
    }

    public void landOn(Platform p) {
        pos.y = p.getY() - height;
        vel.y = 0;
        onGround= true;
        actualPlatform = p;
    }

    @Override
    public void update() {
        if (alive == false) {
            return;
            
        }
       // System.out.println(chargingJump);
      // System.out.println(onGround);
        Ypas = pos.y;
        XPas = pos.x;
        // if (actualPlatform != null) {
        //     pos.x += actualPlatform.getVelX();
        //     pos.y += actualPlatform.getVelY();
        // }
        applyGravity();
        // if (jumpImpulse > 0) {
        //     if (chargeJumpDirectionRight) acc.x += jumpImpulse;
        //     else acc.x -= jumpImpulse;
        //     jumpImpulse--;
        // }
        vel.add(acc);
        pos.add(vel);
        acc.set(0,0);
        if(onGround
    
        )vel.x *= 0.8;
        else vel.x *= 0.99;
        

        // if (pos.x + width > 800) pos.x = 800 - width;
        // if (pos.x < 0) pos.x = 0;
        if(chargingJump){
            if(chargePower>MAX_CHARGE)chargePower=MAX_CHARGE;
            else chargePower +=0.1;System.out.println(chargePower);
            
        }
        if(invincible){
            invincibleTime -= 0.016;
            if(invincibleTime <= 0){
                invincible=false;
                invincibleTime=0;
            }
        }


        if(dying) {
            vel.x = 0;
            vel.y = 0;
        }
        
        updateAnimation();

    }
    
    public void updateAnimation() {
        double delta = 1/60.0;

        if (dying) {
            spriteTimer += delta;
            if (spriteTimer>0.15) {
                spriteTimer = 0;
                currentSprite++;
                if (currentSprite > 12) {
                    dying = false;
                    alive = false;
                }
            }
            return;
        }
        if (!alive) return;

        if (chargingJump) {
            currentSprite = 3;
            return;
        }
        if (!onGround) {
            currentSprite = 2;
            return;
        }
        if (Math.abs(vel.x) > 0.5) {
            spriteTimer += delta;
            if (spriteTimer > 0.12) {
                spriteTimer = 0;
                currentSprite++;
                if (currentSprite<4 || currentSprite>7) currentSprite=4; 
            }
            return;
        }
        currentSprite = lookingRight?0:1;

    }

    @Override
    public void draw(GraphicsContext gc, double camX, double camY) {
        if (!alive && !dying) return;
        Image frame = sprites[currentSprite];
        if (invincible) {
            if ((int)(invincibleTime * 10) % 2 == 0) {
                return;
            }
            gc.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, 0.5, 0)); 
        }
        if (!lookingRight) {
            gc.save();
            gc.scale(-1, 1);
            gc.drawImage(frame, -((1200/2)-(width/2))-width,((800*0.7)-(height/2)), width+3,height+3);
            gc.restore();
        } else {
            gc.drawImage(frame, ((1200/2) -(width/2)), ((800*0.7) - (height/2)), width+3, height+3);
        }
        gc.setEffect(null);
    }

    public void boundH(int dir, Platform p) {
        if (dir == 1) { 
            pos.x = p.getX() - width; 
        } else if (dir == 2) {
            pos.x = p.getX() + p.getWidth();
        }
        vel.x = -vel.x * 0.9;
        if(dir==1) pos.x -=1;
        else pos.x +=1;
    }
    public void boundV(int dir, Platform p) {//1 arriba, 2 abajo
        if (dir == 1) { 
            pos.y = p.getY() - height; 
        } else if (dir == 2) {
            pos.y = p.getY() + p.getHeight();
        }
        vel.y = -vel.y * 0.8;
        if(dir==1) pos.y -=1;
        else pos.y +=1;
    }
    public int getScore() { return score; }
    public void setScore(int p) { this.score = p; }
    public void addScore(int v) { this.score += v; }
    public void setOnGround(boolean v) { 
        this.onGround = v; 
        if(!v)actualPlatform = null;
    }
    public Platform getActualPlatform() { return actualPlatform; }
    public boolean isOnGround() { return onGround; }
    public void respawn(double x, double y) {
        pos.x = x;
        pos.y = y;
    }
    public void reset(){
        vel.set(0,0);
        acc.set(0,0);
        chargingJump = false;
        actualPlatform = null;
        setOnGround(false);
    }
    public void kill() {
        if (!dying && alive) {
            dying = true;
            Sound.playdeath();
            // alive = true;
            currentSprite = 8;
            spriteTimer = 0;
        }
    }
    public void setAlive(boolean v) { this.alive = v; }
    public boolean isAlive() { return alive; }
    public void setDying(boolean v) { this.dying = v; }
    public void addPosX(double v) { this.pos.x += v; }
    public void addPosY(double v) { this.pos.y += v; }
    public void setVelY(double v) { this.vel.y = v; }
    public double getBottom() { return pos.y + height; }
    public double getPreviousBottom() { return Ypas + height; }
    public double getPreviousTop() { return Ypas; }
    public double getRight() { return pos.x + width; }
    public double getLeft() { return pos.x; }
    public double getPreviousRight() { return XPas + width; }
    public double getPreviousLeft() { return XPas; }
    public double getVelX() { return vel.x; }
    public double getVelY() { return vel.y; }   
    public int getLives(){
        return lives;
    }
    public void setLives(int l){
        this.lives = l;
    }
    public void getHit(){
        if(invincible) return;
        lives -=1;
        if(lives <= 0){
            alive = false;
        }
        invincible = true;
        invincibleTime=1.5;

    }

}
