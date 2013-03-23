package support;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import core.COModel;


public class AvailableColorsModel extends AbstractTableModel {
	private static final long serialVersionUID = -1457342170991596203L;

	COModel model;
	
	public AvailableColorsModel(COModel model){
		this.model = model;
	}
	
	public int getColumnCount() {
		return 2;
	}

	public Class<?> getColumnClass(int columnIndex){
		if(columnIndex == 0) return DraggablePanel.class;
		return JPanel.class;
	}
	
	public String getColumnName(int col){
		if (col == 1) return "Grayscale"; 
		return "Available Colors";
	}
	public int getRowCount() {
		int numRows = this.model.getAvailableColors().size();
		return numRows;
	}

	@Override
	public JPanel getValueAt(int row, int column) {
		JPanel panel = new JPanel();
		Color color = this.model.getAvailableColors().get(row);
		if (column == 1){
			float gray = ((float)(color.getRed() * 0.30 / 255 + color.getGreen() * 0.59 / 255 + color.getBlue() * 0.11 / 255));
			color = new Color(gray, gray, gray);
		}
		else{
			panel = new DraggablePanel();
		}
		panel.setBackground(color);
		return panel;
	}
}