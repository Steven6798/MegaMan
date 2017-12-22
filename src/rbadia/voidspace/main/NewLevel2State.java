package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.io.File;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Level very similar to LevelState1.  
 * Platforms arranged in triangular form. 
 * Asteroids travel at 225 degree angle
 */
public class NewLevel2State extends NewLevel1State {

	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public NewLevel2State(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
		setLevelMusic(new File("audio/Arcade Music.wav"));
	}

	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {
			asteroid.translate(-asteroid.getSpeed(), asteroid.getSpeed() / 2);
			getNewGraphicsManager().drawAsteroid(asteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY) {

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
	public Platform[] newPlatforms(int n) {
		platforms = new Platform[n];
		for(int i = 0; i < n; i++) {
			this.platforms[i] = new Platform(0, 0);
			if(i < 4) platforms[i].setLocation(50 + i * 50, getHeight() / 2 + 140 - i * 40);
			if(i == 4) platforms[i].setLocation(50 + i * 50, getHeight() / 2 + 140 - 3 * 40);
			if(i > 4) {	
				int k = 4;
				platforms[i].setLocation(50 + i * 50, getHeight() / 2 + 20 + (i - k) * 40 );
				k += 2;
			}
		}
		return platforms;
	}
	
	@Override
	public void drawBackground() {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawBackground2(g2d, this);
	}
}