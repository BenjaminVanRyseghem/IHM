package support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class COPasteMenuActionListener implements ActionListener {
	
	DualColorComponent component;
	
	public COPasteMenuActionListener(DualColorComponent component2){
		this.component = component2;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.component.paste();
	}
}
