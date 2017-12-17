package rbadia.voidspace.main;

/**
 * Implements the main game loop, i.e. what actions should be taken on each frame update.
 */
public class NewLevelLoop implements Runnable {
	private NewLevelState newLlevelState;
	private NewLevelLogic newGameLogic;
	private InputHandler inputHandler;

	/**
	 * Creates a new game loop.
	 * @param newLevelState the game screen
	 * @param newGameLogic the game logic handler
	 * @param inputHandler the user input handler
	 */
	public NewLevelLoop(NewLevelState levelState) {
		this.newLlevelState = levelState;
		this.newGameLogic = levelState.getGameLogic();
		this.inputHandler = levelState.getInputHandler();
	}

	/**
	 * Implements the run interface method. Should be called by the running thread.
	 */
	public void run() {
		newLlevelState.doStart();

		while(!newLlevelState.getGameStatus().isGameOver() && !newLlevelState.isLevelWon()) {
			// update the game graphics and repaint screen
			newGameLogic.stateTransition(inputHandler, newLlevelState);
			newLlevelState.repaint();
			
			NewLevelLogic.delay(1000/60);
			
//			try{
//				// sleep/wait for 1/60th of a second,
//				// for a resulting refresh rate of 60 frames per second (fps) 
//				Thread.sleep(1000/60);
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
		}
		if (newLlevelState.isLevelWon()) newLlevelState.doLevelWon();
		else newLlevelState.doGameOverScreen();
	}

}