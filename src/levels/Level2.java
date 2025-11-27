package levels;

import entities.*;
import platforms.*;

public class Level2 extends Level {

    @Override
    public void init() {
        xSpawn = -50;
        ySpawn = 300;
        timeLevel = 50; 

       //1        
        platforms.add(new Platform(-450, 500, 600, 1000));
        // //2
        platforms.add(new Platform(150, 300, 200, 1000));
        //3
        platforms.add(new Platform(-450, -350, 450, 700));
        //4
        AerialPlatform pa = new AerialPlatform(50, -50, 250, 50);
        pa.setVelY(2.5);
        pa.setMaxSlide(300);
        platforms.add(pa);
        //5
        platforms.add(new Platform(350, -450, 200, 800));
        //6
        platforms.add(new Platform(350, 550, 250, 1000));
        // //7
        AerialPlatform pa2 = new AerialPlatform(575, 300, 150, 50);
        pa2.setVelY(1.5);
        pa2.setMaxSlide(300);
        platforms.add(pa2);
        // //8
        AerialPlatform pa3 = new AerialPlatform(750, 100, 150, 50);
        pa3.setVelY(2.5);
        pa3.setMaxSlide(300);
        platforms.add(pa3);
        //  //9
        AerialPlatform pa4 = new AerialPlatform(925, -100, 150, 50);
        pa4.setVelY(3.5);
        pa4.setMaxSlide(300);
        platforms.add(pa4);
       // 10
         platforms.add(new Platform(1175, -650, 900, 700));
        //11
         platforms.add(new Platform(1125, 350, 950, 1000));

        items.add(new Door(1250, 245));
        items.add(new Key(450, 500));
        items.add(new Clock(-250, -410));
        items.add(new Clock(825, 100));
        entities.add(new EnemyGround(1225, 270, 80, 80, 1));
        entities.add(new EnemyFly(300, -550, 80, 80, 1));
    }
    
}
