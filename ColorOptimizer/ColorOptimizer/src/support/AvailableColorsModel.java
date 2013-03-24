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
		return 1;
	}

	public Class<?> getColumnClass(int columnIndex){
		return DraggableDualComponent.class;
	}
	
	public String getColumnName(int col){
		return "Available Colors";
	}
	public int getRowCount() {
		int numRows = this.model.getAvailableColors().size();
		return numRows;
	}

	@Override
	public DraggableDualComponent getValueAt(int row, int column) {
		
		Color color = this.model.getAvailableColors().get(row);
		DraggableDualComponent panel = new DraggableDualComponent(this.model, row, color);
		panel.update(color);
		return panel;
	}
}