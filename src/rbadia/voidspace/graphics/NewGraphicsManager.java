package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.NewMegaMan;
import rbadia.voidspace.model.SeekingMissile;

public class NewGraphicsManager extends GraphicsManager{
	
	private BufferedImage megaManRImg;
	private BufferedImage megaFallRImg;
	private BufferedImage megaFireRImg;
	private BufferedImage megaManLImg;
	private BufferedImage megaFallLImg;
	private BufferedImage megaFireLImg;
	private BufferedImage bigPlatformImg;
	private BufferedImage meatballImg;
	private BufferedImage bossImg;
	private BufferedImage bigAsteroidImg;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage backgroundImg;
	private BufferedImage seekingMissileImg;
	
	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public NewGraphicsManager(){
		// load images
		try {
			this.megaManRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManRight.png"));
			this.megaFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRight.png"));
			this.megaFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRight.png"));
			this.megaManLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManLeft.png"));
			this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallLeft.png"));
			this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireLeft.png"));
			this.bigPlatformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigPlatform.png"));
			this.meatballImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/meatBall.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/boss.png"));
			this.bigAsteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroid.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.backgroundImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/background.png"));
			this.seekingMissileImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/seekingMissile.png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a MegaMan image to the specified graphics canvas.
	 * @param MegaMan the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */

	public void drawMegaManL(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaManLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFallL(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaFallLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFireL(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaFireLImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawMegaManR(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaManRImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawMegaFallR(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaFallRImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawMegaFireR(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaFireRImg, megaMan.x, megaMan.y, observer);
	}
	
	public void drawBigPlatform(BigPlatform bigPlatform, Graphics2D g2d, ImageObserver observer, int i) {
		g2d.drawImage(bigPlatformImg, bigPlatform.x, bigPlatform.y, observer);	
	}
	
	public void drawMeatball(Meatball meatball, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(meatballImg, meatball.x, meatball.y, observer);
	}
	
	public void drawBoss(Boss boss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossImg, boss.x, boss.y, observer);
	}
	
	public void drawBigAsteroid(BigAsteroid bigAsteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidImg, bigAsteroid.x, bigAsteroid.y, observer);
	}
	
	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}
	
	public void drawBackground(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(backgroundImg, 0, 0, observer);
	}
	
	public void drawSeekingMissile(SeekingMissile seekingMissile, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(seekingMissileImg, seekingMissile.x, seekingMissile.y, observer);
	}
}