package levels;

import entities.*;
import platforms.*;

public class Level2 extends Level {

    @Override
    public void init() {
        xSpawn = 60;
        ySpawn = 400;
        platforms.add(new Platform(0, 540, 800, 60));
        AerialPlatform p1 = new AerialPlatform(150, 430, 140, 20);
        p1.setVelX(2);
        p1.setVelY(1);
        p1.setMaxSlide(40);
        platforms.add(p1);
        AerialPlatform p2 = new AerialPlatform(400, 350, 140, 20);
        p2.setVelX(-2);
        p2.setVelY(0.8);
        p2.setMaxSlide(35);
        platforms.add(p2);
        platforms.add(new Platform(600, 260, 120, 20));
        items.add(new Door(50, 100));
        items.add(new Key(450, 320));
        entities.add(new EnemyGround(350, 500, 40, 40, 2.0));
        entities.add(new EnemyFly(500, 180, 40, 40, 1.8));
    }
    
}
