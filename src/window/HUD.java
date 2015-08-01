package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class HUD {

	public HUD() {
		// TODO Auto-generated constructor stub
	}
	
	public static int HEALTH = 100, EXP = 0, SCORE = 0;
//	private int UNIT_SIZE = HEALTH/player.getHealth();
	private int greenValue = 255, change = 0;
	
	public void tick() {
		if (HEALTH == 100)
			change = -1;
		if (HEALTH == 0)
			change = 1;
//		HEALTH += change;
		greenValue = HEALTH*2;
		HEALTH = Game.clamp(HEALTH, 0, 100);
		greenValue = Game.clamp(greenValue, 0, 255);
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// use draw for outline
		g2d.setColor(Color.gray);
		g2d.fill(new Rectangle2D.Double(50,15,200,32));
		g2d.fill(new Rectangle2D.Double(10,6,32,50));
//		g2d.drawString(str, x, y);
		
		g2d.setColor(new Color(100,greenValue,0));
		g2d.fill(new Rectangle2D.Double(50,15,HEALTH*2,32));
	}
}
