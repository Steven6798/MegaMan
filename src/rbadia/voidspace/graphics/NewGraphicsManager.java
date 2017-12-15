package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Meatball;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Spaghetti;

public class NewGraphicsManager extends GraphicsManager{
	
	private BufferedImage megaManLImg;
	private BufferedImage megaFallLImg;
	private BufferedImage megaFireLImg;
	private BufferedImage meatball;
	private BufferedImage bossEnter;
	private BufferedImage bossFight;
	private BufferedImage spaghetti;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public NewGraphicsManager(){
		// load images
		try {
			this.megaManLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManL.png"));
			this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallL.png"));
			this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireL.png"));
			this.meatball = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Meatball.png"));
			this.bossEnter = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossEnter.png"));
			this.bossFight = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossFight.png"));
			this.spaghetti = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Spaghetti.png"));
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

	public void drawMegaManL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFallL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallLImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFireL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireLImg, megaMan.x, megaMan.y, observer);	
	}
	
	/**
	 * Draws Meatball projectile
	 * @param meatball
	 * @param g2d
	 * @param observer
	 */
	public void drawMeatball (Meatball meatball, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(this.meatball, meatball.x, meatball.y, observer);
	}
	
	/**
	 * Draws FinalBoss entering
	 * @param boss
	 * @param g2d
	 * @param observer
	 */
	public void drawBossEnter (Boss boss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossEnter, boss.x, boss.y, observer);
	}
	
	/**
	 * Draws FinalBoss in ready state
	 * @param boss
	 * @param g2d
	 * @param observer
	 */
	public void drawBossFight (Boss boss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossFight, boss.x, boss.y, observer);
	}
	
	/**
	 * Draws Spaghetti
	 * @param spaghetti
	 * @param g2d
	 * @param observer
	 */
	public void drawSpaghetti(Spaghetti spaghetti, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(this.spaghetti, spaghetti.x, spaghetti.y, observer);
	}
}