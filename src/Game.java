import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        currentLevelIndex = 1;
        currentLevel = new Level1();
        init();
    }

    public Canvas getCanvas() { return canvas; }

    private void init() {
        gameFile = new GameFile("datos/progreso.txt");
        entities = new ArrayList<>();
        platforms = new ArrayList<>();
        items = new ArrayList<>();
        currentLevel.init();
        player = new Player(currentLevel.getXSpawn(), currentLevel.getYSpawn(), 40, 60);
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

        // try to load previous progress
        try {
            GameFile.Progreso p = gameFile.cargar();
            if (p != null) {
                player.setScore(p.puntaje);
            }
        } catch (Exception e) {
            // ignore
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
    }

    public void setupInput(Scene scene) {
    scene.setOnKeyPressed(e -> {
        keys.add(e.getCode());
        if (e.getCode() == KeyCode.SPACE) {
            player.jump();
        }
        if (e.getCode() == KeyCode.Z) {
            if(player.isOnGround())player.startChargingJump();;
            
        }
        if (e.getCode() == KeyCode.S) {
            guardar();
        }
    });
    scene.setOnKeyReleased(e -> {
        keys.remove(e.getCode());
        if (e.getCode() == KeyCode.Z) {
            if(player.isOnGround())player.releaseChargedJump();
        }
    });

    }

    public void start() { loop.start(); }

    private void actualizar(double delta) {
        // input
        if (keys.contains(KeyCode.LEFT)) player.moveLeft();
        if (keys.contains(KeyCode.RIGHT)) player.moveRight();
        if (keys.contains(KeyCode.SPACE)) player.jump();
        //if (e.getCode() == KeyCode.Z) player.startChargingJump();
        for (Platform p:platforms){
           // if(p.isDestroyed()) continue;
            p.update();
        }
        // update entities
        for (Entity en : entities) en.update();
        //Items update
        for (Entity it : items) it.update();
        // gravedad & platforms collision for player
        colisionPorPlataforma();

        // collisions with enemies
        for (Entity en : entities) {
            if (en instanceof Enemy) {
                if (player.getBounds().intersects(en.getBounds())) {
                    player.setAlive(false);
                }
            }
        }

        for(Entity it : items){
            if (it instanceof Door d) {
                if (!d.isOpen()) {
                    if (player.getBounds().intersects(d.getBounds()) && keys.contains(KeyCode.UP)) {
                        if (player.hasKey()) {
                            d.open();
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
                if (player.getBounds().intersects(c.getBounds())) {
                    System.out.println("Reloj tomado");
                    c.take();
                    //Placeholder para el relog 
                }
            }else if (it instanceof Key k) {
                if (player.getBounds().intersects(k.getBounds())) {
                    System.out.println("Llave tomada");
                    k.take();
                    player.takeKey();
                    
                }
            }

        }
        if (levelCompleted) {
            goToNextLevel();
            levelCompleted = false;
        }

        Platform p = player.getActualPlatform();
        if (p == null) return;
        if (!player.isOnGround()) return;
        player.addPosX(p.getVelX());
        player.addPosY(p.getVelY());

            // remove dead or collected items if any (not implemented but placeholder)
        removeDeadEntities();
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
                if ((player.getPreviousBottom() <= p.getY()) &&(player.getBottom() >=p.getY())) {
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
            case 3 -> currentLevel =  new Level3();
            default -> {
                System.out.println("Juego completado");
            }
        }
        currentLevel.init();
        loadLevel(currentLevel);
        player.reset();
        player.respawn(currentLevel.getXSpawn(), currentLevel.getYSpawn());
    }
    private void removeDeadEntities() {
        // Placeholder for removing dead entities if needed
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
        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, width, height);

        // draw platforms
        gc.setFill(Color.SADDLEBROWN);
        for (Platform p : platforms) {
            p.draw(gc);
        }
        //items
        for (Entity it : items) it.draw(gc);


        // draw entities
        for (Entity e : entities) e.draw(gc);

        // HUD
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Puntaje: " + player.getScore(), 20, 30);
        gc.fillText("Presiona 'S' para guardar", 20, 55);

        if (!player.isAlive()) {
            gc.setFill(Color.color(0,0,0,0.6));
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(36));
            gc.fillText("¡Has perdido!", width/2 - 100, height/2);
        }
    }

    private void guardar() {
        try {
            gameFile.guardar(new GameFile.Progreso(player.getScore(), "player"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
