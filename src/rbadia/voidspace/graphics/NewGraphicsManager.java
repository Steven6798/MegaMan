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
import rbadia.voidspace.model.Platform;

public class NewGraphicsManager extends GraphicsManager{
	
	private BufferedImage megaManImg;
	private BufferedImage megaFallRImg;
	private BufferedImage megaFireRImg;
	private BufferedImage megaManLImg;
	private BufferedImage megaFallLImg;
	private BufferedImage megaFireLImg;
	private BufferedImage bigPlatformImg;
	private BufferedImage bigPlatformBlackImg;
	private BufferedImage platformBlackImg;
	private BufferedImage meatballImg;
	private BufferedImage bossImg;
	private BufferedImage bigAsteroidImg;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage background1Img;
	private BufferedImage background2Img;
	private BufferedImage background3Img;
	private BufferedImage background4Img;
	private BufferedImage background5Img;
	private BufferedImage backgroundFBImg;
	
	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public NewGraphicsManager(){
		// load images
		try {
			this.megaManImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3.png"));
			this.megaFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRight.png"));
			this.megaFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRight.png"));
			this.megaManLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManL.png"));
			this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallL.png"));
			this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireL.png"));
			this.meatballImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/MeatBall.png"));
			this.platformBlackImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform3Black.png"));
			this.bigPlatformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigPlatform.png"));
			this.bigPlatformBlackImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigPlatformBlack.png"));
			this.bigAsteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.background1Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Background1.png"));
			this.background2Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Background2.png"));
			this.background3Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Background3.png"));
			this.background4Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Background4.png"));
			this.background5Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Background5.png"));
			this.backgroundFBImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BackgroundFB.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossFightScaled.png"));
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
	
	public void drawMegaMan(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaManImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawMegaFallR(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaFallRImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawMegaFireR(NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaFireRImg, megaMan.x, megaMan.y, observer);
	}
	
	public void drawBigAsteroid(BigAsteroid bigAsteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidImg, bigAsteroid.x, bigAsteroid.y, observer);
	}
	
	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}
	
	public void drawBigPlatform(BigPlatform bigPlatform, Graphics2D g2d, ImageObserver observer, int i) {
		g2d.drawImage(bigPlatformImg, bigPlatform.x, bigPlatform.y, observer);	
	}
	
	public void drawBigPlatformBlack(BigPlatform bigPlatform, Graphics2D g2d, ImageObserver observer, int i) {
		g2d.drawImage(bigPlatformBlackImg, bigPlatform.x, bigPlatform.y, observer);
	}
	
	public void drawPlatformBlack(Platform platform, Graphics2D g2d, ImageObserver observer, int i) {
			g2d.drawImage(platformBlackImg, platform.x , platform.y, observer);
	}
	
	public void drawMeatball(Meatball meatball, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(meatballImg, meatball.x, meatball.y, observer);
	}
	
	public void drawBoss(Boss boss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossImg, boss.x, boss.y, observer);
	}
	
	public void drawBackground1(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(background1Img, 0, -20, observer);
	}
	
	public void drawBackground2(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(background2Img, 0, 0, observer);
	}
	
	public void drawBackground3(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(background3Img, 0, -40, observer);
	}
	
	public void drawBackground4(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(background4Img, 0, -20, observer);
	}
	
	public void drawBackground5(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(background5Img, 0, -40, observer);
	}
	
	public void drawBackgroundFB(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(backgroundFBImg, 0, 0, observer);
	}
}