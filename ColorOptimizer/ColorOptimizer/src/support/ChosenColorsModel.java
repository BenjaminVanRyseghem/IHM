package support;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import core.COModel;

/**
 * I am a class that store the model of the Chosen colors, aka the middle column of the interface.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class ChosenColorsModel extends AbstractTableModel {
	private static final long serialVersionUID = -1457342170991596203L;

	/**
	 * The model associated to the view.
	 * 
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	COModel model;
	
	/**
	 * The elements of the chosen colors column.
	 * 
	 * @uml.property  name="dualComponents"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="support.DualColorComponent"
	 */
	List<DualColorComponent> dualComponents;
	
	/**
	 * The JScrollPane that contain the column of chosen colors.
	 * 
	 * @uml.property  name="scroller"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JScrollPane scroller;
	
	/**
	 * The basic constructor.
	 * Set the model associated to the view, the list of dualComponents <b>
	 * and the JScrollPane that contain the column of chosen colors
	 * 
	 * @param model the model associated to the view.
	 * @param dualComponents the elements of the chosen colors
	 * @param sc the JScrollPane that will contain the column of colors.
	 */
	public ChosenColorsModel(COModel model, List<DualColorComponent> dualComponents, JScrollPane sc){
		this.model = model;
		this.dualComponents = dualComponents;
		this.scroller = sc;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex){
		if (columnIndex == 1) return DualColorComponent.class;
		return JPanel.class;
	}
	
	@Override
	public String getColumnName(int col){
		switch(col){
		case 0:
			return "Original Colors";
		case 1:
			return "Chosen Colors";
		}
		return "";
	}
	
	@Override
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
			DualColorComponent newComponent = new DualColorComponent(this.model, row, color, scroller);
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
