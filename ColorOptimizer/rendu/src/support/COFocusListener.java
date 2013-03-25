package support;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.border.LineBorder;

public class COFocusListener implements FocusListener {

	DualColorComponent model;

	public COFocusListener(DualColorComponent model){
		this.model = model;
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		this.model.setBorder(new LineBorder(Color.blue, 2) );
		this.model.scrollRectToVisible(this.model.getBounds());
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		this.model.setBorder(new LineBorder(Color.blue, 0) );
	}
	
}