package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Block extends GameObject {

	public Block(double x, double y, double w, double h, Id i, int t) {
		super(x, y, w, h, i);
		type = t;
	}

	public Block(double x, double y, Id i, int t) {
		super(x, y, i);
		type = t;
	}

	public Block(Id i, int t) {
		super(i);
		type = t;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(255,0,255));
		g2d.draw(getBounds());

	}

}
