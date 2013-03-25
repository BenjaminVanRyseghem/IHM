package support;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import core.COModel;

/**
 * I am a class that store the model of the Available color, aka the right column of the interface.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class AvailableColorsModel extends AbstractTableModel {
	private static final long serialVersionUID = -1457342170991596203L;

	/**
	 * The model associated to the view
	 * 
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	COModel model;
	
	/**
	 * The JScrollPane that contain the column of available colors
	 * 
	 * @uml.property  name="scroller"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JScrollPane scroller;
	
	/**
	 * The basic constructor.
	 * 
	 * @param model the model associated to the view.
	 * @param sc the JScrollPane that will contain the column of colors.
	 */
	public AvailableColorsModel(COModel model, JScrollPane sc){
		this.model = model;
		this.scroller = sc;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex){
		return DraggableDualComponent.class;
	}

	@Override
	public String getColumnName(int col){
		return "Available Colors";
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public int getRowCount() {
		int numRows = this.model.getAvailableColors().size();
		return numRows;
	}

	@Override
	public DraggableDualComponent getValueAt(int row, int column) {
		
		Color color = this.model.getAvailableColors().get(row);
		DraggableDualComponent panel = new DraggableDualComponent(this.model, row, color, scroller);
		panel.update(color);
		return panel;
	}
}