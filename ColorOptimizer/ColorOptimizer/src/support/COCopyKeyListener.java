package support;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class COCopyKeyListener implements KeyListener {

	DualColorComponent model;
	
	public COCopyKeyListener(DualColorComponent model){
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
		if(event.getModifiers() == Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() && event.getKeyChar() == 'c'){
			this.model.copy();
		}
	}
}