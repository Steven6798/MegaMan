package rbadia.voidspace.model;

public class Spaghetti extends GameObject {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPAGHETTI_SPEED = 6;
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 100;
	
	public Spaghetti(int xPos, int yPos) {
		super(xPos, yPos, Spaghetti.WIDTH, Spaghetti.HEIGHT);
		this.setSpeed(DEFAULT_SPAGHETTI_SPEED);
	}
	
	public int getDefaultSpeed() {
		return DEFAULT_SPAGHETTI_SPEED;
	}
}
