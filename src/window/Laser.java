package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Laser extends GameObject {
	private Line2D path; // maybe be better for non-player motion or generalPath
	private GameObject origin;
	private Point2D start, dest;
	private double speed, slope;
	public Laser(double x, double y, double w, double h, Id i, int t) {
		super(x, y, w, h, i);
		type = t;
	}

	public Laser(double x, double y, Id i, int t) {
		super(x, y, i);
		width = 16;
		height = 16;
		type = t;
	}
	@Deprecated
	public Laser(Point2D s, Point2D d, Id i, int t) {
		this(i,t);
		start = s; dest = d;
		path = new Line2D.Double(s, d);
		setSlope(path);
	}
	
	public Laser(GameObject o, Point2D d, Id i, int t) {
		this(i,t);
		origin = o;
		dest = d;
		path = new Line2D.Double(o.center, d);
		setSlope(path);
		findClearing();
	}

	public Laser(Id i, int t) {
		super(i);
		type = t;
	}
	
	private void pathfinder() {
		
	}
	public void findClearing() {
		double px, py;
		for (px = origin.center.getX(), py = origin.center.getY(); 
				!getBoundsAt(px,py).intersects(origin.getBounds()); 
				px += velX, py += velY) {
			
		}
		setX(px);
		setY(py);
//		checkSpace(origin.center.x,origin.center.y,origin);
	}
	private boolean checkSpace(double px, double py, GameObject o) {
		if (!true) {
			
			return true;
		} else {
			return checkSpace(px+velX,py+velY,o);
		}
	}
	private void setSlope(Line2D dir) {
		slope = (dir.getY2() - dir.getY1())/(dir.getX2() - dir.getX1());
		setVelY(slope*speed);
		setVelX(speed);
	}
	private Rectangle2D getBoundsAt(double px, double py) {
		return new Rectangle2D.Double(px, py, width, height);
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.cyan);
		g2d.fill(getBounds());
	}

}
