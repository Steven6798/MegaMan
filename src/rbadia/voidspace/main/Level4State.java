package rbadia.voidspace.main;

import java.awt.Graphics2D;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Level very similar to LevelState 3.  
 * Platforms arranged on both sides. 
 * Asteroids travel at 225 degree angle
 */
public class Level4State extends Level3State {

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
		setAsteroidsToDestroy(15);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
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
	
}