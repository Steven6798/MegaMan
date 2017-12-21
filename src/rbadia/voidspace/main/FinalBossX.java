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
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.NewMegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

public class FinalBossX extends Level3State{
private static final long serialVersionUID = 1L;
	
	protected Meatball meatball;
	protected Boss boss;
	protected Boss bossHitbox;
	protected Asteroid deathExplosion;
	private boolean isBossActive = false;
	private boolean meatballDidDamage = false;
	private int hitCounter = 0;
	
	private boolean isMeatballActive = true;
	
	//public Meatball getMeatball()	{ return meatball; }//Necessary?

	public FinalBossX(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}
	
	@Override
	public void doStart() {
		super.doStart();
		bulletsLeft = new ArrayList<Bullet>();
		bigBulletsLeft = new ArrayList<BigBullet>();
		setNumPlatforms(4);
		setNumBigPlatforms(3);
		setAsteroidsToDestroy(50);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		newMeatball(this);
		newBoss(this);
		newBossHitbox(this);
		newDeathExplosion(this);
		NewSoundManager.playToasterSound();
	}
	
	@Override
	public void doGettingReady() {
		setDestroyText("This is it, Luigi...");
		setCurrentState(GETTING_READY);
		getGameLogic().drawGetReady();
		repaint();
		LevelLogic.delay(3500);
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
	public void doPlaying() {
		if(!isBossActive) {
			setCurrentState(PLAYING);
			updateScreen();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.isBossActive = true;
		}
		else {
			setCurrentState(PLAYING);
			updateScreen();
		}
	}
	
	@Override
	public void updateScreen() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = this.getGameStatus();

		// save original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		clearScreen();
		drawBackground();
		drawFloor();
		drawPlatforms();
		drawBigPlatforms();
		drawBoss();
		drawMeatball();
		drawMegaMan();
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		drawBulletsLeft();
		drawBigBulletsLeft();
		if (isBossDefeated()) 
			drawDeath();
		checkMegaManMeatballCollisions();
		checkMegaManBossCollisions();
		checkBulletBossCollisions();
		checkBigBulletBossCollisions();
		
		// update asteroids destroyed (score) label  
				getNewMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
				// update lives left label
				getNewMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
				// update level label
				getNewMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
		
		
		
		
//		if(!isBossActive) {
//			super.updateScreen();
//			drawBoss();
//			getGameLogic();
//			LevelLogic.delay(1000);
//			this.isBossActive = true;
//		}
//		else {
//		super.updateScreen();
//		drawBoss();
//		drawMeatball();
		
//		}
	}
	
	
	
	
	protected void drawMeatball() {
		if (isBossActive) {
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
	
	protected void drawBoss() {
		Graphics2D g2d = getGraphics2D();
		if (!isBossDefeated()) {
		getNewGraphicsManager().drawBoss(boss,  g2d,  this);
		}
		else {
			boss.translate(0, boss.getDefaultSpeed());
			getNewGraphicsManager().drawBoss(boss, g2d, this);
		}
	}
	
	public void drawDeath() {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawAsteroidExplosion(deathExplosion, g2d, this);
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
	
	public Boss newBossHitbox(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Boss.WIDTH + 68);
		int yPos = (int) (screen.getHeight() - Boss.HEIGHT - 32);
		bossHitbox = new Boss(xPos, yPos);
		return bossHitbox;
	}
	
	public Asteroid newDeathExplosion(NewLevel1State screen) {
		while (true) {
		int xPos = rand.nextInt(Boss.WIDTH) + (int) (screen.getWidth() - Boss.WIDTH);
		int yPos = rand.nextInt(Boss.HEIGHT) + (int) (screen.getHeight() - Boss.HEIGHT);
		deathExplosion = new Asteroid(xPos, yPos);
		return deathExplosion;
		}
		
	}
	
	
	@Override
	public NewMegaMan newMegaMan() {
		this.megaMan = new NewMegaMan(0, (getHeight() - NewMegaMan.HEIGHT - NewMegaMan.Y_OFFSET) / 2);
		return megaMan;
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
		
	
	
	protected boolean isBossDefeated() {
		if (hitCounter >= 5) {
			return true;
		}
		else
			return false;
	}
	
	
	

	protected void checkMegaManMeatballCollisions() {
			GameStatus status = getGameStatus();
			if(meatball.intersects(megaMan) && !meatballDidDamage) {
				status.setLivesLeft(status.getLivesLeft() - 1);
				this.meatballDidDamage = true;
			}
	}
	
	protected void checkMegaManBossCollisions() {
		GameStatus status = getGameStatus();
		if(boss.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
	}
	
	protected void checkBulletBossCollisions() {
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bossHitbox.intersects(bullet) && this.hitCounter < 50) {
				// increase asteroids destroyed count
				bullets.remove(i);
				this.hitCounter++;
				//levelAsteroidsDestroyed++;
				damage=0;
				break;
			}
		}
	}

	protected void checkBigBulletBossCollisions() {
		for(int i = 0; i < bigBullets.size(); i++) {
			BigBullet bigBullet = bigBullets.get(i);
			if(bossHitbox.intersects(bigBullet)) {
				bigBullets.remove(i);
				this.hitCounter += 5;
				damage=0;
				break;
			}
		}
	}
}
