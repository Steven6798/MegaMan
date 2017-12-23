package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.io.File;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.model.NewMegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Level very similar to LevelState 1.  
 * The big platforms move and the platforms are arranged in the middle. 
 * Asteroids travel at 225 degree angle
 */
public class Level3State extends NewLevel2State {
	
	protected BigPlatform[] bigPlatforms;
	protected int numBigPlatforms = 4;
	protected int platformDirection;
	protected int asteroidDirection = 1;

	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level3State(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}
	
	// getters
	public BigPlatform[] getBigPlatforms() 		{ return bigPlatforms; }
	public int getNumBigPlatforms() 			{ return numBigPlatforms; }
	
	// setters
	public void setNumPlatforms(int numPlatforms) { this.numPlatforms = numPlatforms; }
	public void setNumBigPlatforms(int numBigPlatforms) { this.numBigPlatforms = numBigPlatforms; }
	public void setAsteroidsToDestroy(int asteroidsToDestroy) { this.asteroidsToDestroy = asteroidsToDestroy; }
	
	@Override
	public void doStart() {
		super.doStart();
		setNumPlatforms(4);
		setNumBigPlatforms(4);
		setAsteroidsToDestroy(5);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
		setLevelMusic(new File("audio/Galactic chase.wav"));
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		drawBigPlatforms();
	}

	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {
			asteroid.translate(-asteroid.getSpeed(), asteroidDirection * asteroid.getSpeed() / 2);
			getNewGraphicsManager().drawAsteroid(asteroid, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY) {
				asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
						rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
				asteroidDirection *= -1;
			}
			else {
				// draw explosion
				getNewGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
	}

	public BigPlatform[] newBigPlatforms(int n){
		bigPlatforms = new BigPlatform[n];
		for(int i = 0; i < n; i++){
			this.bigPlatforms[i] = new BigPlatform(0, (getHeight() / 2 + 140 - 2 * i * 40));
		}
		return bigPlatforms;
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i = 0; i < n; i++){
			this.platforms[i] = new Platform(getWidth() / 2 - platforms.length, getHeight() / 2 + 140 - (2 * i + 1) * 40);
		}
		return platforms;
	}
	
	/**
	 * Move the big platform.
	 * @param bigPlatform the big platform to move.
	 */
	public void moveBigPlatform(BigPlatform bigPlatform) {
		if(bigPlatform.getX() == 0) {
			platformDirection = 1;
		}
		if(bigPlatform.getMaxX() == getWidth()) {
			platformDirection = -1;
		}
		bigPlatform.translate(platformDirection, 0);
	}
	
	protected void drawBigPlatforms() {
		//draw big platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumBigPlatforms(); i++) {
			getNewGraphicsManager().drawBigPlatform(bigPlatforms[i], g2d, this, i);
			moveBigPlatform(bigPlatforms[i]);
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
	public boolean Fall() {
		NewMegaMan megaMan = this.getMegaMan(); 
		BigPlatform[] bigPlatforms = this.getBigPlatforms();
		Platform[] platforms = this.getPlatforms();
		for(int i = 0; i < getNumBigPlatforms(); i++){
			if((((bigPlatforms[i].getX() < megaMan.getX()) && (megaMan.getX()< bigPlatforms[i].getX() + bigPlatforms[i].getWidth()))
					|| ((bigPlatforms[i].getX() < megaMan.getX() + megaMan.getWidth()) 
							&& (megaMan.getX() + megaMan.getWidth()< bigPlatforms[i].getX() + bigPlatforms[i].getWidth())))
					&& megaMan.getY() + megaMan.getHeight() == bigPlatforms[i].getY()) {
				return false;
			}
		}
		for(int i = 0; i < getNumPlatforms(); i++){
			if((((platforms[i].getX() < megaMan.getX()) && (megaMan.getX()< platforms[i].getX() + platforms[i].getWidth()))
					|| ((platforms[i].getX() < megaMan.getX() + megaMan.getWidth()) 
					&& (megaMan.getX() + megaMan.getWidth()< platforms[i].getX() + platforms[i].getWidth())))
					&& megaMan.getY() + megaMan.getHeight() == platforms[i].getY()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void drawBackground() {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawBackground3(g2d, this);
	}
}