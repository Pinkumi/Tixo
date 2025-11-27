import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import platforms.*;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import entities.*;
import tools.*;
import levels.*;

public class Game {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int width;
    private final int height;
    private boolean levelCompleted = false;
    private Player player;
    private List<Entity> entities;
    private List<Platform> platforms;
    private Set<KeyCode> keys = new HashSet<>();
    private List<Entity> items;
    private GameFile gameFile;
    private Level currentLevel;
    private int currentLevelIndex;
    private AnimationTimer loop;
    private double levelTime;
    private double levelTimeInitial;
    private double camX;
    private double camY;
    private Image background;
    private boolean gameOver;
    private boolean win;
    private boolean loadData;

    public Game(int width, int height, boolean loadData) {
        this.width = width;
        this.height = height;
        this.loadData = loadData;
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        currentLevelIndex = 1;
        currentLevel = new Level2();
        if (loadData) {
            cargar();
            switch (currentLevelIndex) {
                case 0 -> currentLevel = new Level1();
                case 1 -> currentLevel = new Level2();
                default -> currentLevel = new Level1();
            }

            loadData = false;
        } else {
            currentLevelIndex = 0;
            currentLevel = new Level2();
        }
        init();
    }

    public Canvas getCanvas() { return canvas; }

    private void init() {
        camX = 0;
        camY = 0;
        gameFile = new GameFile("datos/progreso.txt");
        entities = new ArrayList<>();
        platforms = new ArrayList<>();
        items = new ArrayList<>();
        currentLevel.init();
        gameOver = false;
        win = false;
        player = new Player(currentLevel.getXSpawn(), currentLevel.getYSpawn(), 70, 90);
        background = new Image("file:assets/images/Background1.png");

        //player = new Player(50, 450, 40, 60);
        loadLevel(currentLevel);
        loop = new AnimationTimer() {
            private long last = 0;
            @Override
            public void handle(long now) {
                if (last == 0) last = now;
                double delta = (now - last) / 1e9;
                actualizar(delta);
                dibujar();
                last = now;
            }
        };

        if (loadData) {
            try {
                GameFile.Progreso p = gameFile.cargar();
                if (p != null) {
                    player.setScore(p.puntaje);
                    player.setLives(p.nLives);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void loadLevel(Level level) {
        currentLevel = level;
        currentLevel.init();
        entities.clear();
        platforms.clear();
        items.clear();
        entities.add(player);
        entities.addAll(currentLevel.getEntities());
        platforms.addAll(currentLevel.getPlatforms());
        items.addAll(currentLevel.getItems());
        levelTime = currentLevel.getTimeLevel();
        levelTimeInitial = levelTime;
        Sound.playMusic();
    }

    public void setupInput(Scene scene) {
        scene.setOnKeyPressed(e -> {
            boolean alreadyPressed = keys.contains(e.getCode());
            keys.add(e.getCode());

            if (e.getCode() == KeyCode.SPACE) {
                player.jump();
            }

            if (e.getCode() == KeyCode.Z) {
                if (!alreadyPressed && player.isOnGround()) {
                    player.startChargingJump();
                }
            }

            if (e.getCode() == KeyCode.S) {
                guardar();
            }
        });
    scene.setOnKeyReleased(e -> {
        keys.remove(e.getCode());
        if (e.getCode() == KeyCode.Z) {
            player.releaseChargedJump();
            //System.out.println("HOLAAAAA");
        }
    });

    }

    public void start() { loop.start(); }
    public void updateCamera(){
        camX = player.getX()+(player.getWidth()/2) - (width/2);
        camY = player.getY()+(player.getHeight()/2) - (height*0.7);
    }
    private void actualizar(double delta) {
        //tiempo nivel
        if(player.isAlive()) levelTime -= delta;
        if (levelTime <= 0) {
            player.getHit();
                if(player.getLives()<=0){
                    player.kill();
                }
        }
        
        // input
        if (keys.contains(KeyCode.LEFT)) player.moveLeft();
        if (keys.contains(KeyCode.RIGHT)) player.moveRight();
        //if (keys.contains(KeyCode.SPACE)) player.jump();
        //if (e.getCode() == KeyCode.Z) player.startChargingJump();
        for (Platform p:platforms){
           // if(p.isDestroyed()) continue;
            p.update();
        }
        // update entities
        for (Entity en : entities) en.update();
        //Items update
        //for (Entity it : items) it.update();
        // gravedad & platforms collision for player
        colisionPorPlataforma();

        // collisions with enemies
        for (Entity en : entities) {
            if (en instanceof Enemy) {
                if (player.getBounds().intersects(en.getBounds())) {
                    player.getHit();
                    if(player.getLives()<=0){
                        player.kill();
                    }
                }
            }
        }

        for(Entity it : items){
            if (it instanceof Door d) {
                if (!d.isOpen()) {
                    if (player.getBounds().intersects(d.getBounds()) && keys.contains(KeyCode.UP)) {
                        if (player.hasKey() && !d.isOpen()) {
                            d.open();
                            Sound.playdoor();
                            player.addScore(7);
                        }else{
                            System.out.println("Necesitas una llave para abrir la puerta.");
                        }
                    }
                }else{
                    if (player.getBounds().intersects(d.getBounds()) && keys.contains(KeyCode.UP)) {
                        levelCompleted = true;
                        
                    }
                }
            }
            else if (it instanceof Clock c) {
                if (!c.isTaken() && player.getBounds().intersects(c.getBounds())) {
                    System.out.println("Reloj tomado");
                    c.take();
                    levelTime += c.getExtraTime();
                    items.remove(c);
                    player.addScore(2);
                    Sound.playclock();
                    break;
                }                
            }else if (it instanceof Key k) {
                if (player.getBounds().intersects(k.getBounds()) && !k.isTaken()) {
                    System.out.println("Llave tomada");
                    k.take();
                    player.addScore(5);
                    player.takeKey();
                    Sound.playkey();
                    
                }
            }

        }
        if (levelCompleted) {
            goToNextLevel();
            levelCompleted = false;
        }

        System.out.println(player.getY());
        if(player.getY()>1000){
            player.getHit();
            player.respawn(currentLevel.getXSpawn(), currentLevel.getYSpawn());
        }
        removeDeadEntities();
        Platform p = player.getActualPlatform(); 
        if (p == null) return; 
        if (!player.isOnGround()) return; 
        player.addPosX(p.getVelX()); 
        player.addPosY(p.getVelY());
    }

    
    private void colisionPorPlataforma() {
        boolean onPlatform = false;
        for (Platform p : platforms) {
            if (p.isDestroyed()) continue;
            if (player.getVelX()>0) { 
                if (player.getPreviousRight() <= p.getX() && player.getRight() >= p.getX()) {
                    if (player.getBounds().intersects(p.getBounds())) {
                        if(p.getTipe().equals("aerea"))p.startBreaking();
                        player.boundH(1, p);//izq
                    }
                }
            }
            if (player.getVelX()<0) {
                if (player.getPreviousLeft() >=(p.getX()+p.getWidth()) && player.getLeft() <= (p.getX()+p.getWidth())) {
                    if (player.getBounds().intersects(p.getBounds())) {
                        if(p.getTipe().equals("aerea"))p.startBreaking();
                        player.boundH(2, p);//der
                    }
                }
            }

            if (player.getVelY()>0) {
                if ((player.getPreviousBottom() <= p.getY()) &&(player.getBottom() >=p.getY()) || (player.getBottom() <= p.getY()+15 && player.getBottom()>= p.getY())) {
                    if (player.getBounds().intersects(p.getBounds())) {
                        if(p.getTipe().equals("aerea"))p.startBreaking();
                        player.landOn(p);
                        onPlatform = true;
                    }
                }
            }
            if (p.getTipe().equals("fija")) {
                if (player.getVelY()<0) {
                    if ((player.getY() <= p.getY()+p.getHeight()) && (player.getPreviousTop() >= p.getY()+p.getHeight())) {
                        if (player.getBounds().intersects(p.getBounds())) {
                            player.boundV(2, p);
                        }
                    }
                }  
                
            }
        }
        if (!onPlatform) player.setOnGround(false);

    }
    private void goToNextLevel() {
        // Placeholder for level transition logic
        System.out.println("siguiente nivel");
        currentLevelIndex++;
        switch (currentLevelIndex) {
            case 2 -> currentLevel = new Level2();
            default -> {
                System.out.println("Juego completado");
                win = true;
            }
        }
        currentLevel.init();
        loadLevel(currentLevel);
        player.reset();
        player.respawn(currentLevel.getXSpawn(), currentLevel.getYSpawn());
    }
    private void removeDeadEntities() {
        Iterator<Entity> it = items.iterator();
        while (it.hasNext()) {
            Entity item = it.next();
            if (item instanceof Clock c) {
                if (c.isTaken()) {
                    it.remove();
                }
            } else if (item instanceof Key k) {
                if (k.isTaken()) {
                    it.remove();
                }
            }
        }

    }
    private void dibujar() {
        // clear
        updateCamera();


        double bgWidth = background.getWidth();
        double bgHeight = background.getHeight();
        double offsetX = -camX % bgWidth;
        for (int i = 0; i < 3; i++) {
            gc.drawImage(background, (offsetX + i * bgWidth)-550, -400, bgWidth, bgHeight);
        }
        // gc.drawImage(background, -camX-550, -400, background.getWidth(),background.getHeight());
        // gc.setFill(Color.web("#1e1e1e"));
        // gc.fillRect(0, 0, width, height);

        // draw platforms
        gc.setFill(Color.SADDLEBROWN);
        for (Platform p : platforms) {
            p.draw(gc,camX, camY);
        }
        //items
        for (Entity it : items) it.draw(gc, camX, camY);


        // draw entities
        for (Entity e : entities) e.draw(gc, camX, camY);

        // HUD
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(40));
        gc.setTextAlign(TextAlignment.CENTER); 
        gc.setTextBaseline(VPos.CENTER); 
        gc.fillText("Time: " + (int)levelTime, width/2, 40);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Puntaje: " + player.getScore(), 20, 30);
        gc.fillText("Presiona 'S' para guardar", 20, 55);
        if (win) {
            gc.setFill(Color.color(0.2, 1.0, 0.2, 0.9));
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(56));
            gc.fillText("HAS GANADO!", width/2, height/2);   
        }
        else if (levelTime<=0) {
            gc.setFill(Color.color(0,0,0,0.6));
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(36));
            gc.fillText("¡Tiempo agotado!, has perdido :c", width/2, height/2);
            
            if(!gameOver){
                Sound.stopMusic();
                Sound.playGameOver();
            }
            gameOver = true;

            return;
        }
        else if (!player.isAlive() && levelTime>0) {
            gc.setFill(Color.color(0,0,0,0.6));
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(36));
            gc.fillText("¡Has perdido!", width/2, height/2);
            if(!gameOver){
                Sound.stopMusic();
                Sound.playGameOver();
            }
            gameOver = true;
        }
    }

    private void guardar() {
        try {
            gameFile.guardar(new GameFile.Progreso(player.getScore(), "yael",(int)player.getLives(), (int)currentLevelIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargar(){
        try {
            player.setLives(gameFile.cargar().nLives);
            player.setScore(gameFile.cargar().puntaje);
            currentLevelIndex = (gameFile.cargar().idxLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
