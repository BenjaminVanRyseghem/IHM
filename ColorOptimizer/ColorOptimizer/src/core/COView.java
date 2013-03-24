package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

import support.AvailableColorsModel;
import support.COColorChooser;
import support.ChosenColorsModel;
import support.DualColorComponent;
import updates.COAvailableColorsUpdate;
import updates.COChosenColorUpdate;
import updates.COGeneratorUpdate;
import updates.COUpdate;

import color.generator.*;

public class COView implements Observer{

	COModel model;
	JFrame frame;
	JScrollPane chosenColors;
	JScrollPane availableColors;	
	JComboBox generatorChooser;
	JButton reset;
	JButton export;
	JButton quit;
	
	List<DualColorComponent> dualComponents;
	
	public COView(COModel model){
		model.addObserver(this);
		this.model = model;
		dualComponents = new ArrayList<DualColorComponent>();
		
		this.instantiateWidgets();
	}
	
	protected void instantiateWidgets(){
		this.setUpFrame();
		this.setUpChosenColors();
		this.setUpAvailableColors();
		this.setUpGeneratorChooser();
		this.setUpReset();
		this.setUpExport();
		this.setUpQuit();
	}

	protected void setUpFrame() {
		frame = new JFrame("Color Optimizer");
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		this.frame.setPreferredSize(new Dimension(600,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	protected void setUpQuit() {
		quit = new JButton("Quit");
		quit.addActionListener(new QuitActionListener());
	}

	protected void setUpExport() {
		export = new JButton("Export");
	}
	
	protected void setUpReset() {
		reset = new JButton("Reset");
		reset.addActionListener(new ResetActionListener(this.model));
	}
	
	protected void setUpChosenColors(){
		dualComponents.clear();

		chosenColors = new JScrollPane();
		TableModel tableModel = new ChosenColorsModel(model, dualComponents, chosenColors);
		COColorChooser table = new COColorChooser(tableModel);
		
		chosenColors.setViewportView(table);
		chosenColors.setPreferredSize(new Dimension(350,300));
	}
	
	protected void setUpAvailableColors(){
		availableColors = new JScrollPane();
		TableModel tableModel = new AvailableColorsModel(model, availableColors);
		COColorChooser table = new COColorChooser(tableModel);
		
		availableColors.setViewportView(table);
		availableColors.setPreferredSize(new Dimension(175,300));
	}
	
	protected void setUpGeneratorChooser(){
		
		generatorChooser = new JComboBox(availableGenerators());
		generatorChooser.isVisible();
		generatorChooser.addActionListener(new GeneratorChoseListener(model));
	}
	
	protected COColorsGenerator[] availableGenerators(){
		return this.model.availableGenerators(); 
	}
		
	protected void buildView(){
		Container buttonsBar = new Container();
	
		buttonsBar.setLayout(new BoxLayout(buttonsBar, BoxLayout.LINE_AXIS));
		buttonsBar.add(this.reset);
		buttonsBar.add(Box.createHorizontalGlue());
		buttonsBar.add(this.export);
		buttonsBar.add(this.quit);
		
		Container container = this.frame.getContentPane();
	
		container.add(this.generatorChooser, BorderLayout.NORTH);
		container.add(this.chosenColors, BorderLayout.WEST);
		container.add(this.availableColors, BorderLayout.EAST);
		container.add(buttonsBar, BorderLayout.SOUTH);
	}
	
	public void show(){
		this.buildView();
		this.frame.setVisible(true);
		this.frame.pack();
	}
	
	@Override
	public void update(Observable arg0, Object update) {
		((COUpdate)update).updateOn(this);
	}
	
	public void update(COGeneratorUpdate update){
	}
	
	public void update(COAvailableColorsUpdate update){
		this.frame.remove(availableColors);
		this.setUpAvailableColors();
		this.frame.getContentPane().add(this.availableColors, BorderLayout.EAST);
		this.frame.validate();
	}
	
	public void update(COChosenColorUpdate update){
		int index = update.getIndex();
		DualColorComponent component = dualComponents.get(index);
		component.update(update.getColor());
	}
	
	private class QuitActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}		
	}
	
	private class ResetActionListener implements ActionListener{
		
		COModel model;
		public ResetActionListener(COModel m){
			model = m;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.model.resetChosenColors();
		}		
	}
	
	private class GeneratorChoseListener implements ActionListener{
		
		COModel model;
		
		public GeneratorChoseListener(COModel model){
			this.model = model;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
	        COColorsGenerator generator = (COColorsGenerator)cb.getSelectedItem();
	        model.setColorsGenerator(generator);
		}		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.red);
		colors.add(Color.blue);
		colors.add(Color.green);
		colors.add(Color.yellow);
		colors.add(Color.pink);
		colors.add(Color.cyan);
		COModel model = new COModel(new COStandardColorsGenerator(), colors);
		COView view = new COView(model);
		view.show();
	}
	
}
