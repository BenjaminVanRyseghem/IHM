package support;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class MPMusicListModel implements ListModel {
	List<String[]> elements;
	List<ListDataListener> listeners = new ArrayList<ListDataListener>();;
	
	public MPMusicListModel(){
		elements = new ArrayList<String[]>();
	}
	
	public MPMusicListModel(List<String[]> list){
		elements = list;
		this.fireAllChanged();
	}
	
	public void fireAllChanged(){
		this.fireIntervalChanged(0, elements.size()-1);
	}
	
	public void fireIntervalChanged(int start, int end){
		for(ListDataListener l : listeners){
			l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, start, end));
		}
	}
	
	public void setElements(List<String[]> elements) {
		this.elements = elements;
		this.fireAllChanged();
	}
	
	@Override
	public Object getElementAt(int index) {
		return this.get(index);
	}
	
	public Object elementAt(int index) {
		return this.get(index);
	}
	
	public Object get(int index) {
		
		String musicInfo = "";
		
		String[] informations = elements.get(index);
		
		for (int i = 0; i<informations.length; i++) {
			musicInfo += informations[i] + " _ ";
		}
		return musicInfo;
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
		
	}

	public void addElement(String[] element) {
		int size = this.getSize();
		elements.add(element);
		this.fireIntervalChanged(size-1, size);
	}
}
