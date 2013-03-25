package support;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

public class COColorChooser extends JPanel {

	private static final long serialVersionUID = 6099857231974977024L;
	
	TableModel model;
	
	public COColorChooser(TableModel model){
		this.model = model;
		this.setUp();
	}

	public void updateModel(TableModel model) {
		this.model  = model;
		this.setUp();
	}
	
	protected void setUp(){		
		
		int rows = model.getRowCount();
		int columns = model.getColumnCount();
		
		int width = 150;
		int height = 60;
		
		Dimension size = new Dimension(width*columns+(4*(columns-1)),height*rows+(4*(rows-1)));
		
		this.setLayout(new GridBagLayout());		
		
		for( int c = 0; c < columns ; c++ ){
			GridBagConstraints constraint = new GridBagConstraints(c,0,1,1,1,1,GridBagConstraints.CENTER, 0, new Insets(1,1,1,1),0,0);
			JLabel label = new JLabel(model.getColumnName(c));
			label.setPreferredSize(new Dimension(width, height));
			this.add(label, constraint);
		}
		
		for( int r = 0 ; r < rows ; r++ ){
			for( int c = 0; c < columns ; c++ ){
				GridBagConstraints constraint = new GridBagConstraints(c,r+1,1,1,1,1,GridBagConstraints.CENTER, 0, new Insets(1,1,1,1),0,0);
				Class<?> klass = model.getColumnClass(c);
				JComponent component = (JComponent) klass.cast(model.getValueAt(r, c));
				component.setPreferredSize(new Dimension(width, height));
				this.add(component, constraint);
			}
		}

		this.setMinimumSize(size);
		this.setMaximumSize(size);
	}
}
