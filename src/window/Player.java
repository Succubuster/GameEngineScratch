package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player extends GameObject {
	private KeyListener keyboard = null;
	public Player(double x, double y, double w, double h, Id i, int t) {
		super(x, y, w, h, i);
		type = t;
	}
	public Player(double x, double y, Id i, int t) {
		super(x,y,i);
		type = t;
	}
	public Player(Id i, int t) {
		super(i);
		type = t;
	}

	public void addListener() {
		keyboard = new KeyListener();// add parameter to make it focus on this player and maybe with set/dynamic controls
		Game.getInstance().addKeyListener(keyboard);
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (type == 0) g2d.setColor(Color.red);
		else if (type == 1) g2d.setColor(Color.blue);
		
		if (!debugging) 
			g2d.fill(getBounds());
		else {
			g2d.setColor(Color.yellow);
			g2d.draw(getTopBounds());
			g2d.setColor(Color.cyan);
			g2d.draw(getBottomBounds());
			g2d.setColor(Color.magenta);
			g2d.draw(getLeftBounds());
			g2d.setColor(Color.green);
			g2d.draw(getRightBounds());
		}
	}
}
