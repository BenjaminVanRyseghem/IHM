package updates;

import java.awt.Color;

import core.COView;

public class COChosenColorUpdate extends COUpdate {
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	int index;
	Color color;
	
	public COChosenColorUpdate(int index2, Color color2) {
		index = index2;
		color = color2;
	}

	@Override
	public void updateOn(COView view) {
		view.update(this);
	}
}
