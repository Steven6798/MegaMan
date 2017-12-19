package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.Level1State;

public class NewSoundManager extends SoundManager{
	
	private static final boolean SOUND_ON = true;
	
    private static AudioClip meatballSound = Applet.newAudioClip(Level1State.class.getResource(
    "/rbadia/voidspace/sounds/Meatball.wav"));
    
    public static void playMeatballSound() {
    	if(SOUND_ON) {
    		new Thread(new Runnable() {
    			public void run() {
    				meatballSound.play();
    			}
    		}).start();
    	}
    }
    
}