package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.io.File;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.sounds.NewSoundManager;

public class Level5State extends Level4State {
	protected int translation = 1;
	private static final long serialVersionUID = 1L;

	public Level5State(int level, NewMainFrame frame, GameStatus status, 
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
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
		setLevelMusic(new File("audio/Arcade Music.wav"));
	}
	
	@Override
	public Asteroid newAsteroid(NewLevel1State screen) {
		if (rand.nextInt(1) == 1) {
			int xPos = (int) (screen.getWidth());
			int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT - 32));
			asteroid = new Asteroid(xPos, yPos);
			asteroidDirection = -1;
		}
		else {
			int xPos = -Asteroid.WIDTH;
			int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT - 32));
			asteroid = new Asteroid(xPos, yPos);
			asteroidDirection = 1;
		}
		return asteroid;
	}

	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if (asteroidDirection == -1) {
			if((asteroid.getX() + asteroid.getPixelsWide() > 0)) {
				asteroid.translate(-asteroid.getSpeed(), asteroid.getSpeed() / 2);
				getNewGraphicsManager().drawAsteroid(asteroid, g2d, this);
			}
			else {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY) {
					asteroid.setLocation(-Asteroid.WIDTH,
							rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
					asteroidDirection *= -1;
				}
				else {
					// draw explosion
					getNewGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
					asteroidDirection *= -1;
				}
			}
		}
		else {
			if((asteroid.getX() <  this.getWidth())) {
				asteroid.translate(asteroid.getSpeed(), asteroid.getSpeed() / 2);
				getNewGraphicsManager().drawAsteroid(asteroid, g2d, this);
			}
			else {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY) {
					asteroid.setLocation(this.getWidth(),
							rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
					asteroidDirection *= -1;
				}
				else {
					// draw explosion
					getNewGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
					asteroidDirection *= -1;
				}
			}
		}
	}

	@Override
	public BigPlatform[] newBigPlatforms(int n){
		bigPlatforms = new BigPlatform[n];
		for(int i = 0; i < n; i++){
			if(i % 4 == 1)
				this.bigPlatforms[i] = new BigPlatform(88, getHeight() / 2 + 140 - i * 40);
			else if (i % 4 == 2)
				this.bigPlatforms[i] = new BigPlatform(getWidth() / 2 - 44, getHeight() / 2 + 140 - i * 40);
			else if(i % 4 == 3)
				this.bigPlatforms[i] = new BigPlatform(getWidth() - 176, getHeight() / 2 + 140 - i * 40);
			else
				this.bigPlatforms[i] = new BigPlatform(getWidth() / 2 - 44, getHeight() / 2 + 140 - i * 40);
		}
		return bigPlatforms;
	}

	@Override
	protected void drawBigPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumBigPlatforms(); i++) {
			getNewGraphicsManager().drawBigPlatform(bigPlatforms[i], g2d, this, i);
			if(i % 4 == 2) {
				moveBigPlatform(bigPlatforms[i]);
				translation *= -1;
			}
			else if (i % 4 == 0) {
				moveBigPlatform(bigPlatforms[i]);
			}
		}
	}

	@Override
	public void moveBigPlatform(BigPlatform bigPlatform) {
		if(bigPlatform.getX() <= 0) {
			translation = 1;
		}
		if(bigPlatform.getMaxX() >= getWidth()) {
			translation = -1;
		}
		bigPlatform.translate(translation, 0);
	}
}