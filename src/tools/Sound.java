package tools;

import javafx.scene.media.AudioClip;

public class Sound {

    private static AudioClip jump = new AudioClip("file:assets/sounds/jump.mp3");
    private static AudioClip chargejump = new AudioClip("file:assets/sounds/chargejump.mp3");
    private static AudioClip clock = new AudioClip("file:assets/sounds/clock.mp3");
    private static AudioClip key = new AudioClip("file:assets/sounds/key.mp3");
    private static AudioClip door = new AudioClip("file:assets/sounds/door.mp3");
    private static AudioClip death = new AudioClip("file:assets/sounds/death.mp3");
    private static AudioClip music = new AudioClip("file:assets/sounds/music.mp3");
    private static AudioClip gameOver = new AudioClip("file:assets/sounds/gameOver.mp3");
    private static AudioClip win = new AudioClip("file:assets/sounds/win.mp3");
    public static void playJump() {
        jump.stop();         
        jump.play();
    }
    public static void playchargejump() {
        chargejump.stop();
        chargejump.play();
    }
    public static void playMusic() {
        music.stop();
        music.play();
    }
    public static void playclock() {
        clock.stop();
        clock.play();
    }
    public static void playkey() {
        key.stop();
        key.play();
    }
    public static void playdoor() {
        door.stop();
        door.play();
    }
    public static void playdeath() {
        death.stop();
        death.play();
    }
    public static void stopMusic(){
        music.stop();
    
    }
    public static void playGameOver(){
        gameOver.play();
    }
    public static void playWin(){
        win.stop();
        win.play();
    }
}
