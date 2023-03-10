package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {
    //this class is to open our music file
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
    }

    public void setFile(int i) {
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception  e) {
        }


    }

    public void play(){
        clip.start();

    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }


}
