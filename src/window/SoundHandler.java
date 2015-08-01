package window;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Dated 5/28/15
 * So far, this static class can manage most audio files 
 * and designate them properly. But properly only plays one: 
 * thus a looping method needs to be made
 * 
 * 
 * @author cmking
 *
 */
public class SoundHandler {
	
	private static SoundHandler instance = new SoundHandler();
	
//	private ArrayList<String> soundNames;
//	private ArrayList<AudioData> sounds;
	
	private AudioPlayer BGM;
	//private AudioPlayer SFX; // only needs to be instaniated when used
	private HashMap<String,String> KeyToFile = new HashMap<String,String>();
	private final String BACKGROUND = "!";
	private final String SFX = "-";
	
	private SoundHandler() {
		
	}
	
	private void addSound(String action, String filename) {
//		System.out.println("Listen for sample sound...");
		String checkedFilename = filename; //= checkFile(filename); // will check assuming .exists works for audio files
		//AudioPlayer temp = new AudioPlayer(checkedFilename);
//		temp.play();
		KeyToFile.put(action, filename);
		System.out.println("Sound Command " + action.substring(1) + " was successfully added.");
	}	
	
	private String checkFile(String fn) { // does not work for audio files, need to brainstorm
		String properfn = fn;
		File temp = new File(fn);
		Scanner console = new Scanner(System.in);
		while (!temp.exists()) {
			System.out.println("Problem occurred. Please re-enter your filename...");			
			properfn = console.nextLine().trim();
			temp = new File(properfn);
		} console.close(); return properfn;
	}
 	public void addBackgroundSound(String action, String filename) {
		addSound(BACKGROUND+action,filename);
	}
	public void addSoundEffect(String action, String filename) {
		addSound(SFX+action,filename);
	}
	
	public void removeSound(String action, String filename) {
		KeyToFile.remove(action);
	}
	
	public void play(String action) {
		if (KeyToFile.get(BACKGROUND+action) != null) {
			playBackgroundSound(KeyToFile.get(BACKGROUND+action));
		} else if (KeyToFile.get(SFX+action) != null) {
			playSoundEffect(KeyToFile.get(SFX+action));
		} 
		
	}
	private void playBackgroundSound(String fn) {
		BGM = new AudioPlayer(fn);
		BGM.play();
	}
	private void playSoundEffect(String fn) {
		AudioPlayer sfxPlayer = new AudioPlayer(fn);
		sfxPlayer.play();
	}
	
	public static SoundHandler getInstance() {
		return instance;
	}
}
