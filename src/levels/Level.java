package levels;

import entities.Entity;
import java.util.List;
import java.util.ArrayList;
import platforms.*;

public abstract class Level {
    protected int xSpawn = 0;
    protected int ySpawn = 0;
    protected int timeLevel = 60;
    protected List<Platform> platforms = new ArrayList<>();
    protected List<Entity> entities = new ArrayList<>();
    protected List<Entity> items = new ArrayList<>();

    public abstract void init();

    public List<Platform> getPlatforms() {
        return platforms;
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public List<Entity> getItems() {
        return items;
    }
    public int getXSpawn() {
        return xSpawn;
    }
    public int getYSpawn() {
        return ySpawn;
    }
    public int getTimeLevel() {
        return timeLevel;
    }
    public void setTimeLevel(int timeLevel) {
        this.timeLevel = timeLevel;
    }

}
