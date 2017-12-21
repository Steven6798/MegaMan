package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Handles general level logic and status.
 */
public class NewLevelLogic extends LevelLogic {

	private NewLevelState newLevelState;

	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public NewLevelLogic() {
	}

	public NewLevelState getNewLevelState() {
		return newLevelState;
	}

	public void setNewLevelState(NewLevelState newLevelState) {
		this.newLevelState = newLevelState;
	}

	@Override
	public void gameOver() {
		getNewLevelState().getGameStatus().setGameOver(true);

		newLevelState.doGameOverScreen();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getNewLevelState().getGameStatus().setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();

		//Change music back to menu screen music
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/menuScreen.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void gameWon() {
		//status.setGameStarted(false);  //SENDS TO MAIN SCREEN/ IF COMMENTED OUT LOOPS THE GAME
		getNewLevelState().getGameStatus().setGameWon(true);
		newLevelState.doLevelWon();

		// delay to display "Game Won" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getNewLevelState().getGameStatus().setGameWon(false);
			}
		});
		timer.setRepeats(false);
		timer.start();

		//Change music back to menu screen music
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/menuScreen.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void drawGameOver() {

		NewLevelState newLevelState = getNewLevelState();
		Graphics2D g2d = newLevelState.getGraphics2D();

		// set original font - for later use
		if(this.originalFont == null) {
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String gameOverStr = "Game Over";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > newLevelState.getWidth() - 10) {
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (newLevelState.getWidth() - strWidth) / 2;
		int strY = (newLevelState.getHeight() + ascent) / 2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.RED);
		g2d.drawString(gameOverStr, strX, strY);
	}

	@Override
	protected void drawYouWin() {

		NewLevelState newLevelState = getNewLevelState();
		Graphics2D g2d = newLevelState.getGraphics2D();

		// set original font - for later use
		if(this.originalFont == null) {
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String youWinStr = "Level " + newLevelState.getLevel() + " Completed";

		//Font currentFont = biggestFont == null? bigFont : biggestFont;
		Font currentFont = originalFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 5).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(youWinStr);
		if(strWidth > newLevelState.getWidth() - 10) {
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(youWinStr);
		}
		int ascent = fm.getAscent();
		int strX = (newLevelState.getWidth() - strWidth) / 2;
		int strY = (newLevelState.getHeight() + ascent) / 2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(youWinStr, strX, strY);
	}

	@Override
	protected void drawInitialMessage() {

		NewLevelState newLevelState = getNewLevelState();
		Graphics2D g2d = newLevelState.getGraphics2D();

		if(this.originalFont == null) {
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String gameTitleStr = "MegaBOY !!!";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > newLevelState.getWidth() - 10) {
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (newLevelState.getWidth() - strWidth) / 2;
		int strY = (newLevelState.getHeight() + ascent) / 2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start the Level";
		strWidth = fm.stringWidth(newGameStr);
		strX = (newLevelState.getWidth() - strWidth) / 2;
		strY = (newLevelState.getHeight() + fm.getAscent()) / 2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String itemGameStr = "Press <I> for Item Menu.";
		strWidth = fm.stringWidth(itemGameStr);
		strX = (newLevelState.getWidth() - strWidth) / 2;
		strY = strY + 16;
		g2d.drawString(itemGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String shopGameStr = "Press <S> for Shop Menu.";
		strWidth = fm.stringWidth(shopGameStr);
		strX = (newLevelState.getWidth() - strWidth) / 2;
		strY = strY + 16;
		g2d.drawString(shopGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (newLevelState.getWidth() - strWidth) / 2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	@Override
	protected void drawGetReady() {
		NewLevelState NewlevelState = getNewLevelState();
		Graphics2D g2d = NewlevelState.getGraphics2D();

		if(this.originalFont == null) {
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String readyStr = "Get Ready for Level " + NewlevelState.getLevel();
		String AstDest = NewLevel1State.getDestroyText();
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (NewlevelState.getWidth() - strWidth) / 2;
		int strY = (NewlevelState.getHeight() + ascent) / 2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
		g2d.drawString(AstDest, strX, strY + 20);
	}

	public void stateTransition(InputHandler ih, NewLevelState newLevelState) {
		GameStatus status = getNewLevelState().getGameStatus();
		switch (newLevelState.getCurrentState()) {
		case NewLevelState.START_STATE:
			newLevelState.setCurrentState(NewLevelState.INITIAL_SCREEN);
			break;

		case NewLevelState.INITIAL_SCREEN:
			newLevelState.doInitialScreen();
			handleKeysDuringInitialScreen(ih, newLevelState);
			break;

		case NewLevelState.GETTING_READY:
			newLevelState.doGettingReady();
			newLevelState.setCurrentState(NewLevelState.PLAYING);
			break;

		case NewLevelState.PLAYING:
			newLevelState.doPlaying();
			handleKeysDuringPlay(ih, newLevelState);
			if(status.getLivesLeft() == 0) {
				newLevelState.setCurrentState(NewLevelState.GAME_OVER_SCREEN);
			}
			if(newLevelState.isLevelWon()) {
				newLevelState.setCurrentState(NewLevelState.LEVEL_WON);
			}
			break;

		case NewLevelState.NEW_MEGAMAN:
			// TODO Verify that this state is activated when MegaMan dies
			break;

		case NewLevelState.GAME_OVER_SCREEN:
			newLevelState.doGameOverScreen();
			newLevelState.setCurrentState(NewLevelState.GAME_OVER);
			break;

		case NewLevelState.GAME_OVER:
			newLevelState.doGameOver();
			break;

		case NewLevelState.LEVEL_WON:
			newLevelState.doLevelWon();
			break;
		}
	}

	public void handleKeysDuringInitialScreen(InputHandler ih, NewLevelState newLevelState) {
		if(ih.isSpacePressed()) {
			ih.reset();
			newLevelState.setCurrentState(NewLevelState.GETTING_READY);	
		}
		if(ih.isSPressed()) {
			JOptionPane.showMessageDialog( null, 
					"Item:                Price\r\n"+
							"\r\n"+
							"Extra Life:      1500\r\n"+ 
							"Power Shot:   500\r\n"+
					"\r\n");
		}
		if(ih.isIPressed()) {
			JOptionPane.showMessageDialog( null, 
					"Power Up:     Explanation\r\n"+
							"\r\n"+
							"Extra Life:      Gives an extra life (One Extra Life per second)\r\n"+ 
							"                           (Press E to buy, limit of one life per second.)\r\n" +
							"Power Shot:  Activates the Power Shot which kills the asteroid in one hit\r\n"+
					"                           (Press Q to buy, afterwards press Q to fire.)\r\n");
		}
		ih.reset();
	}

	public void handleKeysDuringPlay(InputHandler ih, NewLevelState newLevelState) {

		GameStatus status = getNewLevelState().getGameStatus();

		// fire bullet if space is pressed
		if(ih.isSpacePressed()) {
			// fire only up to 5 bullets per second
			stack = 0;
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBulletTime) > 1000/5) {
				lastBulletTime = currentTime;
				getNewLevelState().fireBullet();
			}
		}
		
		if(ih.isEPressed()) {
			if(status.getAsteroidsDestroyed() >= 1500) {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastExchangeTime > 1000)) {
					lastExchangeTime = currentTime;
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() - 1500);
					status.setLivesLeft(status.getLivesLeft() + 1);
				}
			}
		}
		
		if(ih.isQPressed()) {
			if(stack == 0 && status.getAsteroidsDestroyed() >= 500) {
				stack++;
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() - 500);
			}
			else if(stack >= 1) {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBigBulletTime) > 1000) {
					lastBigBulletTime = currentTime;
					getNewLevelState().fireBigBullet();
				}

			}
		}
		
		if(ih.isZPressed()) {
			getNewLevelState().slowDownMegaMan();
		}
		
		if(ih.isXPressed()) {
			getNewLevelState().speedUpMegaMan();
		}
		
		if(ih.isNPressed()) {
			getNewLevelState().skipLevel();
		}

		if(ih.isUpPressed()) {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBigBulletTime) > 570) {
				lastBigBulletTime = currentTime;
				for(int i = 0; i < 6; i++) {
					getNewLevelState().moveMegaManUp();
				}
			}
		}

		if(ih.isDownPressed()) {
			getNewLevelState().moveMegaManDown();
		}

		if(ih.isLeftPressed()) {
			getNewLevelState().moveMegaManLeft();
		}

		if(ih.isRightPressed()) {
			getNewLevelState().moveMegaManRight();
		}
	}

}