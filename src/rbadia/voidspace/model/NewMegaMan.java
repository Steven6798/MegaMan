package rbadia.voidspace.model;

public class NewMegaMan extends GameObject {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 31;
	public static final int HEIGHT = 38;
	
	public static final int DEFAULT_SPEED = 5;
	public static final int Y_OFFSET = 5; // initial y distance of the ship from the bottom of the screen 
	
	public NewMegaMan(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}
	
	public int getInitialYOffset() {
		return Y_OFFSET;
	}
	
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}

}
