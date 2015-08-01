package window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
//	private Handler handler;
	private int[][] playerKeys = {{87, 83, 68, 65},{73, 75, 76, 74}};
	public KeyListener () {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		for(GameObject o: Handler.getInstance().getChain()) {
			if(o.getId() == Id.Player){
				int type = ((Player) o).getType();
//				System.out.println(type);
				if(kc == playerKeys[type][0]) o.setVelY(-5); 
				if(kc == playerKeys[type][1]) o.setVelY(5); 
				if(kc == playerKeys[type][2]) o.setVelX(5); 
				if(kc == playerKeys[type][3]) o.setVelX(-5);
			} 			
		} if(kc == KeyEvent.VK_ESCAPE) Game.getInstance().switchToMenu();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();
		for(GameObject o: Handler.getInstance().getChain()) {
			if(o.getId() == Id.Player) {
				int type = ((Player) o).getType();
				if(kc == playerKeys[type][0]) o.setVelY(0); 
				if(kc == playerKeys[type][1]) o.setVelY(0); 
				if(kc == playerKeys[type][2]) o.setVelX(0); 
				if(kc == playerKeys[type][3]) o.setVelX(0);
			}
		}
	}
}


//public class KeyListener extends KeyAdapter{
//    private Handler handler;
//    private boolean[] keyDown = new boolean[4];
//    public KeyListener(Handler handler){
//            this.handler = handler;
//            keyDown[0] = false;
//            keyDown[1] = false;
//            keyDown[2] = false;
//            keyDown[3] = false;
//    }
//    public void keyPressed(KeyEvent e){
//            int key = e.getKeyCode();      
//            for(int i = 0; i < handler.getChain().size(); i++){
//                    GameObject tempObject = handler.getChain().get(i);
//                    if(tempObject.getId() == Id.Player){
//                            if(key == KeyEvent.VK_W) { tempObject.setVelY(-5); keyDown[0] = true;  }
//                            if(key == KeyEvent.VK_S) { tempObject.setVelY(5); keyDown[1] = true; }
//                            if(key == KeyEvent.VK_D) { tempObject.setVelX(5); keyDown[2] = true; }
//                            if(key == KeyEvent.VK_A) { tempObject.setVelX(-5); keyDown[3] = true; }
//                    }
//            }
//            if(key == KeyEvent.VK_ESCAPE) System.exit(1);
//    }
//    public void keyReleased(KeyEvent e){
//            int key = e.getKeyCode();      
//            for(int i = 0; i < handler.getChain().size(); i++){
//                    GameObject tempObject = handler.getChain().get(i);
//                    if(tempObject.getId() == Id.Player){
//                            if(key == KeyEvent.VK_W) keyDown[0] = false; //tempObject.setVelY(0);
//                            if(key == KeyEvent.VK_S) keyDown[1] = false; //tempObject.setVelY(0);
//                            if(key == KeyEvent.VK_D) keyDown[2] = false; //tempObject.setVelX(0);
//                            if(key == KeyEvent.VK_A) keyDown[3] = false; //tempObject.setVelX(0);
//                            if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
//                            if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
//                    }
//            }
//    }
//}
