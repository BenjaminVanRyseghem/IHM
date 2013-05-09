package player;

import java.util.List;

import javax.swing.DefaultListModel;

public class MusicListModel extends DefaultListModel {

	private static final long serialVersionUID = 5945587908050556127L;

	public void addElements(List<String[]> elements) {
		for (String[] information : elements) {
			this.addElement(information);
		}
	}
	
	public Object getElementAt(int index) {
		return this.get(index);
	}
	
	public Object elementAt(int index) {
		return this.get(index);
	}
	
	public Object get(int index) {
		
		String musicInfo = "";
		
		String[] informations = (String[])super.get(index);
		
		for (int i = 0; i<informations.length; i++) {
			//System.out.println(informations[i]);
			musicInfo += informations[i] + " _ ";
		}
		//System.out.println(musicInfo);
		return musicInfo;
	}
}
