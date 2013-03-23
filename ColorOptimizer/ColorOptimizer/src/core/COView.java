package core;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;

import color.generator.*;

public class COView implements Observer{

	COModel model;
	JFrame frame;
	JList originalColors;
	JList chosenColors;
	JList availableColors;	
	JComboBox generatorChooser;
	JButton export;
	JButton quit;
	
	public COView(COModel model){
		model.addObserver(this);
		this.model = model;
		this.instantiateWidgets();
		this.buildView();	
	}
	
	protected void instantiateWidgets(){
		setUpFrame();
		this.setUpOriginalColors();
		this.setUpChosenColors();
		this.setUpAvailableColors();
		this.setUpGeneratorChooser();
		this.setUpExport();
		this.setUpQuit();
	}

	protected void setUpFrame() {
		frame = new JFrame("Color Optimizer");
	}

	protected JButton setUpQuit() {
		return quit = new JButton("Quit");
	}

	protected void setUpExport() {
		export = new JButton("Export");
	}
	
	protected void setUpOriginalColors(){
		originalColors = new JList();
	}
	
	protected void setUpChosenColors(){
		chosenColors = new JList();
	}
	
	protected void setUpAvailableColors(){
		availableColors = new JList();
	}
	
	protected void setUpGeneratorChooser(){
		generatorChooser = new JComboBox();
	}
	
	protected static COColorGenerator[] availableGenerators(){
		COColorGenerator[] generators = new COColorGenerator[1];
		
		generators[0] = new COStandardColorGenerator();
		
		return generators;
	}
		
	protected void buildView(){
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
