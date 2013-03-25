package support;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class COMouseFocusListener implements MouseListener {

	DualColorComponent model;
	
	public COMouseFocusListener(DualColorComponent model){
		this.model = model;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.model.requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
