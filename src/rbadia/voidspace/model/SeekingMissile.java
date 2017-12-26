package rbadia.voidspace.model;

/**
 * Represents a missile fired from the ship.
 */
public class SeekingMissile extends GameObject {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	public static final  int WIDTH = 20;
	public static final int HEIGHT = 7;
	
	public SeekingMissile(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(12);
	}
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}