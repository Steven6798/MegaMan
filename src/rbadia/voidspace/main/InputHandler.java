package rbadia.voidspace.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles user input events.
 */
public class InputHandler implements KeyListener {
	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean downIsPressed;
	private boolean upIsPressed;
	private boolean spaceIsPressed = false;
	private boolean zIsPressed; // slow megaman
	private boolean xIsPressed; // boost megaman
	private boolean eIsPressed; // extra live
	private boolean qIsPressed; // power shot
	private boolean wIsPressed; // heat-seeking missile
	private boolean mIsPressed; // pause music
	private boolean nIsPressed; // level skip
	private boolean sIsPressed; // shop
	private boolean iIsPressed; // inventory

	private LevelState levelState;
	private NewLevelState newLevelState;

	public LevelState getLevelState() {
		return levelState;
	}
	public NewLevelState getNewLevelState() {
		return newLevelState;
	}
	public void setLevelState(LevelState levelState) {
		this.levelState = levelState;
	}
	public void setNewLevelState(NewLevelState newLevelState) {
		this.newLevelState = newLevelState;
	}
	
	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler() {
		reset();
	}

	public void reset() {
		leftIsPressed = false;
		rightIsPressed = false;
		downIsPressed = false;
		upIsPressed = false;
		spaceIsPressed = false;
		zIsPressed = false;
		xIsPressed = false;
		eIsPressed = false;
		qIsPressed = false;
		wIsPressed = false;
		mIsPressed = false;
		nIsPressed = false;
		sIsPressed = false;
		iIsPressed = false;
	}

	public boolean isLeftPressed() {
		return leftIsPressed;
	}

	public boolean isRightPressed() {
		return rightIsPressed;
	}

	public boolean isDownPressed() {
		return downIsPressed;
	}

	public boolean isUpPressed() {
		return upIsPressed;
	}

	public boolean isSpacePressed() {
		return spaceIsPressed;
	}
	
	public boolean isZPressed() {
		return zIsPressed;
	}
	
	public boolean isXPressed() {
		return xIsPressed;
	}

	public boolean isEPressed() {
		return eIsPressed;
	}

	public boolean isQPressed() {
		return qIsPressed;
	}
	
	public boolean isWPressed() {
		return wIsPressed;
	}

	public boolean isMPressed() {
		return mIsPressed;
	}
	
	public boolean isNPressed() {
		return nIsPressed;
	}

	public boolean isSPressed() {
		return sIsPressed;
	}

	public boolean isIPressed() {
		return iIsPressed;
	}

	/**
	 * Handle a key input event.
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.upIsPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = true;
			break;
		case KeyEvent.VK_Z:
			this.zIsPressed = true;
			break;
		case KeyEvent.VK_X:
			this.xIsPressed = true;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		case KeyEvent.VK_E:
			this.eIsPressed = true;
			break;
		case KeyEvent.VK_Q:
			this.qIsPressed= true;
			break;
		case KeyEvent.VK_W:
			this.wIsPressed= true;
			break;
		case KeyEvent.VK_M:
			if(!mIsPressed) {
				this.getNewLevelState().pauseMusic();
			}
			this.mIsPressed= true;
			break;
		case KeyEvent.VK_N:
			this.nIsPressed= true;
			break;
		case KeyEvent.VK_S:
			this.sIsPressed = true;
			break;
		case KeyEvent.VK_I:
			this.iIsPressed = true;
			break;
		}
		e.consume();
	}

	/**
	 * Handle a key release event.
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.upIsPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = false;
			break;
		case KeyEvent.VK_Z:
			this.zIsPressed = false;
			break;
		case KeyEvent.VK_X:
			this.xIsPressed = false;
			break;
		case KeyEvent.VK_E:
			this.eIsPressed = false;
			break;
		case KeyEvent.VK_Q:
			this.qIsPressed = false;
			break;
		case KeyEvent.VK_W:
			this.wIsPressed = false;
			break;
		case KeyEvent.VK_M:
			this.mIsPressed = false;
			break;
		case KeyEvent.VK_N:
			this.nIsPressed = false;
			break;
		case KeyEvent.VK_S:
			this.sIsPressed = false;
			break;
		case KeyEvent.VK_I:
			this.iIsPressed = false;
			break;
		}
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// not used
	}
}