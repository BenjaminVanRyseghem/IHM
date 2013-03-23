package support;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import core.COModel;


public class ChosenColorsModel extends AbstractTableModel {
	private static final long serialVersionUID = -1457342170991596203L;

	COModel model;
	List<DualColorComponent> dualComponents;
	
	public ChosenColorsModel(COModel model, List<DualColorComponent> dualComponents){
		this.model = model;
		this.dualComponents = dualComponents;
	}
	
	public int getColumnCount() {
		return 2;
	}

	public Class<?> getColumnClass(int columnIndex){
		if (columnIndex == 1) return DualColorComponent.class;
		return JPanel.class;
	}
	
	public String getColumnName(int col){
		switch(col){
		case 0:
			return "Original Colors";
		case 1:
			return "Chosen Colors";
		}
		return "";
	}
	public int getRowCount() {
		
		int numRows = this.model.getOriginalColors().size();
		return numRows;
	}

	@Override
	public JComponent getValueAt(int row, int column) {
		Color color;
		JComponent panel = new JPanel();
		
		if (column == 1){
			color = this.model.getChosenColors()[row];
			DualColorComponent newComponent = new DualColorComponent(this.model, row, color);
			dualComponents.add(row, newComponent);
			panel = newComponent;
			new DropTargetListener(panel);
		}
		else {
			color = this.model.getOriginalColors().get(row);
			panel.setBackground(color);
		}
		
		return panel;
	}
}
