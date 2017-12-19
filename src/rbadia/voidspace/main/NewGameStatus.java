package rbadia.voidspace.main;

public class NewGameStatus extends GameStatus{
	
	public NewGameStatus() {
		
	}
	
	private boolean newMeatball;
	
	public synchronized boolean isNewMeatball() {
		return newMeatball;
	}

	public synchronized void setNewMeatball(boolean newMeatball) {
		this.newMeatball = newMeatball;
	}
}
