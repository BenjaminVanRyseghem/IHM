package player;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ListListenner implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		String name = (String)e.getSource();
		
		System.out.println(name);
	}

}
