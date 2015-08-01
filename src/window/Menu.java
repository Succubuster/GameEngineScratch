package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Vector;

public class Menu extends MouseAdapter{
	private Point2D lastPlace = new Point2D.Double(0,0);
	private Vector<MenuOption> opt = new Vector<MenuOption>();
	private HashMap<String,MenuOption> optionHolder = new HashMap<String,MenuOption>();
	
	public Menu () {
		
		optionHolder.put("Resume", new MenuOption(250,100,100,32,"Resume",Color.white));
		optionHolder.put("Quit", new MenuOption(250,300,100,32,"Quit",Color.white));
//		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
//		Image image = toolkit.getImage("D:/images/mousepoint.jpg");
//		Cursor a = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "");
//		Window.getFrame().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}
	@Override
	public void mousePressed(MouseEvent e) {
		double mx = e.getX(), my = e.getY();
		lastPlace = new Point2D.Double(mx, my);
		for (String command: optionHolder.keySet()) {
			MenuOption choice = optionHolder.get(command);
			if (choice.isClicked(lastPlace)) {
				
				if (choice.equals("Resume")) {
					Game.getInstance().switchToGame();
				} 
				else if (choice.equals("Quit")) {
					System.exit(0);
				}
				
			}
		}
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public void tick() {
		
	}
//	private boolean isMousedOver() {
//		for (MenuOption mo: opt) {
//			if (mo.getBounds().contains(lastPlace)) {
//				
//			}
//		}
//	}
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// clear background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g2d.setColor(Color.blue);
		g2d.fill(new Rectangle2D.Double(lastPlace.getX(),lastPlace.getY(),10,10));;
		
		for (MenuOption mo : optionHolder.values()) {
			mo.render(g2d);
		}
//		g2d.setColor(Color.white);
//		g2d.drawString("Resume", 265, 120);
//		g2d.draw(new Rectangle2D.Double(250,100,100,32));
//		
//		g2d.drawString("Help", 265, 220);
//		g2d.draw(new Rectangle2D.Double(250,200,100,32));
//		
//		g2d.drawString("Quit", 265, 320);
//		g2d.draw(new Rectangle2D.Double(250,300,100,32));
	}
//	//CUSTOM CURSOR SETTING
//	java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
//	java.awt.Image image = toolkit.getImage("D:/images/mousepoint.jpg");
//	java.awt.Cursor a = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "");
//	this.setCursor (a);
//	
	private class MenuOption {
		double x, y, width, height;
		String text;
		Color color;
		public MenuOption(double x, double y, double w, double h, String t, Color c) {
			this.x = x;
			this.y = y;
			width = w;
			height = h;
			text = t;
			color = c;
		}
		public Rectangle2D getBounds() {
			return new Rectangle2D.Double(x,y,width,height);
		}
		public boolean equals(String s) {
			return text.equals(s);
		}
		public boolean isClicked(Point2D cursor) {
			return getBounds().contains(cursor);
		}
		public void render(Graphics2D g2d) {
			g2d.setColor(color);
			g2d.drawString(text, (int)x+15, (int)y+15);
			g2d.draw(getBounds());
		}
	}
} 