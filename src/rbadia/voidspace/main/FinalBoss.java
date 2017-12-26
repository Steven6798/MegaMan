package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.io.File;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.NewMegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

public class FinalBoss extends Level3State{
	
	private static final long serialVersionUID = 1L;

	protected Meatball meatball;
	protected Boss boss;
	protected Asteroid deathExplosion;
	private int meatBallLife = 3;
	private int bossLife = 50;

	public FinalBoss(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}

	@Override
	public void doStart() {
		super.doStart();
		setNumPlatforms(4);
		setNumBigPlatforms(0);
		setAsteroidsToDestroy(1);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		newMeatball(this);
		newBoss(this);
		newDeathExplosion(this);
		NewSoundManager.playToasterSound();
		setDestroyText("This is it, Luigi...");
		setLevelMusic(new File("audio/BossBattle.wav"));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		isBossDefeated();
		drawBoss();
		drawMeatball();
		drawDeath();
		checkMegaManMeatballCollisions();
		checkBulletMeatBallCollisions();
		checkBulletLeftMeatBallCollisions();
		checkBigBulletMeatBallCollisions();
		checkBigBulletLeftMeatBallCollisions();
		checkMegaManBossCollisions();
		checkBulletBossCollisions();
		checkBigBulletBossCollisions();
	}

	@Override
	public boolean isLevelWon() {
		if(levelAsteroidsDestroyed >= asteroidsToDestroy) {
			return true;
		}
		return false;
	}

	protected void drawMeatball() {
		Graphics2D g2d = getGraphics2D();
		if(!isBossDefeated()) {
			if((meatball.getX() + meatball.getWidth() >  0)) {
				meatball.translate(-meatball.getSpeed(), 0);
				getNewGraphicsManager().drawMeatball(meatball, g2d, this);
			}
			else {
				meatball.setLocation((int) (this.getWidth() - meatball.getPixelsWide() - 100),
						rand.nextInt(Boss.HEIGHT) + (int)(this.getHeight() - Boss.HEIGHT - Meatball.HEIGHT));
				NewSoundManager.playMeatballSound();
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
	
	@Override
	public void drawBigPlatforms() {
		
	}

	public Boss newBoss(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth());
		int yPos = (int) (screen.getHeight() - Boss.HEIGHT - 32);
		boss = new Boss(xPos, yPos);
		return boss;
	}

	protected void drawBoss() {
		Graphics2D g2d = getGraphics2D();
		if(boss.x > this.getWidth() - Boss.WIDTH) {
			boss.translate(-boss.getDefaultSpeed(), 0);
			getNewGraphicsManager().drawBoss(boss, g2d, this);
		}
		else {
			if (!isBossDefeated() && boss.y < this.getWidth()) {
				getNewGraphicsManager().drawBoss(boss,  g2d,  this);
			}
			else if (isBossDefeated() && boss.y < this.getWidth()) {
				boss.translate(0, boss.getDefaultSpeed());
				getNewGraphicsManager().drawBoss(boss, g2d, this);
			}
		}
	}

	public void drawDeath() {
		if (isBossDefeated()) {
			Graphics2D g2d = getGraphics2D();
			getNewGraphicsManager().drawAsteroidExplosion(deathExplosion, g2d, this);
			if(boss.y > getHeight()) {
				levelAsteroidsDestroyed++;
				NewSoundManager.playDeathSound();
			}
			if(rand.nextInt(Boss.WIDTH) + boss.x < this.getWidth() &&
					rand.nextInt(Boss.HEIGHT) + boss.y < 480)
				deathExplosion.setLocation(rand.nextInt(Boss.WIDTH) + ((int) (this.getWidth() - Boss.WIDTH)),
						rand.nextInt(boss.y) + ((int) (this.getHeight() - boss.y)));
			else
				deathExplosion.setLocation(boss.x, boss.y);
			NewSoundManager.playDeathExplosionSound();
		}
	}

	public Meatball newMeatball(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Meatball.WIDTH - 50);
		int yPos = rand.nextInt(Boss.HEIGHT) + (int)(screen.getHeight() - Boss.HEIGHT - Meatball.HEIGHT);
		meatball = new Meatball(xPos, yPos);
		return meatball;
	}

	public Asteroid newDeathExplosion(NewLevel1State screen) {
		int xPos = rand.nextInt(Boss.WIDTH) + (int) (screen.getWidth() - Boss.WIDTH);
		int yPos = rand.nextInt(Boss.HEIGHT) + (int) (screen.getHeight() - Boss.HEIGHT);
		deathExplosion = new Asteroid(xPos, yPos);
		return deathExplosion;
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
		if (bossLife <= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void checkMegaManMeatballCollisions() {
		GameStatus status = getGameStatus();
		if(meatball.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeMeatBall(meatball);
		}
	}
	
	protected void checkBulletMeatBallCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(meatball.intersects(bullet)) {
				meatBallLife--;
				// remove bullet
				bullets.remove(i);
				if(meatBallLife <= 0) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);
					removeMeatBall(meatball);
					meatBallLife = 3;
					break;
				}
			}
		}
	}
	protected void checkBulletLeftMeatBallCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bulletsLeft.size(); i++) {
			Bullet bulletLeft = bulletsLeft.get(i);
			if(meatball.intersects(bulletLeft)) {
				meatBallLife--;
				// remove bullet
				bulletsLeft.remove(i);
				if(meatBallLife <= 0) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);
					removeMeatBall(meatball);
					meatBallLife = 3;
					break;
				}
			}
		}
	}
	
	protected void checkBigBulletMeatBallCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bigBullets.size(); i++) {
			BigBullet bigBullet = bigBullets.get(i);
			if(meatball.intersects(bigBullet)) {
				meatBallLife -= 5;
				// remove big bullet
				bigBullets.remove(i);
				if(meatBallLife <= 0) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);
					removeMeatBall(meatball);
					meatBallLife = 3;
					break;
				}
			}
		}
	}
	
	protected void checkBigBulletLeftMeatBallCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bigBulletsLeft.size(); i++) {
			BigBullet bigBulletLeft = bigBulletsLeft.get(i);
			if(meatball.intersects(bigBulletLeft)) {
				meatBallLife -= 5;
				// remove big bullet
				bigBulletsLeft.remove(i);
				if(meatBallLife <= 0) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);
					removeMeatBall(meatball);
					meatBallLife = 3;
					break;
				}
			}
		}
	}

	protected void checkMegaManBossCollisions() {
		GameStatus status = getGameStatus();
		if(boss.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
			newMegaMan();
		}
	}

	protected void checkBulletBossCollisions() {
		GameStatus status = getGameStatus();
		if(!isBossDefeated()) {
			for(int i = 0; i < bullets.size(); i++) {
				Bullet bullet = bullets.get(i);
				if(boss.intersects(bullet)) {
					NewSoundManager.playGotHitSound();
					// increase boss hits count
					bullets.remove(i);
					bossLife--;
					if(bossLife <= 0) {
						status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 5000);
					}
					break;
				}
			}
		}
	}

	protected void checkBigBulletBossCollisions() {
		GameStatus status = getGameStatus();
		if(!isBossDefeated()) {
			for(int i = 0; i < bigBullets.size(); i++) {
				BigBullet bigBullet = bigBullets.get(i);
				if(boss.intersects(bigBullet)) {
					NewSoundManager.playGotHitSound();
					// increase boss hits count
					bigBullets.remove(i);
					bossLife -= 5;
					if(bossLife <= 0) {
						status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 5000);
					}
					break;
				}
			}
		}
	}
	
	public void removeMeatBall(Meatball meatball) {
		// "remove" meatball
		meatball.setLocation(-meatball.getPixelsWide(), -meatball.getPixelsTall());
	}
}