package levels;

import entities.*;
import platforms.*;

public class Level1 extends Level {
    @Override
    public void init() {
        xSpawn = 50;
        ySpawn = 300;
        timeLevel = 40; 

       //1
        platforms.add(new Platform(-450, 500, 600, 300));
        //2
        platforms.add(new Platform(400, 500, 350, 300));
        //3
        platforms.add(new Platform(750, 300, 300, 500));
        //4
        platforms.add(new Platform(450, -400, 150, 800));
        //5
        platforms.add(new Platform(1350, 200, 350, 600));
        //6
        platforms.add(new Platform(1450, -100, 300, 200));
        //7
        platforms.add(new Platform(750, -100, 250, 100));
        //8
         platforms.add(new Platform(800, -450, 100, 150));
         //9
         platforms.add(new Platform(-250, -450, 400, 50));
        AerialPlatform pa = new AerialPlatform(1100, 150, 150, 50);
        pa.setVelY(2.5);
       //pa.setVelX(1);
        pa.setMaxSlide(300);
        platforms.add(pa);

         items.add(new Door(-100, -560));
         items.add(new Key(1580, -200));
         items.add(new Clock(550, 430));
         items.add(new Clock(850, -170));
         entities.add(new EnemyGround(1550, -180, 80, 80, 1));
         entities.add(new EnemyFly(280, -480, 80, 80, 1));
    }
    
}
