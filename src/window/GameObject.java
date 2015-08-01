package window;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {
	
	protected Id id; // chosen from an enum (a fancy list)
	protected int type = 0;
	protected double x = 100, y = 100, width = 32, height = 32, velX = 0, velY = 0, health = 1;
	protected boolean falling, jumping, debugging = false;
	protected Point2D.Double center;
	
	public GameObject(double x, double y, double w, double h, Id i) {
		this.x = x; 
		this.y = y;
		width = w;
		height = h;
		id = i;
		center = new Point2D.Double(x+(width/2), y+(height/2));
	}	
	public GameObject(double x, double y, Id i) {
		this.x = x; 
		this.y = y;
		id = i;
		center = new Point2D.Double(x+(width/2), y+(height/2));
	}	
	public GameObject(Id i) {
		id = i;
		center = new Point2D.Double(x+(width/2), y+(height/2));
	}
	public GameObject(Point2D c, double w, double h, Id i, int t) {
		
	}
	
	
	public abstract void tick();
	public abstract void render(Graphics g);
//	public abstract void collision(Handler handler);
	
	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(x, y, width, height);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public Id getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean equals(GameObject o) {
		return x == o.x && y == o.y && id == o.id;
	}
	
	public boolean collidesWith(GameObject o) {
		return getBounds().intersects(o.getBounds());
	}
	
	private int squeeze = 1;
	private double topBottomSpace = Math.sqrt(width);
	public Line2D getTopBounds() {
		return new Line2D.Double(x+topBottomSpace, y, x+width-topBottomSpace, y);
	}
	public Line2D getBottomBounds() {
		return new Line2D.Double(x+topBottomSpace, y+height, x+width-topBottomSpace, y+height);
	}
	public Line2D getLeftBounds() {
		return new Line2D.Double(x+squeeze, y+squeeze, x+squeeze, y+height-squeeze);
	}
	public Line2D getRightBounds() {
		return new Line2D.Double(x+width-squeeze, y+squeeze, x+width-squeeze, y+height-squeeze);
	}
	
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public boolean isAlive() {
		return health != 0;
	}
	public boolean isDebugging() {
		return debugging;
	}
	public void setDebugging(boolean debugging) {
		this.debugging = debugging;
	}
	public Point2D.Double getCenter() {
		return center;
	}
}
