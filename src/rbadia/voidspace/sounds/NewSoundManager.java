package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import rbadia.voidspace.main.Level1State;

public class NewSoundManager extends SoundManager{
	
	private static final boolean SOUND_ON = true;
	
    private static AudioClip meatballSound = Applet.newAudioClip(Level1State.class.getResource(
    "/rbadia/voidspace/sounds/Meatball.wav"));
    private static AudioClip toasterSound = Applet.newAudioClip(Level1State.class.getResource(
    "/rbadia/voidspace/sounds/Toasters.wav"));
    private static AudioClip deathExplosionSound = Applet.newAudioClip(Level1State.class.getResource(
    	    "/rbadia/voidspace/sounds/shipExplosion.wav"));
    private static AudioClip noSound = Applet.newAudioClip(Level1State.class.getResource(
    		"/rbadia/voidspace/sounds/NO.wav"));
    
    public static double lengthOfSound() {
    	try {
    	File file = new File("src/rbadia/voidspace/sounds/ShipExplosion.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames + 0.0) / format.getFrameRate();
        return durationInSeconds;
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0.0;
    }
    
    public static void playMeatballSound() {
    	if(SOUND_ON) {
    		new Thread(new Runnable() {
    			public void run() {
    				meatballSound.play();
    			}
    		}).start();
    	}
    }
    
    public static void playToasterSound() {
    	if(SOUND_ON) {
    		new Thread(new Runnable() {
    			public void run() {
    				toasterSound.play();
    			}
    		}).start();
    	}
    }
    
    public static void playDeathExplosionSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				deathExplosionSound.play();
    			}
    		}).start();	
    	}
    }
    
    public static void playNoSound() {
    	if(SOUND_ON) {
    		new Thread (new Runnable() {
    			public void run() {
    				noSound.play();
    			}
    		}).start();
    	}
    }
    
}