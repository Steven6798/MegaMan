package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.NewMegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class NewLevel1State extends NewLevelState {

	private static final long serialVersionUID = 1L;
	protected BufferedImage backBuffer;
	protected NewMegaMan megaMan;
	protected Asteroid asteroid;
	protected List<Bullet> bullets;
	protected List<Bullet> bulletsLeft;
	protected List<BigBullet> bigBullets;
	protected List<BigBullet> bigBulletsLeft;
	protected Floor[] floor;	
	protected int numPlatforms = 8;
	protected Platform[] platforms;

	protected int damage = 0;
	protected static final int NEW_MEGAMAN_DELAY = 500;
	protected static final int NEW_ASTEROID_DELAY = 500;

	protected long lastAsteroidTime;
	protected long lastLifeTime;

	protected Rectangle asteroidExplosion;

	protected Random rand;

	protected Font originalFont;
	protected Font bigFont;
	protected Font biggestFont;

	protected int levelAsteroidsDestroyed = 0;
	
	boolean musicState = true;
	
	protected static int asteroidsToDestroy; //======================
	protected static String destroyText;
	
	protected int megaManDirection = 1;
	
	protected File levelMusic = new File("audio/Bag Raiders - Shooting Stars.wav");
	
	// Constructors
	public NewLevel1State(int level, NewMainFrame frame, GameStatus status, 
			NewLevelLogic newGameLogic, InputHandler inputHandler,
			NewGraphicsManager newGraphicsMan, NewSoundManager newSoundMan) {
		super();
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
		this.setLevel(level);
		this.setNewMainFrame(frame);
		this.setGameStatus(status);
		this.setNewGameLogic(newGameLogic);
		this.setInputHandler(inputHandler);
		this.setNewSoundManager(newSoundMan);
		this.setNewGraphicsManager(newGraphicsMan);
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		this.setGraphics2D(backBuffer.createGraphics());
		rand = new Random();
	}

	// Getters
	public NewMegaMan getMegaMan() 				{ return megaMan; }
	public Floor[] getFloor()					{ return floor; }
	public int getNumPlatforms()				{ return numPlatforms; }
	public Platform[] getPlatforms()			{ return platforms; }
	public Asteroid getAsteroid() 				{ return asteroid; }
	public List<Bullet> getBullets() 			{ return bullets; }
	public List<Bullet> getBulletsLeft() 		{ return bulletsLeft; }
	public List<BigBullet> getBigBullets()		{ return bigBullets; }
	public List<BigBullet> getBigBulletsLeft() 	{ return bigBulletsLeft; }
	//public static int getAsteroidsToDestroy() 	{ return asteroidsToDestroy; }
	public static String getDestroyText()		{ return destroyText; }
	
	
	public void setDestroyText(String destroyText) { NewLevel1State.destroyText = destroyText; }
	public void setLevelMusic(File levelMusic) { this.levelMusic = levelMusic; }
	// Level state methods
	// The method associated with the current level state will be called 
	// repeatedly during each LevelLoop iteration until the next a state 
	// transition occurs
	// To understand when each is invoked see LevelLogic.stateTransition() & LevelLoop class

	@Override
	public void doStart() {	

		setStartState(START_STATE);
		setCurrentState(getStartState());
		// init game variables
		bullets = new ArrayList<Bullet>();
		bigBullets = new ArrayList<BigBullet>();
		//numPlatforms = new Platform[5];
		bulletsLeft = new ArrayList<Bullet>();
		bigBulletsLeft = new ArrayList<BigBullet>();
		
		GameStatus status = this.getGameStatus();

		status.setGameOver(false);
		status.setNewAsteroid(false);

		// init the life and the asteroid
		newMegaMan();
		newFloor(this, 9);
		newPlatforms(getNumPlatforms());
		newAsteroid(this);

		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastLifeTime = -NEW_MEGAMAN_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// Display initial values for scores
		getNewMainFrame().getDestroyedValueLabel().setForeground(Color.BLACK);
		getNewMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		getNewMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		getNewMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));

		asteroidsToDestroy = 3;
		setDestroyText("Destroy " + String.valueOf(asteroidsToDestroy) + " asteroids");
	}

	@Override
	public void doInitialScreen() {
		setCurrentState(INITIAL_SCREEN);
		clearScreen();
		getNewGameLogic().drawInitialMessage();
	}

	@Override
	public void doGettingReady() {
		setCurrentState(GETTING_READY);
		getNewGameLogic().drawGetReady();
		repaint();
		NewLevelLogic.delay(2000);
		//Changes music from "menu music" to "ingame music"
		MegaManMain.audioClip.close();
		MegaManMain.audioFile =  levelMusic;
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
	}
	
	@Override
	public void doPlaying() {
		setCurrentState(PLAYING);
		updateScreen();
	}

	@Override
	public void doNewMegaman() {
		setCurrentState(NEW_MEGAMAN);
	}

	@Override
	public void doLevelWon() {
		setCurrentState(LEVEL_WON);
		getNewGameLogic().drawYouWin();
		repaint();
		NewLevelLogic.delay(5000);
		MegaManMain.audioClip.close();
	}

	@Override
	public void doGameOverScreen() {
		setCurrentState(GAME_OVER_SCREEN);
		getNewGameLogic().drawGameOver();
		getNewMainFrame().getDestroyedValueLabel().setForeground(new Color(128, 0, 0));
		repaint();
		NewLevelLogic.delay(1500);
		MegaManMain.audioClip.close();
	}

	@Override
	public void doGameOver() {
		this.getGameStatus().setGameOver(true);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
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
		drawMegaMan();
		drawAsteroid();
		drawBullets();
		drawBulletsLeft();
		drawBigBullets();
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
	}

	@Override
	public void pauseMusic() {
		if(musicState == true) {
			MegaManMain.audioClip.stop();
			musicState = false;
		}
		else {
			MegaManMain.audioClip.start();
			musicState = true;
		}
	}
	
	@Override
	public void skipLevel() {
		levelAsteroidsDestroyed = asteroidsToDestroy;
	}
	
	protected void checkAsteroidFloorCollisions() {
		for(int i = 0; i < 9; i++) {
			if(asteroid.intersects(floor[i])) {
				removeAsteroid(asteroid);
			}
		}
	}

	protected void checkMegaManAsteroidCollisions() {
		GameStatus status = getGameStatus();
		if(asteroid.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeAsteroid(asteroid);
		}
	}

	protected void checkBigBulletAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bigBullets.size(); i++) {
			BigBullet bigBullet = bigBullets.get(i);
			if(asteroid.intersects(bigBullet)) {
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				removeAsteroid(asteroid);
				levelAsteroidsDestroyed++;
				// remove big bullet
				bigBullets.remove(i);
				break;
			}
		}
	}

	protected void checkBulletAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)) {
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				removeAsteroid(asteroid);
				levelAsteroidsDestroyed++;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}

	protected void drawBigBullets() {
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < bigBullets.size(); i++) {
			BigBullet bigBullet = bigBullets.get(i);
			getNewGraphicsManager().drawBigBullet(bigBullet, g2d, this);
			boolean remove = this.moveBigBullet(bigBullet);
			if(remove) {
				bigBullets.remove(i);
				i--;
			}
		}
	}

	protected void drawBullets() {
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			getNewGraphicsManager().drawBullet(bullet, g2d, this);
			boolean remove = this.moveBullet(bullet);
			if(remove) {
				bullets.remove(i);
				i--;
			}
		}
	}

	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if((asteroid.getX() + asteroid.getWidth() >  0)) {
			asteroid.translate(-asteroid.getSpeed(), 0);
			getNewGraphicsManager().drawAsteroid(asteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY) {
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation((int) (this.getWidth() - asteroid.getPixelsWide()),
						(rand.nextInt((int) (this.getHeight() - asteroid.getPixelsTall() - 32))));
			}
			else{
				// draw explosion
				getNewGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}
	}

	protected void drawMegaMan() {
		//draw one of six possible MegaMan poses according to situation
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(getInputHandler().isLeftPressed()) {
			megaManDirection = -1;
		}
		if(getInputHandler().isRightPressed()) {
			megaManDirection = 1;
		}
		
		if(!status.isNewMegaMan()) {
			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))) {
				if(megaManDirection == -1) {
					getNewGraphicsManager().drawMegaFallL(megaMan, g2d, this);
				}
				else {
					getNewGraphicsManager().drawMegaFallR(megaMan, g2d, this);
				}
			}
		}

		if((Fire() == true || Fire2() == true) && (Gravity() == false)) {
			if(megaManDirection == -1) {
				getNewGraphicsManager().drawMegaFireL(megaMan, g2d, this);
			}
			else {
				getNewGraphicsManager().drawMegaFireR(megaMan, g2d, this);
			}
		}

		if((Gravity() == false) && (Fire() == false) && (Fire2() == false)) {
			if(megaManDirection == -1) {
				getNewGraphicsManager().drawMegaManL(megaMan, g2d, this);
			}
			else {
				getNewGraphicsManager().drawMegaMan(megaMan, g2d, this);
			}
		}
	}

	protected void drawPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < getNumPlatforms(); i++) {
			getNewGraphicsManager().drawPlatformBlack(platforms[i], g2d, this, i);
		}
	}

	protected void drawFloor() {
		//draw Floor
		Graphics2D g2d = getGraphics2D();
		for(int i = 0; i < 9; i++) {
			getNewGraphicsManager().drawFloor(floor[i], g2d, this, i);	
		}
	}
	
	protected void drawBackground() {
		Graphics2D g2d = getGraphics2D();
		getNewGraphicsManager().drawBackground1(g2d, this);
	}

	protected void clearScreen() {
		// clear screen
		Graphics2D g2d = getGraphics2D();
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	protected void drawStars(int numberOfStars) {
		Graphics2D g2d = getGraphics2D();
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < numberOfStars; i++) {
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	@Override
	public boolean isLevelWon() {
		return levelAsteroidsDestroyed >= asteroidsToDestroy;
	}

	protected boolean Gravity() {
		NewMegaMan megaMan = this.getMegaMan();
		Floor[] floor = this.getFloor();
		for(int i = 0; i < 9; i++) {
			if((megaMan.getY() + megaMan.getHeight() -17 < this.getHeight() - floor[i].getHeight()/2 - 18) //===
					&& Fall() == true) {
				megaMan.translate(0, 2);
				return true;
			}
		}
		return false;
	}

	//Bullet fire pose
	protected boolean Fire() {
		NewMegaMan megaMan = this.getMegaMan();
		List<Bullet> bullets = this.getBullets();
		List<Bullet> bulletsLeft = this.getBulletsLeft();
		if(megaManDirection == -1) {
			for(int i = 0; i < bulletsLeft.size(); i++){
				Bullet bulletLeft = bulletsLeft.get(i);
				if((bulletLeft.getX() < megaMan.getX()) && (bulletLeft.getX() >= megaMan.getX() - 60)) {
					return true;
				}
			}
		}
		else {
			for(int i = 0; i < bullets.size(); i++) {
				Bullet bullet = bullets.get(i);
				if((bullet.getX() > megaMan.getX() + megaMan.getWidth()) && 
				   (bullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)) {
					return true;
				}
			}
		}
		return false;
	}

	//BigBullet fire pose
	protected boolean Fire2() {
		NewMegaMan megaMan = this.getMegaMan();
		List<BigBullet> bigBullets = this.getBigBullets();
		List<BigBullet> bigBulletsLeft = this.getBigBulletsLeft();
		if(megaManDirection == -1) {
			for(int i = 0; i < bigBulletsLeft.size(); i++){
				BigBullet bigBulletLeft = bigBulletsLeft.get(i);
				if((bigBulletLeft.getX() < megaMan.getX()) && (bigBulletLeft.getX() >= megaMan.getX() - 60)) {
					return true;
				}
			}
		}
		else {
			for(int i = 0; i < bigBullets.size(); i++) {
				BigBullet bigBullet = bigBullets.get(i);
				if((bigBullet.getX() > megaMan.getX() + megaMan.getWidth()) && 
				   (bigBullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)) {
					return true;
				}
			}
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

	//Platform Gravity
	public boolean Fall(){
		NewMegaMan megaMan = this.getMegaMan(); 
		Platform[] platforms = this.getPlatforms();
		for(int i = 0; i < getNumPlatforms(); i++) {
			if((((platforms[i].getX() < megaMan.getX()) && (megaMan.getX()< platforms[i].getX() + platforms[i].getWidth()))
					|| ((platforms[i].getX() < megaMan.getX() + megaMan.getWidth()) 
					&& (megaMan.getX() + megaMan.getWidth()< platforms[i].getX() + platforms[i].getWidth())))
					&& megaMan.getY() + megaMan.getHeight() == platforms[i].getY()) {
				return false;
			}
		}
		return true;
	}

	public void removeAsteroid(Asteroid asteroid) {
		// "remove" asteroid
		asteroidExplosion = new Rectangle(
				asteroid.x,
				asteroid.y,
				asteroid.getPixelsWide(),
				asteroid.getPixelsTall());
		asteroid.setLocation(-asteroid.getPixelsWide(), -asteroid.getPixelsTall());
		this.getGameStatus().setNewAsteroid(true);
		lastAsteroidTime = System.currentTimeMillis();
		// play asteroid explosion sound
		this.getNewSoundManager().playAsteroidExplosionSound();
	}

	/**
	 * Fire a bullet from life.
	 */
	public void fireBullet() {
		Bullet bullet;
		if(megaManDirection == -1) {
			bullet = new Bullet(megaMan.x - Bullet.WIDTH / 2,
								megaMan.y + megaMan.height / 2 - Bullet.HEIGHT + 2);
			bulletsLeft.add(bullet);
		}
		else {
			bullet = new Bullet(megaMan.x + megaMan.width + Bullet.WIDTH + 8,
								megaMan.y + megaMan.height / 2 - Bullet.HEIGHT + 2);
			bullets.add(bullet);
		}
		this.getNewSoundManager().playBulletSound();
	}

	/**
	 * Fire the "Power Shot" bullet
	 */
	public void fireBigBullet() {
		BigBullet  bigBullet;
		if(megaManDirection == -1) {
			bigBullet = new BigBullet(megaMan.x - BigBullet.WIDTH / 2,
								megaMan.y + megaMan.height / 2 - BigBullet.HEIGHT + 6);
			bigBulletsLeft.add(bigBullet);
		}
		else {
			bigBullet = new BigBullet(megaMan.x + megaMan.width + BigBullet.WIDTH / 2,
									  megaMan.y + megaMan.height / 2 - BigBullet.HEIGHT + 6);
			bigBullets.add(bigBullet);
		}
		this.getNewSoundManager().playBulletSound();
	}

	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet) {
		if(bullet.getX() + bullet.getPixelsWide() + bullet.getSpeed() < getWidth()) {
			bullet.translate(bullet.getSpeed(), 0);
			return false;
		}
		return true;
	}
	
	public boolean moveBulletLeft(Bullet bullet) {
		if(bullet.getX() - bullet.getSpeed() > 0) {
			bullet.translate(-bullet.getSpeed(), 0);
			return false;
		}
		return true;
	}
	
	/** Move a "Power Shot" bullet once fired.
	 * @param bigBullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBigBullet(BigBullet bigBullet) {
		if(bigBullet.getX() + bigBullet.getPixelsWide() + bigBullet.getSpeed() < getWidth()) {
			bigBullet.translate(bigBullet.getSpeed(), 0);
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

	/**
	 * Create a new MegaMan (and replace current one).
	 */
	public NewMegaMan newMegaMan() {
		this.megaMan = new NewMegaMan((getWidth() - NewMegaMan.WIDTH) / 2, (getHeight() - NewMegaMan.HEIGHT - NewMegaMan.Y_OFFSET) / 2);
		return megaMan;
	}

	public Floor[] newFloor(NewLevel1State screen, int n) {
		floor = new Floor[n];
		for(int i = 0; i < n; i++){
			this.floor[i] = new Floor(0 + i * Floor.WIDTH, screen.getHeight() - Floor.HEIGHT / 2);
		}
		return floor;
	}

	public Platform[] newPlatforms(int n) {
		platforms = new Platform[n];
		for(int i = 0; i < n; i++){
			this.platforms[i] = new Platform(0 , getHeight() / 2 + 140 - i * 40);
		}
		return platforms;
	}

	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(NewLevel1State screen) {
		int xPos = (int) (screen.getWidth() - Asteroid.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT - 32));
		asteroid = new Asteroid(xPos, yPos);
		return asteroid;
	}

	/**
	 * Move the megaMan up
	 * @param megaMan the megaMan
	 */
	public void moveMegaManUp() {
		if(megaMan.getY() - megaMan.getSpeed() >= 0) {
			megaMan.translate(0, -megaMan.getSpeed() * 2);
		}
	}

	/**
	 * Move the megaMan down
	 * @param megaMan the megaMan
	 */
	public void moveMegaManDown() {
		for(int i = 0; i < 9; i++){
			if(megaMan.getY() + megaMan.getSpeed() + megaMan.height < getHeight() - floor[i].getHeight() / 2) {
				megaMan.translate(0, 2);
			}
		}
	}

	/**
	 * Move the megaMan left
	 * @param megaMan the megaMan
	 */
	public void moveMegaManLeft() {
		if(megaMan.getX() - megaMan.getSpeed() >= 0) {
			megaMan.translate(-megaMan.getSpeed(), 0);
		}
	}

	/**
	 * Move the megaMan right
	 * @param megaMan the megaMan
	 */
	public void moveMegaManRight() {
		if(megaMan.getX() + megaMan.getSpeed() + megaMan.width < getWidth()) {
			megaMan.translate(megaMan.getSpeed(), 0);
		}
	}

	public void speedUpMegaMan() {
		megaMan.setSpeed(megaMan.getDefaultSpeed() * 2 + 1);
	}

	public void slowDownMegaMan() {
		megaMan.setSpeed(megaMan.getDefaultSpeed());
	}
}