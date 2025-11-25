package levels;

import entities.*;
import platforms.*;

public class Level1 extends Level {

    @Override
    public void init() {
        xSpawn = 50;
        ySpawn = 400;
        platforms.add(new Platform(0, 540, 800, 60));
        AerialPlatform pa = new AerialPlatform(200, 420, 120, 20);
        pa.setVelY(1);
        pa.setVelX(1);
        pa.setMaxSlide(30);
        platforms.add(pa);
        platforms.add(new Platform(450, 350, 150, 20));
        items.add(new Door(700, 460));
        items.add(new Key(520, 300));
        entities.add(new EnemyGround(300, 500, 40, 40, 1.5));
        entities.add(new EnemyFly(600, 200, 40, 40, 1.2));
    }
    

}
