package support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class COCopyMenuActionListener implements ActionListener {
	
	DualColorComponent component;
	
	public COCopyMenuActionListener(DualColorComponent component){
		this.component = component;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.component.copy();
	}
}
