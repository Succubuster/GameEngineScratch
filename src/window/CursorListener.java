package window;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.event.MouseInputAdapter;

public class CursorListener extends MouseInputAdapter {

	public CursorListener() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
       Point2D p2d = (Point2D) e.getPoint();
//       Handler.getInstance().fireTowards(p2d);
    }
	@Override
    public void mouseReleased(MouseEvent e) {
       
    }
	@Override
    public void mouseEntered(MouseEvent e) {
//       saySomething("Mouse entered", e);
		Cursor c = new Cursor(Cursor.CROSSHAIR_CURSOR);
		Window.getFrame().setCursor(c);
    }
	@Override
    public void mouseExited(MouseEvent e) {
//       saySomething("Mouse exited", e);
		
    }
	@Override
    public void mouseClicked(MouseEvent e) {
       
	}
	@Override
	public void mouseMoved(MouseEvent e) {

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}


}
