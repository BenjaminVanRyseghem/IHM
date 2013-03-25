package support;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class COCopyPopupMenuListener implements MouseListener {
	
	DualColorComponent component;
	
	public COCopyPopupMenuListener(DualColorComponent component){
		this.component = component;
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent event) {
		if(SwingUtilities.isRightMouseButton(event)){
			JPopupMenu menu = new JPopupMenu("Menu");
			JMenuItem item = new JMenuItem("Copy color", KeyEvent.VK_C);
			item.addActionListener(new COCopyMenuActionListener(this.component));
			menu.add(item);
			menu.show(event.getComponent(), event.getX(), event.getY());
		}
	}
}