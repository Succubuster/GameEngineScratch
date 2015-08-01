package window;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Handler {
	
	private static Handler instance = new Handler();
	
	private LinkedList<GameObject> chain = new LinkedList<GameObject>();
	private ControllerListener gb;

	private boolean playerEstablished = false;
	private Handler() {
		SoundHandler.getInstance().addBackgroundSound("level","/LEVEL_MUSIC.mp3");
//		SoundHandler.getInstance().play("level");
//		MapHandler.getInstance().mapReader();
		
		
		
	}
	
	public void tick() {
//		if (!playerEstablished) {
//			for (GameObject o: chain) {
//				if (o.getId() == Id.Player) {
//					gb = new ControllerListener(o);//make it pass it to the player(s)
//					playerEstablished = true;
//				}
//			}
//		} else {
//			gb.tick();
//		}
//		
		for (int i = 0; i < chain.size(); i++) {
			GameObject o = chain.get(i);
			if (o.isAlive()) {
				o.tick();
				collision(o);
			} else 
				chain.remove(o); // may not work, but if does, setting health to 0 will remove it 
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < chain.size(); i++) {
			GameObject o = chain.get(i);
			o.render(g);
		}
	}
	
	public void addObject(GameObject obj) {
//		if (obj.getId() == Id.Player) Game.getInstance().playerPresent = true;
//		while (!findPlacementFor(obj)) {
//			obj.setX(obj.getX() + 1);
//			obj.setY(obj.getY() + 1);
//		}
		
		chain.add(obj);
	}
	public boolean findPlacementFor(GameObject obj) {
		if (chain.isEmpty()) return true;
		for (GameObject o: chain) {
			if (obj.collidesWith(o)) {
				return false;
			}
		} return true;
	}
	public void removeObject(GameObject obj) {
		chain.remove(obj);
	}

	public LinkedList<GameObject> getChain() {
		return chain;
	}
	
	public static Handler getInstance() {
		return instance;
	}
	
	public void fireTowards(Point2D p) {
		for (int i = 0; i < chain.size(); i++) {
			GameObject o = chain.get(i);
			if (o.getId() == Id.Player) {
				addObject(new Laser(o,p,Id.Laser,0));
			}
		}
	}
	
	private void collision(GameObject bumper) {
		for (GameObject bumpee: chain) {
			if (bumper != bumpee) {
				if (bumper.collidesWith(bumpee)) {
					if (bumper.getId() == Id.Player) {
						Player player = (Player) bumper;
						
//						if (bumper.getTopBounds().intersects(bumpee.getBounds())) {
//							bumper.setY(bumpee.getY()+bumper.getHeight()); 
//						}
//						if (bumper.getBottomBounds().intersects(bumpee.getBounds())) {
//							bumper.setY(bumpee.getY()-bumper.getHeight());
//						}
//						if (bumper.getLeftBounds().intersects(bumpee.getBounds())) {
//							bumper.setX(bumpee.getX()+bumper.getWidth());
//						}
//						if (bumper.getRightBounds().intersects(bumpee.getBounds())) {
//							bumper.setX(bumpee.getX()-bumper.getWidth());
//						}
					} else if (bumper.getId() == Id.Laser) {
						Laser laser = (Laser) bumper;
						laser.setHealth(0);
					} else if (bumper.getId() == Id.Enemy) {
//						Enemy enemy = (Enemy) bumper;
					}
					// fix below, only access collision bounds for movable objs
					// this format:
					// if (nonmove)
					//   health management
					// else
					//   health management
					//   collision management
					
				}
			}
		}
	}
}