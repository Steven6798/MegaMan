package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

public class FinalBoss extends Level3State {
	private static final long serialVersionUID = 1L;
	
	protected Meatball meatball;
	protected Boss boss;
	private boolean isBossActive = false;
	
//	private static AudioInputStream bossStream;
//	private static File bossSound = new File("/rbadia/voidspace/sounds/Meatball.wav");
//	private static Clip bossClip;
	private boolean isMeatballActive = true;
	
	public Meatball getMeatball()	{ return meatball; }//Necessary?

	public FinalBoss(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}
	
	@Override
	public void doStart() {
		super.doStart();
		bulletsLeft = new ArrayList<Bullet>();
		bigBulletsLeft = new ArrayList<BigBullet>();
		setNumPlatforms(6);
		setNumBigPlatforms(4);
		setAsteroidsToDestroy(10);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		newMeatball(this);
		newBoss(this);
	}
	
	@Override
	public void doGettingReady() {
		setCurrentState(GETTING_READY);
		getGameLogic().drawGetReady();
		repaint();
		LevelLogic.delay(2000);
		//Changes music from "menu music" to "ingame music"
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/BossBattle.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	};
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		drawBossEnter();
		//drawBossFight();
		//drawMeatball();
		checkMegaManMeatballCollisions();
		checkMegaManBossCollisions();
		checkBulletBossCollisions();
		checkBigBulletBossCollisions();
	}
	
	
	
	
	protected void drawMeatball() {
		Graphics2D g2d = getGraphics2D();

		if((meatball.getX() + meatball.getWidth() >  0)) {
			meatball.translate(-meatball.getSpeed(), 0);
			getNewGraphicsManager().drawMeatball(meatball, g2d, this);
			if(isMeatballActive) {
			NewSoundManager.playMeatballSound();
			this.isMeatballActive = false;
			}
		}
		else {
				meatball.setLocation((int) (this.getWidth() - meatball.getPixelsWide()),
						(rand.nextInt((int) (this.getHeight() - meatball.getPixelsTall() - 32))));
				this.isMeatballActive = true;
		}
	}
	
	@Override
	protected void drawPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumPlatforms(); i++) {
			getNewGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
		}
	}
	
	protected void drawBossEnter() {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawBossEnter(boss, g2d, this);
	}
	
	
	
	
	public Meatball newMeatball(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Meatball.WIDTH - 50);
		int yPos = rand.nextInt((int)(screen.getHeight() - Meatball.HEIGHT - 32));
		meatball = new Meatball(xPos, yPos);
		return meatball;
	}
	
	public Boss newBoss(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Boss.WIDTH);
		int yPos = (int) (screen.getHeight() - Boss.HEIGHT - 32);
		boss = new Boss(xPos, yPos);
		return boss;
	}
	
	
	
	
	
	@Override
	protected void drawAsteroid() {
		//No asteroids wanted;
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i = 0; i < n; i++){
				this.platforms[i] = new Platform(0 , getHeight() / 2 + 140 - i * 40);
		}
		return platforms;
	}
		
	
	
	
	
	
	
	protected void checkBigBulletBossCollisions() {
		while (isBossActive) {
			GameStatus status = getGameStatus();
			for(int i = 0; i < bigBullets.size(); i++) {
				BigBullet bigBullet = bigBullets.get(i);
				if(asteroid.intersects(bigBullet)) {
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
					removeAsteroid(asteroid);
					damage=0;
				}
			}
		}
	}

	protected void checkBulletBossCollisions() {
		while (isBossActive) {
			GameStatus status = getGameStatus();
			for(int i = 0; i < bullets.size(); i++) {
				Bullet bullet = bullets.get(i);
				if(asteroid.intersects(bullet)) {
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
					removeAsteroid(asteroid);
					levelAsteroidsDestroyed++;
					damage=0;
					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}
	}
	
	protected void checkMegaManMeatballCollisions() {
		GameStatus status = getGameStatus();
		if(meatball.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
	}
	
	protected void checkMegaManBossCollisions() {
		GameStatus status = getGameStatus();
		if(boss.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
	}
	
}
