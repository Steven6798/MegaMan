package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Level very similar to LevelState 3.  
 * Platforms arranged on both sides. 
 * Asteroids travel at 225 degree angle
 */
public class Level4State extends Level3State {
	protected BigAsteroid bigAsteroid;
	protected Rectangle bigAsteroidExplosion;
	protected long lastBigAsteroidTime = NEW_ASTEROID_DELAY;
	protected int bigAsteroidHealth = 5;

	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level4State(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}
	
	@Override
	public void doStart() {
		super.doStart();
		setNumPlatforms(0);
		setNumBigPlatforms(8);
		setAsteroidsToDestroy(10);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		newBigAsteroid(this);
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
		setLevelMusic(new File("audio/Arcade Music.wav"));
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		drawBigAsteroid();
		checkBigAsteroidFloorCollisions();
		checkBigBulletBigAsteroidCollisions();
		checkBulletBigAsteroidCollisions();
		checkMegaManBigAsteroidCollisions();
	}

	public BigAsteroid newBigAsteroid(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - BigAsteroid.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - BigAsteroid.HEIGHT - 32));
		bigAsteroid = new BigAsteroid(xPos, yPos);
		return bigAsteroid;
	}
	
	public void drawBigAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if((bigAsteroid.getX() + bigAsteroid.getPixelsWide() >  0)) {
			bigAsteroid.translate(-bigAsteroid.getSpeed(), 0);
			getNewGraphicsManager().drawBigAsteroid(bigAsteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBigAsteroidTime) > NEW_ASTEROID_DELAY){

				bigAsteroid.setLocation(this.getWidth() - bigAsteroid.getPixelsWide(),
						300);
						//rand.nextInt(this.getHeight() - bigAsteroid.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				getNewGraphicsManager().drawBigAsteroidExplosion(bigAsteroidExplosion, g2d, this);
			}
		}	
	}
	
	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {
			asteroid.translate(-asteroid.getSpeed(), 0);
			getNewGraphicsManager().drawAsteroid(asteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
						rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				getNewGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
	}

	@Override
	public BigPlatform[] newBigPlatforms(int n){
		bigPlatforms = new BigPlatform[n];
		for(int i = 0; i < n; i++){
			if(i % 2 == 0) {
				this.bigPlatforms[i] = new BigPlatform(0, getHeight() / 2 + 140 - i * 40);
			}
			else {
				this.bigPlatforms[i] = new BigPlatform(getWidth() - 88, getHeight() / 2 + 140 - i * 40);
			}
		}
		return bigPlatforms;
	}
	
	@Override
	protected void drawBigPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumBigPlatforms(); i++) {
			getNewGraphicsManager().drawBigPlatform(bigPlatforms[i], g2d, this, i);
			if(i % 2 == 0) {
				moveBigPlatform(bigPlatforms[i]);
				platformDirection *= -1;
			}
			else {
				moveBigPlatform(bigPlatforms[i]);
				platformDirection *= -1;
			}
		}
	}
	
	@Override
	public void drawBackground() {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawBackground4(g2d, this);
	}
	
	protected void checkBigAsteroidFloorCollisions() {
		for(int i = 0; i < 9; i++) {
			if(bigAsteroid.intersects(floor[i])) {
				removeBigAsteroid(bigAsteroid);
			}
		}
	}
	
	protected void checkMegaManBigAsteroidCollisions() {
		GameStatus status = getGameStatus();
		if(bigAsteroid.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeBigAsteroid(bigAsteroid);
		}
	}
	
	protected void checkBulletBigAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bigAsteroid.intersects(bullet)) {
				if(bigAsteroidHealth > 1) {
					bullets.remove(i);
					bigAsteroidHealth--;
					break;
				}
				else {
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 500);
					removeBigAsteroid(bigAsteroid);
					levelAsteroidsDestroyed+=5;
					// remove bullet
					bullets.remove(i);
					bigAsteroidHealth = 5;
					break;
				}
			}
		}
	}
	
	protected void checkBigBulletBigAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bigBullets.size(); i++) {
			BigBullet bigBullet = bigBullets.get(i);
			if(bigAsteroid.intersects(bigBullet)) {
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 500);
				removeBigAsteroid(bigAsteroid);
				levelAsteroidsDestroyed += 5;
				// remove big bullet
				bigBullets.remove(i);
				break;
			}
		}
	}
	
	public void removeBigAsteroid(BigAsteroid bigAsteroid) {
		// "remove" asteroid
		bigAsteroidExplosion = new Rectangle(
				bigAsteroid.x,
				bigAsteroid.y,
				bigAsteroid.getPixelsWide(),
				bigAsteroid.getPixelsTall());
		bigAsteroid.setLocation(-bigAsteroid.getPixelsWide(), -bigAsteroid.getPixelsTall());
		this.getGameStatus().setNewBigAsteroid(true);
		lastBigAsteroidTime = System.currentTimeMillis();
		// play asteroid explosion sound
		this.getNewSoundManager().playAsteroidExplosionSound();
	}
	
}