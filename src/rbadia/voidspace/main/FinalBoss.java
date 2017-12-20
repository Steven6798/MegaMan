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
	protected Boss bossE;
	protected Boss bossF;
	private boolean isBossActive = false;
	private boolean meatballDidDamage = false;
	private int hitCounter = 0;
	
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
		setAsteroidsToDestroy(50);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		newMeatball(this);
		newBossEnter(this);
		newBossFight(this);
		NewSoundManager.playToasterSound();
	}
	
	@Override
	public void doGettingReady() {
		setDestroyText("This is it, Luigi...");
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
//		if (!isBossActive)
//			drawBossEnter();
//		else
//			drawBossFight();
		drawMeatball();
		checkMegaManMeatballCollisions();
//		if(isBossActive)
//			checkMegaManBossFCollisions();
//		else
//			checkMegaManBossECollisions();
		checkBulletBossCollisions();
		checkBigBulletBossCollisions();
	}
	
	
	
	
	protected void drawMeatball() {
		if (!isBossActive) {
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
				meatball.setLocation((int) (this.getWidth() - meatball.getPixelsWide() - 100), //==========
						(rand.nextInt((int) (this.getHeight() - meatball.getPixelsTall()))));
				this.isMeatballActive = true;
				this.meatballDidDamage = false;
			}
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
		if (!isBossActive && (bossE.x > this.getWidth() - bossE.width)) {
			Graphics2D g2d = getGraphics2D();
			bossE.translate(-bossE.getSpeed(), 0);
			getNewGraphicsManager().drawBossEnter(bossE, g2d, this);
		}
		else {
			this.isBossActive = true;
//			Graphics2D g2d = getGraphics2D();
//			boss.translate(1000000, 1000000);
//			getNewGraphicsManager().drawBossFight(boss,  g2d,  this);
		}
	}
	
	protected void drawBossFight() {
		if (isBossActive) {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawBossFight(bossF,  g2d,  this);
		}
	}
	
	
	
	public Meatball newMeatball(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Meatball.WIDTH - 50);
		int yPos = rand.nextInt((int)(screen.getHeight() - Meatball.HEIGHT - 32));
		meatball = new Meatball(xPos, yPos);
		return meatball;
	}
	
	public Boss newBossEnter(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth());
		int yPos = (int) (screen.getHeight() - Boss.HEIGHT - 32);
		bossE = new Boss(xPos, yPos);
		return bossE;
	}
	
	public Boss newBossFight(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Boss.WIDTH);
		int yPos = (int) (screen.getHeight() - Boss.HEIGHT - 32);
		bossF = new Boss(xPos, yPos);
		return bossF;
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
		
	
	
	
	protected void isBossDefeated() {
		if (hitCounter >= 50) {
			levelAsteroidsDestroyed = 50;
		}
	}
	
	
	protected void checkBigBulletBossCollisions() {
		while (isBossActive) {
			for(int i = 0; i < bigBullets.size(); i++) {
				BigBullet bigBullet = bigBullets.get(i);
				if(bossF.intersects(bigBullet)) {
					bigBullets.remove(i);
					hitCounter += 5;
					damage=0;
					break;
				}
			}
		}
	}

	
	protected void checkMegaManMeatballCollisions() {
			GameStatus status = getGameStatus();
			if(meatball.intersects(megaMan) && !meatballDidDamage) {
				status.setLivesLeft(status.getLivesLeft() - 1);
				this.meatballDidDamage = true;
			}
	}
	
	protected void checkMegaManBossFCollisions() {
		GameStatus status = getGameStatus();
		if(bossF.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
	}
	
	protected void checkMegaManBossECollisions() {
		GameStatus status = getGameStatus();
		if(bossE.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
	}
	
	protected void checkBulletBossCollisions() {
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bossF.intersects(bullet)) {
				// increase asteroids destroyed count
				bullets.remove(i);
				hitCounter++;
				damage=0;
				break;
			}
		}
	}
	
}
