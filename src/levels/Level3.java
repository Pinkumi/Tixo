package levels;

import entities.*;
import platforms.*;

public class Level3 extends Level {

    @Override
    public void init() {
        xSpawn = 80;
        ySpawn = 430;
        platforms.add(new Platform(0, 540, 250, 60));
        platforms.add(new Platform(350, 540, 200, 60));
        platforms.add(new Platform(600, 540, 200, 60));
        platforms.add(new Platform(220, 420, 100, 20));
        platforms.add(new Platform(500, 380, 120, 20));
        platforms.add(new Platform(650, 300, 100, 20));
        AerialPlatform longAir = new AerialPlatform(300, 250, 200, 20);
        longAir.setVelX(1);
        longAir.setVelY(0.4);
        longAir.setMaxSlide(50);
        platforms.add(longAir);
        items.add(new Door(720, 260));
        items.add(new Key(120, 500));
        items.add(new Clock(520, 360));
        entities.add(new EnemyGround(150, 500, 40, 40, 1.3));
        entities.add(new EnemyGround(550, 500, 40, 40, 1.6));
        entities.add(new EnemyFly(400, 200, 40, 40, 2.0));
    }
    
}
