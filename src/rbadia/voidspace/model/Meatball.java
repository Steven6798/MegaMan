package rbadia.voidspace.model;

public class Meatball extends GameObject {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_MEATBALL_SPEED = 4;
	
	public static final int WIDTH = 90;
	public static final int HEIGHT = 70;
	
	public Meatball(int xPos, int yPos) {
		super(xPos, yPos, Meatball.WIDTH, Meatball.HEIGHT);
		this.setSpeed(DEFAULT_MEATBALL_SPEED);
	}
	
	public int getDefaultSpeed() {
		return DEFAULT_MEATBALL_SPEED;
	}
}
