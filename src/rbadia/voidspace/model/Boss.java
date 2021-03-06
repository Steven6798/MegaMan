package rbadia.voidspace.model;

public class Boss extends GameObject {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_BOSS_SPEED = 1;
	
	public static final int WIDTH = 218;
	public static final int HEIGHT = 217;
	
	public Boss(int xPos, int yPos) {
		super(xPos, yPos, Boss.WIDTH, Boss.HEIGHT);
		this.setSpeed(DEFAULT_BOSS_SPEED);
	}
	
	public int getDefaultSpeed() {
		return DEFAULT_BOSS_SPEED;
	}
}
