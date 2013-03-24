package support;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class COPasteKeyListener implements KeyListener {

	DualColorComponent model;
	
	public COPasteKeyListener(DualColorComponent model){
		this.model = model;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
			if(event.getModifiers() == 4 && event.getKeyChar() == 'v'){
				this.model.paste();
			}
	}
}