package rbadia.voidspace.model;

import java.awt.Rectangle;

public class BigPlatform extends Rectangle {
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 88;
	private static final int HEIGHT = 4;

	public BigPlatform(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
	}
}