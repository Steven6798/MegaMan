package rbadia.voidspace.main;

import java.awt.Graphics2D;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Meatball;
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

}