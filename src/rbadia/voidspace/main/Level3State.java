package rbadia.voidspace.main;

import java.awt.Graphics2D;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Level very similar to LevelState1.  
 * Platforms arranged in triangular form. 
 * Asteroids travel at 225 degree angle
 */
public class Level3State extends NewLevel2State {

	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level3State(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}

	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {
			asteroid.translate(-asteroid.getSpeed(), asteroid.getSpeed()/2);
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
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i = 0; i < n; i++){
			if(i % 2 == 0) {
				this.platforms[i] = new Platform(0 , getHeight() / 2 + 140 - i * 40);
			}
			else {
				this.platforms[i] = new Platform(getWidth() - 44, getHeight() / 2 + 140 - i * 40);
			}
		}
		return platforms;
	}
	
	/**
	 * Move the platform to the right.
	 * @param platform the platform to move
	 */
	public void movePlatformRight(Platform platform) {
		if(platform.getMaxX() + 2 < getWidth()) {
			platform.translate(2, 0);
		}
		else {
			platform.translate(-getWidth() + 44, 0);
		}
	}
	
	/**
	 * Move the platform to the left.
	 * @param platform the platform to move
	 */
	public void movePlatformLeft(Platform platform) {
		if(platform.getX() -2 > 0) {
			platform.translate(-2, 0);
		}
		else {
			platform.translate(getWidth() - 44, 0);
		}
	}
	
	@Override
	protected void drawPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumPlatforms(); i++) {
			getNewGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
			if(i % 2 == 0) {
				movePlatformRight(platforms[i]);
			}
			else {
				movePlatformLeft(platforms[i]);
			}
		}
	}
}