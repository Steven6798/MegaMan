package rbadia.voidspace.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.sounds.NewSoundManager;

/**
 * Main game class. Starts the game.
 */
public class MegaManMain {

	//Starts playing menu music as soon as the game frame is created

	public static AudioInputStream audioStream;
	public static Clip audioClip;
	public static File audioFile;

	public static void main(String[] args) throws InterruptedException, IOException {
		NewMainFrame frame = new NewMainFrame();              		// Main Game Window
		NewLevelLogic gameLogic = new NewLevelLogic();        		// Coordinates among various levels
		InputHandler inputHandler = new InputHandler(); 		// Keyboard listener
		NewGraphicsManager graphicsMan = new NewGraphicsManager(); // Draws all graphics for game objects
		NewSoundManager soundMan = new NewSoundManager();			// Loads and plays all sounds during the game
		
		frame.addKeyListener(inputHandler);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int playAgain = 2;
		while(playAgain != 1) {
			audioFile = new File("audio/menuScreen.wav");
			try {
				audioStream = AudioSystem.getAudioInputStream(audioFile);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			GameStatus gameStatus = new GameStatus();
			gameStatus.setLivesLeft(3);
			NewLevelState level1State = new NewLevel1State(1, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			NewLevelState level2State = new NewLevel2State(2, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			NewLevelState level3State = new Level3State(3, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			NewLevelState level4State = new Level4State(4, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			NewLevelState level5State = new Level5State(5, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			NewLevelState FinalBoss = new FinalBoss(6, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			NewLevelState levels[] = { level1State, level2State, level3State, level4State, level5State, FinalBoss };

			String outcome = "CONGRATS!! YOU WON!!";
			for (NewLevelState nextLevel : levels) {
				System.out.println("Next Level Started");
				frame.setNewLevelState(nextLevel);
				gameLogic.setNewLevelState(nextLevel);
				inputHandler.setNewLevelState(nextLevel);
				gameStatus.setLevel(nextLevel.getLevel());
				
				frame.setVisible(true);
				startInitialMusic();

				// init main game loop
				Thread nextLevelLoop = new Thread(new NewLevelLoop(nextLevel));
				nextLevelLoop.start();
				nextLevelLoop.join();
				if (nextLevel.getGameStatus().isGameOver()) {
					outcome = "SORRY YOU LOST";
					break;
				}
			}
			playAgain = JOptionPane.showConfirmDialog(null, outcome + " ... Play Again?", "", JOptionPane.YES_NO_OPTION);
		}
		System.exit(0);
	}
	
	public static void startInitialMusic() throws InterruptedException, IOException {
		// allows music to be played while playing
		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);

		try {
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}