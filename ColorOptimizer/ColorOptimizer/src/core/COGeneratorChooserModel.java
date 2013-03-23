package core;

import javax.swing.DefaultComboBoxModel;

import color.generator.COColorGenerator;

public class COGeneratorChooserModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = -7463084035586035288L;

	public COGeneratorChooserModel(COColorGenerator[] items){
		super(items);
	}
	
	public Object getElementAt(int index){
		Object object = super.getElementAt(index);
		return ((COColorGenerator) object).label();
	}
		
}
