package window;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4942235602233720257L;
	
//	private Game game;
	private static JFrame frame;
	
	public Window (int width, int height, String title, Game g) {
		frame = new JFrame(title);

		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
		frame.add(g);
		frame.setVisible(true);
//		try{
//			game.start();
//		} catch (NullPointerException e) {
//			System.out.println("FAIL");
//			e.printStackTrace();
//		}
		
		
	} 
	public static JFrame getFrame() {
		return frame;
	}
	
	
}
