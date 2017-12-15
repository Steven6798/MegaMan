package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.Spaghetti;
import rbadia.voidspace.sounds.NewSoundManager;

public class FinalBoss extends Level3State {
	private static final long serialVersionUID = 1L;
	
	protected Meatball meatball;
	protected Spaghetti spaghetti;
	protected Boss boss;

	public FinalBoss(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager soundMan) {
		super(level, frame, status, newGameLogic, inputHandler, newGraphicsMan, soundMan);
	}
	
	public List<Bullet> getBulletsLeft() 		{ return bulletsLeft; }
	public List<BigBullet> getBigBulletsLeft() 	{ return bigBulletsLeft; }
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		
		bulletsLeft = new ArrayList<Bullet>();
		bigBulletsLeft = new ArrayList<BigBullet>();
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		drawBulletsL();
	}
	
	protected void drawMeatball() {
		Graphics2D g2d = getGraphics2D();
		meatball.translate(-meatball.getSpeed(), 0);
		getNewGraphicsManager().drawMeatball(meatball, g2d, this);
	}
	//========================================================= Adjust for Boss
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
	protected void drawMegaMan() {
		//draw one of six possible MegaMan poses according to situation
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(!status.isNewMegaMan()) {
			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))) {
				if(getInputHandler().isLeftPressed()) {
					getNewGraphicsManager().drawMegaFallL(megaMan, g2d, this);
				}
				else {
					getNewGraphicsManager().drawMegaFallR(megaMan, g2d, this);
				}
			}
		}

		if((Fire() == true || Fire2() == true) && (Gravity() == false)) {
			if(getInputHandler().isLeftPressed()) {
				getNewGraphicsManager().drawMegaFireL(megaMan, g2d, this);
			}
			else {
				getNewGraphicsManager().drawMegaFireR(megaMan, g2d, this);
			}
		}

		if((Gravity() == false) && (Fire() == false) && (Fire2() == false)) {
			if(getInputHandler().isLeftPressed()) {
				getNewGraphicsManager().drawMegaManL(megaMan, g2d, this);
			}
			else {
				getNewGraphicsManager().drawMegaMan(megaMan, g2d, this);
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
	
	@Override
	protected boolean Fire() {
		if(getInputHandler().isLeftPressed()) {
			MegaMan megaMan = this.getMegaMan();
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
			MegaMan megaMan = this.getMegaMan();
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
	
	protected void drawBulletsL() {
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
	
	@Override
	public void fireBullet() {
		if(getInputHandler().isLeftPressed()) {
			Bullet bullet;
			bullet = new Bullet(megaMan.x - Bullet.WIDTH/2,
								megaMan.y + megaMan.width/2 - Bullet.HEIGHT + 2);
			bulletsLeft.add(bullet);
			this.getNewSoundManager().playBulletSound();
		}
		else {
			super.fireBullet();
		}
	}
	
	public boolean moveBulletLeft(Bullet bullet) {
		if(bullet.getX() - bullet.getSpeed() > 0) {
			bullet.translate(-bullet.getSpeed(), 0);
			return false;
		}
		return true;
	}
	
	public boolean moveBigBulletLeft(BigBullet bigBullet){
		if(bigBullet.getX() - bigBullet.getSpeed() > 0) {
			bigBullet.translate(-bigBullet.getSpeed(), 0);
			return false;
		}
		return true;
	}

}
