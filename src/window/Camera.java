package window;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Camera {
	private static Camera instance = new Camera();
	private double x, y;
	private Camera() { }
	
	public void tick() {
		for (GameObject focus: Handler.getInstance().getChain()) {
			if (focus.getId() == Id.Player) {
				x = Game.WIDTH/2 - focus.getX();
				y = Game.HEIGHT/2 - focus.getY();
			}
		}
	}
	
//	public void render(Graphics g) {
//		Graphics2D g2d = (Graphics2D) g;
//	}
	
	
	
	public double getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public static Camera getInstance() {
		return instance;
	}
	
}
