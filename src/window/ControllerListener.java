package window;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerListener {

	private boolean emptyHanded = true;
	private Controller controller;
	private GameObject player;
	//	private static ControllerListener instance = new ControllerListener();

	public ControllerListener(GameObject p) { // constr's will be changed for multi support
		System.out.println("Sydnee");
		player = p;
		while (controller == null) {
			System.out.println("Check to make sure the controller is on...");
			for (Controller c: ControllerEnvironment.getDefaultEnvironment().getControllers()) {
				if (c.getType() == Controller.Type.GAMEPAD) {
					controller = c; System.out.println("Controller connected."); emptyHanded = false; break;
				} 
			} 
		}
	}

	public ControllerListener(Controller con) {
		controller = con; System.out.println("Controller connected."); emptyHanded = false;
	}

	public void tick() {
//		player = p;
		for (GameObject o: Handler.getInstance().getChain()) {
			if (o.getId() == Id.Player)
				player = o;
		}
		if (!emptyHanded) {
			grabQueue();
		}

	}
	private void pollForController() {
		for (Controller c: ControllerEnvironment.getDefaultEnvironment().getControllers()) {	
			//			System.out.println(c.getName() + " " + c.getType());
			//			for (Rumbler r: c.getRumblers()) {
			//				r.rumble(1000);
			//			}

			//			EventQueue queue = c.getEventQueue();
			//			Event event = new Event();
			//			
			//			queue.getNextEvent(event);
			//			Component com = event.getComponent();
			//			System.out.println(com);
		}

	}
	private void grabQueue() {
		controller.poll();
		EventQueue queue = controller.getEventQueue();
		Event event = new Event();
		while(queue.getNextEvent(event)) {
			StringBuffer sb = new StringBuffer(controller.getName());
			sb.append(" at ");
			sb.append(event.getNanos()).append(", ");
			Component comp = event.getComponent();


			if (comp.getName().equals("X Axis")) {
				if (event.getValue() > 0.5 || event.getValue() < -0.5)
					player.setVelX(event.getValue()*5);
				else 
					player.setVelX(0);
			} else if (comp.getName().equals("Y Axis")) {
				if (event.getValue() > 0.5 || event.getValue() < -0.5)
					player.setVelY(event.getValue()*5);
				else 
					player.setVelY(0);
			} else if (comp.getName().equals("X Rotation") || comp.getName().equals("Y Rotation")) {
				boolean direction [] = {false, false, false, false}; 
				boolean create = false;
				double speedX = 0, speedY= 0;
				if (comp.getName().equals("X Rotation")) {
					if (event.getValue() > 0.9) {
						direction[3] = true; // right
						speedX = event.getValue()*5;
						create = true;
					} else if (event.getValue() < -0.9) {
						direction[2] = true; // left
						speedX = event.getValue()*5;
						create = true;
					}
				}
				if (comp.getName().equals("Y Rotation")) {
					if (event.getValue() > 0.9) {
						direction[1] = true; // down
						speedY = event.getValue()*5;
						create = true;
					} else if (event.getValue() < -0.9) {
						direction[0] = true; // up
						speedY = event.getValue()*5;
						create = true;
					}
				}
				if (create) {
					double lx = 0, ly = 0;
					if (direction[0]) {
						if (direction[2]) { //up-left
							lx = player.center.getX()-((player.width+16)/2);
							ly = player.center.getY()-((player.height+16)/2);
						} else if (direction[3]) { //up-right
							lx = player.center.getX()+((player.width+16)/2);
							ly = player.center.getY()-((player.height+16)/2);
						} else { //up
							lx = player.center.getX();
							ly = player.center.getY()-((player.height+16)/2);
						}
					} else if (direction[1]) {
						if (direction[2]) { //down-left
							lx = player.center.getX()-((player.width+16)/2);
							ly = player.center.getY()+((player.height+16)/2);
						} else if (direction[3]) { //down-right
							lx = player.center.getX()+((player.width+16)/2);
							ly = player.center.getY()+((player.height+16)/2);
						} else { //down
							lx = player.center.getX();
							ly = player.center.getY()+((player.height+16)/2);
						}
					} else {
						if (direction[2]) { //left
							lx = player.center.getX()-((player.width+16)/2);
							ly = player.center.getY();
						} else if (direction[3]) { //right
							lx = player.center.getX()+((player.width+16)/2);
							ly = player.center.getY();
						}
					}
					Laser l = new Laser(lx,ly,16,16,Id.Laser,0);
					l.setVelX(speedX);
					l.setVelY(speedY);
					Handler.getInstance().addObject(l);
					create = false;
				}
			} else if (comp.getName().equals("Button 0")) {
				if (event.getValue() == 1.0f) {
					Laser l = new Laser(player.getX()+100,player.getY(),16,16,Id.Laser,0);
					l.setVelX(5);
					Handler.getInstance().addObject(l);
				}
			} else if (comp.getName().equals("Button 6"))
				if (event.getValue() == 1.0f)
					System.exit(0);


			sb.append(comp.getName()).append(" changed to ");
			float value = event.getValue(); 
			if(comp.isAnalog()) {
				sb.append(value);
			} else {
				if(value==1.0f) {
					sb.append("On");
				} else {
					sb.append("Off");
				}
			} System.out.println(sb.toString()); 
		}
	}

	//	public static ControllerListener getInstance() {
	//		return instance;
	//	}
	//	
	// Add public connect(Controller c)  and disconnect(); then use in constr

	//	for (Identifier i: c.getIdentifier())
	//	if (c.getName().equals("X Axis")) {
	//        dx += c.getPollData();
	//    } else if (c.getName().equals("Button ")) {
	//    	Toolkit.getDefaultToolkit().beep();
	//    } .equals("Controller (Xbox 360 Wireless Receiver for Windows)"



}
