package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.Level1State;

public class NewSoundManager extends SoundManager{
	
	private static final boolean SOUND_ON = true;
	
    private static AudioClip meatballSound = Applet.newAudioClip(Level1State.class.getResource(
    "/rbadia/voidspace/sounds/Meatball.wav"));
    private static AudioClip toasterSound = Applet.newAudioClip(Level1State.class.getResource(
    "/rbadia/voidspace/sounds/Toasters.wav"));
    private static AudioClip deathExplosionSound = Applet.newAudioClip(Level1State.class.getResource(
    	    "/rbadia/voidspace/sounds/shipExplosion.wav"));
    private static AudioClip deathSound = Applet.newAudioClip(Level1State.class.getResource(
    		"/rbadia/voidspace/sounds/DeathYelp.wav"));
    private static AudioClip gotHitSound = Applet.newAudioClip(Level1State.class.getResource(
    		"/rbadia/voidspace/sounds/GotHit.wav"));

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
    
    public static void playDeathSound() {
    	if(SOUND_ON) {
    		new Thread (new Runnable() {
    			public void run() {
    				deathSound.play();
    			}
    		}).start();
    	}
    }
    
    public static void playGotHitSound() {
    	if(SOUND_ON) {
    		new Thread (new Runnable() {
    			public void run() {
    				gotHitSound.play();
    			}
    		}).start();
    	}
    }
}