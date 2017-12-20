package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.NewMegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Level very similar to LevelState 1.  
 * The big platforms move and the platforms are arranged in the middle. 
 * Asteroids travel at 225 degree angle
 */
public class Level3State extends NewLevel2State {
	
	protected List<Bullet> bulletsLeft;
	protected List<BigBullet> bigBulletsLeft;
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
	public List<Bullet> getBulletsLeft() 		{ return bulletsLeft; }
	public List<BigBullet> getBigBulletsLeft() 	{ return bigBulletsLeft; }
	public BigPlatform[] getBigPlatforms() 		{ return bigPlatforms; }
	public int getNumBigPlatforms() 			{ return numBigPlatforms; }
	
	// setters
	public void setNumPlatforms(int numPlatforms) { this.numPlatforms = numPlatforms; }
	public void setNumBigPlatforms(int numBigPlatforms) { this.numBigPlatforms = numBigPlatforms; }
	@SuppressWarnings("static-access") //===========================
	public void setAsteroidsToDestroy(int asteroidsToDestroy) { this.asteroidsToDestroy = asteroidsToDestroy; }
	
	@Override
	public void doStart() {
		super.doStart();
		bulletsLeft = new ArrayList<Bullet>();
		bigBulletsLeft = new ArrayList<BigBullet>();
		setNumPlatforms(4);
		setNumBigPlatforms(4);
		setAsteroidsToDestroy(10);
		newPlatforms(getNumPlatforms());
		newBigPlatforms(getNumBigPlatforms());
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
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
		//drawStars(50);
		drawFloor();
		drawPlatforms();
		drawBigPlatforms();
		drawMegaMan();
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		drawBulletsLeft();
		drawBigBulletsLeft();
		checkBulletAsteroidCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();

		// update asteroids destroyed (score) label  
		getNewMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getNewMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		// update level label
		getNewMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
		
//		super.updateScreen();
//		drawBulletsLeft();
//		drawBigBulletsLeft();
//		drawBigPlatforms();
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

//	@Override
//	protected void drawMegaMan() {
//		//draw one of six possible MegaMan poses according to situation
//		Graphics2D g2d = getGraphics2D();
//		GameStatus status = getGameStatus();
//		if(!status.isNewMegaMan()) {
//			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))) {
//				if(getInputHandler().isLeftPressed()) {
//					getNewGraphicsManager().drawMegaFallL(megaMan, g2d, this);
//				}
//				else {
//					getNewGraphicsManager().drawMegaFallR(megaMan, g2d, this);
//				}
//			}
//		}
//
//		if((Fire() == true || Fire2() == true) && (Gravity() == false)) {
//			if(getInputHandler().isLeftPressed()) {
//				getNewGraphicsManager().drawMegaFireL(megaMan, g2d, this);
//			}
//			else {
//				getNewGraphicsManager().drawMegaFireR(megaMan, g2d, this);
//			}
//		}
//
//		if((Gravity() == false) && (Fire() == false) && (Fire2() == false)) {
//			if(getInputHandler().isLeftPressed()) {
//				getNewGraphicsManager().drawMegaManL(megaMan, g2d, this);
//			}
//			else {
//				getNewGraphicsManager().drawMegaMan(megaMan, g2d, this);
//			}
//		}
//	}

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
		//draw big platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumPlatforms(); i++) {
			getNewGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
		}
	}
	
	@Override
	protected boolean Fire() {
		if(getInputHandler().isLeftPressed()) {
			NewMegaMan megaMan = this.getMegaMan();
			List<Bullet> bullets = this.getBulletsLeft();
			for(int i = 0; i < bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if((bullet.getX() < megaMan.getX()) && (bullet.getX() >= megaMan.getX() - 60)) {
					return true;
				}
			}
		}
		if(super.Fire() == true) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean Fire2() {
		if(getInputHandler().isLeftPressed()) {
			NewMegaMan megaMan = this.getMegaMan();
			List<BigBullet> bigBullets = this.getBigBulletsLeft();
			for(int i = 0; i < bigBullets.size(); i++){
				BigBullet bigBullet = bigBullets.get(i);
				if((bigBullet.getX() < megaMan.getX()) && (bigBullet.getX() >= megaMan.getX() - 60)) {
					return true;
				}
			}
		}
		if(super.Fire2() == true) {
			return true;
		}
		return false;
	}
	
	protected void drawBulletsLeft() {
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < bulletsLeft.size(); i++) {
			Bullet bulletLeft = bulletsLeft.get(i);
			getNewGraphicsManager().drawBullet(bulletLeft, g2d, this);
			boolean remove = this.moveBulletLeft(bulletLeft);
			if(remove){
				bulletsLeft.remove(i);
				i--;
			}
		}
	}
	
	protected void drawBigBulletsLeft() {
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < bigBulletsLeft.size(); i++) {
			BigBullet bigBulletLeft = bigBulletsLeft.get(i);
			getNewGraphicsManager().drawBigBullet(bigBulletLeft, g2d, this);
			boolean remove = this.moveBigBulletLeft(bigBulletLeft);
			if(remove){
				bigBulletsLeft.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void fireBullet() {
		if(getInputHandler().isLeftPressed()) {
			Bullet bullet;
			bullet = new Bullet(megaMan.x - Bullet.WIDTH / 2,
								megaMan.y + megaMan.width / 2 - Bullet.HEIGHT + 2);
			bulletsLeft.add(bullet);
			this.getNewSoundManager().playBulletSound();
		}
		else {
			super.fireBullet();
		}
	}
	
	@Override
	public void fireBigBullet() {
		if(getInputHandler().isLeftPressed()) {
			BigBullet bigBullet;
			bigBullet = new BigBullet(megaMan.x - Bullet.WIDTH / 2,
								megaMan.y + megaMan.width / 2 - Bullet.HEIGHT + 2);
			bigBulletsLeft.add(bigBullet);
			this.getNewSoundManager().playBulletSound();
		}
		else {
			super.fireBigBullet();
		}
	}
	
	public boolean moveBulletLeft(Bullet bullet) {
		if(bullet.getX() - bullet.getSpeed() > 0) {
			bullet.translate(-bullet.getSpeed(), 0);
			return false;
		}
		return true;
	}
	
	public boolean moveBigBulletLeft(BigBullet bigBullet) {
		if(bigBullet.getX() - bigBullet.getSpeed() > 0) {
			bigBullet.translate(-bigBullet.getSpeed(), 0);
			return false;
		}
		return true;
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
	
	
	
	
	
	
//	@Override
//	protected boolean Gravity() {
//		NewMegaMan megaMan = this.getMegaMan();
//		Floor[] floor = this.getFloor();
//		for(int i = 0; i < 9; i++) {
//			if((megaMan.getY() + megaMan.getHeight() -17 < this.getHeight() - floor[i].getHeight()/2 - 18) //===
//					&& FallPlatform() == true) {
//				megaMan.translate(0, 2);
//				return true;
//			}
//			else if((megaMan.getY() + megaMan.getHeight() -17 < this.getHeight() - floor[i].getHeight()/2 - 18) //===
//					&& FallBigPlatform() == true) {
//				megaMan.translate(0, 2);
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	protected boolean FallPlatform() {
//		NewMegaMan megaMan = this.getMegaMan(); 
//		Platform[] platforms = this.getPlatforms();
//		for(int i = 0; i < getNumPlatforms(); i++){
//			if((((platforms[i].getX() < megaMan.getX()) && (megaMan.getX()< platforms[i].getX() + platforms[i].getWidth()))
//					|| ((platforms[i].getX() < megaMan.getX() + megaMan.getWidth()) 
//					&& (megaMan.getX() + megaMan.getWidth()< platforms[i].getX() + platforms[i].getWidth())))
//					&& megaMan.getY() + megaMan.getHeight() == platforms[i].getY()) {
//				return false;
//			}
//		}
//		return true;      
//	}
//	
//	protected boolean FallBigPlatform() {
//		NewMegaMan megaMan = this.getMegaMan(); 
//		BigPlatform[] bigPlatforms = this.getBigPlatforms();
//		for(int i = 0; i < getNumBigPlatforms(); i++){
//			if((((bigPlatforms[i].getX() < megaMan.getX()) && (megaMan.getX()< bigPlatforms[i].getX() + bigPlatforms[i].getWidth()))
//					|| ((bigPlatforms[i].getX() < megaMan.getX() + megaMan.getWidth()) 
//							&& (megaMan.getX() + megaMan.getWidth()< bigPlatforms[i].getX() + bigPlatforms[i].getWidth())))
//					&& megaMan.getY() + megaMan.getHeight() == bigPlatforms[i].getY()) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	
	
	
}