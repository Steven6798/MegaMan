package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.BigPlatform;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.NewMegaMan;

public class NewGraphicsManager extends GraphicsManager{
	
	private BufferedImage megaManLImg;
	private BufferedImage megaFallLImg;
	private BufferedImage megaFireLImg;
	private BufferedImage bigPlatformImg;
	private BufferedImage meatballImg;
	private BufferedImage bossEnterImg;
	private BufferedImage bossFightImg;
	
	private BufferedImage megaManImg;
	private BufferedImage megaFallRImg;
	private BufferedImage megaFireRImg;

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
			this.bigPlatformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigPlatform.png"));
			this.bossEnterImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossEnterScaled.png"));
			this.bossFightImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossFightScaled.png"));
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

	public void drawMegaManL (NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFallL (NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFireL (NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireLImg, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawBigPlatform (BigPlatform bigPlatform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(bigPlatformImg, bigPlatform.x, bigPlatform.y, observer);	
	}
	
	public void drawMeatball (Meatball meatball, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(meatballImg, meatball.x, meatball.y, observer);
	}
	
	public void drawBossEnter (Boss boss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossEnterImg, boss.x, boss.y, observer);
	}
	
	public void drawBossFight (Boss bossF, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossFightImg, bossF.x, bossF.y, observer);
	}
	
	
	public void drawMegaMan (NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManImg, megaMan.x, megaMan.y, observer);	
	}
	public void drawMegaFallR (NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallRImg, megaMan.x, megaMan.y, observer);	
	}
	public void drawMegaFireR (NewMegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireRImg, megaMan.x, megaMan.y, observer);
	}
	
}