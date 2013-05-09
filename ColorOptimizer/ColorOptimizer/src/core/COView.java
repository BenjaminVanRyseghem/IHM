package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

import support.AvailableColorsModel;
import support.COColorChooser;
import support.ChosenColorsModel;
import support.ClipBoardComponent;
import support.DualColorComponent;
import updates.COAvailableColorsUpdate;
import updates.COChosenColorUpdate;
import updates.COClipBoardUpdate;
import updates.COGeneratorUpdate;
import updates.COUpdate;

import color.generator.*;

public class COView implements Observer{

	/**
	 * The model associated to this view
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	COModel model;
	
	/**
	 * The main window
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
	JFrame frame;
	
	/**
	 * The two column on the left
	 * @uml.property  name="chosenColors"
	 * @uml.associationEnd  
	 */
	JScrollPane chosenColors;
	
	/**
	 * The column on the right
	 * @uml.property  name="availableColors"
	 * @uml.associationEnd  
	 */
	JScrollPane availableColors;	
	
	/**
	 * The combo box that change the available colors
	 * @uml.property  name="generatorChooser"
	 * @uml.associationEnd  
	 */
	JComboBox generatorChooser;
	
	/**
	 * The reset button
	 * @uml.property  name="reset"
	 * @uml.associationEnd  
	 */
	JButton reset;
	
	/**
	 * The compute button
	 * @uml.property  name="compute"
	 * @uml.associationEnd  
	 */
	JButton compute;
	
	/**
	 * The export button
	 * @uml.property  name="export"
	 * @uml.associationEnd  
	 */
	JButton export;
	
	/**
	 * The quit button
	 * @uml.property  name="quit"
	 * @uml.associationEnd  
	 */
	JButton quit;
	
	/**
	 * The clipboard item
	 * 
	 * @uml.property  name="clipboard"
	 * @uml.associationEnd  
	 */
	ClipBoardComponent clipboard;
	
	
	/**
	 * The label "Number of wanted colors"
	 * @uml.property  name="label"
	 * @uml.associationEnd  
	 */
	JLabel label;
	
	/**
	 * The slider that change the number of chosen colors
	 * @uml.property  name="slider"
	 * @uml.associationEnd  
	 */
	JSlider slider;
	
	/**
	 * The label that display the number of chosen colors
	 * @uml.property  name="numbers"
	 * @uml.associationEnd  
	 */
	JLabel numbers;
	
	/**
	 * The list of elements in the chosen colors
	 * @uml.property  name="dualComponents"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="support.DualColorComponent"
	 */
	List<DualColorComponent> dualComponents;
	
	/** 
	 * The basic constructor
	 * 
	 * @param model the model associated to this view
	 */
	public COView(COModel model){
		model.addObserver(this);
		this.model = model;
		dualComponents = new ArrayList<DualColorComponent>();
		
		this.instantiateWidgets();
	}
	
	/**
	 * instantiate the elements of the view
	 */
	protected void instantiateWidgets(){
		this.setUpClipBoard();
		this.setUpFrame();
		this.setUpChosenColors();
		this.setUpAvailableColors();
		this.setUpGeneratorChooser();
		this.setUpReset();
		this.setUpCompute();
		this.setUpExport();
		this.setUpQuit();
		this.setUpNumberColorChooser();
	}
	
	/**
	 * set up the slider to choose the number of wanted colors
	 */
	protected void setUpNumberColorChooser() {
		this.slider = new JSlider(1, 10);
		this.slider.setPreferredSize(new Dimension(176,29));
		this.slider.setValue(this.model.getChosenColors().length);
		
		NumberOfColorChooserListener listener = new NumberOfColorChooserListener(this);
		this.slider.addChangeListener(listener);
		
		this.label = new JLabel("Number of wanted colors");
		this.numbers = new JLabel(String.valueOf(this.slider.getValue()));
	}
	
	/**
	 * set up the clip board component (top right on the interface)
	 */
	protected void setUpClipBoard(){
		clipboard = new ClipBoardComponent(this.model);
	}

	/**
	 * set up the main frame that will contain all of the widgets
	 */
	protected void setUpFrame() {
		frame = new JFrame("Color Optimizer");
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 *  set up the button quit
	 */
	protected void setUpQuit() {
		quit = new JButton("Quit");
		quit.addActionListener(new QuitActionListener());
	}

	/**
	 * set up the button export
	 */
	protected void setUpExport() {
		export = new JButton("Export");
		export.addActionListener(new ExportActionListener(this.model));
	}
	
	/**
	 * set up the button reset
	 */
	protected void setUpReset() {
		reset = new JButton("Reset");
		reset.addActionListener(new ResetActionListener(this.model));
	}
	
	/**
	 * set up the button compute
	 */
	protected void setUpCompute() {
		compute = new JButton("Compute");
		compute.addActionListener(new ComputeActionListener(this.model));
	}
	
	/** 
	 * set up the two color columns on the left 
	 */
	protected void setUpChosenColors(){
		dualComponents.clear();

		chosenColors = new JScrollPane();
		TableModel tableModel = new ChosenColorsModel(model, dualComponents, chosenColors);
		COColorChooser table = new COColorChooser(tableModel);
		
		chosenColors.setViewportView(table);
		chosenColors.setPreferredSize(new Dimension(350,300));
	}
	
	/**
	 * set up the column on the right 
	 */
	protected void setUpAvailableColors(){
		availableColors = new JScrollPane();
		TableModel tableModel = new AvailableColorsModel(model, availableColors);
		COColorChooser table = new COColorChooser(tableModel);
		
		availableColors.setViewportView(table);
		availableColors.setPreferredSize(new Dimension(175,300));
	}
	
	/**
	 * set up the combo box on the top of the interface
	 */
	protected void setUpGeneratorChooser(){
		
		generatorChooser = new JComboBox(availableGenerators());
		generatorChooser.isVisible();
		generatorChooser.addActionListener(new GeneratorChoseListener(model));
	}
	
	
	/**
	 * get the available colors generated
	 * 
	 * @return an array of COColorsGenerator
	 */
	protected COColorsGenerator[] availableGenerators(){
		return this.model.availableGenerators(); 
	}
		
	/**
	 * build the container of the top elements
	 * 
	 * @return a Container that contain the elements of the top bar
	 */
	private Container buildTopBar() {
		Container topBar = new Container();
		topBar.setLayout(new GridBagLayout());
		
		topBar.setLayout(new BoxLayout(topBar, BoxLayout.LINE_AXIS));
		topBar.add(this.label);
		topBar.add(this.slider);
		topBar.add(this.numbers);
		topBar.add(Box.createHorizontalGlue());
		topBar.add(this.generatorChooser);
		topBar.add(this.clipboard);
		
		
		return topBar;
	}
	
	/**
	 * build the entire view (top bar, columns and buttons)
	 */
	protected void buildView(){
		
		Container topBar = this.buildTopBar();
		
		Container buttonsBar = new Container();
	
		buttonsBar.setLayout(new BoxLayout(buttonsBar, BoxLayout.LINE_AXIS));
		buttonsBar.add(this.compute);
		buttonsBar.add(this.reset);
		buttonsBar.add(Box.createHorizontalGlue());
		buttonsBar.add(this.export);
		buttonsBar.add(this.quit);
		
		Container container = this.frame.getContentPane();

		container.add(topBar, BorderLayout.NORTH);
		container.add(this.chosenColors, BorderLayout.WEST);
		container.add(this.availableColors, BorderLayout.EAST);
		container.add(buttonsBar, BorderLayout.SOUTH);
	}
	
	/**
	 * display the interface by building its elements
	 */
	public void show(){
		this.buildView();
		
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setMinimumSize(new Dimension(530,400));
		this.frame.setMaximumSize(new Dimension(530,1000));
		this.frame.setVisible(true);
		
	}
	
	@Override
	public void update(Observable arg0, Object update) {
		((COUpdate)update).updateOn(this);
	}
	
	/**
	 * do nothing just here for the design of the updates
	 * 
	 * @param update
	 */
	public void update(COGeneratorUpdate update){
	}
	
	/**
	 * update the available color with the selected item of the combo box
	 * 
	 * @param update the parameter used to know which update has to be used 
	 */
	public void update(COAvailableColorsUpdate update){
		this.frame.remove(availableColors);
		this.setUpAvailableColors();
		this.frame.getContentPane().add(this.availableColors, BorderLayout.EAST);
		this.frame.validate();
	}
	
	/**
	 * update the chosen color at the index stored in the COChosenColorUpdate class.
	 * 
	 * @param update the parameter used to know which update has to be use.
	 */
	public void update(COChosenColorUpdate update){
		int index = update.getIndex();
		DualColorComponent component = dualComponents.get(index);
		component.update(update.getColor());
	}
	

	/**
	 * update the clip board item and the content of the clip board.
	 * 
	 * @param update the parameter used to know which update has to be use.
	 */
	public void update(COClipBoardUpdate update) {
		clipboard.update();
	}
	
	
	
	
	
	
	
	
	
	/**
	 * I am a listener of the quit button.
	 * I quit the application on click.
	 * 
	 * @author Benjamin Van Ryseghem, Francois Lepan
	 */
	private class QuitActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}		
	}
	
	/**
	 * I am a listener of the reset button.
	 * I empty the chosen colors column.
	 * 
	 * @author Benjamin Van Ryseghem, Francois Lepan
	 */
	private class ResetActionListener implements ActionListener{
		
		/**
		 * @uml.property  name="model"
		 * @uml.associationEnd  
		 */
		COModel model;
		public ResetActionListener(COModel m){
			model = m;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.model.resetChosenColors();
		}		
	}

	/**
	 * I am a listener of the compute button.
	 * I compute colors for the chosen colors column depending on the selected item of the combo box.
	 * 
	 * @author Benjamin Van Ryseghem, Francois Lepan
	 */
	private class ComputeActionListener implements ActionListener{
	
		/**
		 * @uml.property  name="model"
		 * @uml.associationEnd  
		 */
		COModel model;
		public ComputeActionListener(COModel m){
			model = m;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.model.recomputeChosenColors();
		}		
	}
	
	/**
	 * I am a listener of the export button.
	 * I save in the clip board a list of the chosen colors.
	 * 
	 * @author Benjamin Van Ryseghem, Francois Lepan
	 */
	private class ExportActionListener implements ActionListener{
		
		/**
		 * @uml.property  name="model"
		 * @uml.associationEnd  
		 */
		COModel model;
		public ExportActionListener(COModel m){
			model = m;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.model.exportChosenColors();
			JOptionPane.showMessageDialog(COView.this.frame, "Colors copied in the clipboard", "Export done", JOptionPane.OK_OPTION);
		}		
	}
	
	/**
	 * I am a listener of the combo box.
	 * I change the available colors depending on the choice of the user.
	 * 
	 * @author Benjamin Van Ryseghem, Francois Lepan
	 */
	private class GeneratorChoseListener implements ActionListener {
		
		/**
		 * @uml.property  name="model"
		 * @uml.associationEnd  
		 */
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
	
	
	
	/**
	 * I am a listener of the slider.
	 * I display X colors depending on the value of the slider.
	 * 
	 * @author Benjamin Van Ryseghem, Francois Lepan
	 */
	private class NumberOfColorChooserListener implements ChangeListener {

		/**
		 * @uml.property  name="view"
		 * @uml.associationEnd  
		 */
		COView view;
		
		public NumberOfColorChooserListener(COView view) {
			this.view = view;
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			this.view.numbers.setText(slider.getValue() + "");
			int number = slider.getValue();
			List<Color> colors = new ArrayList<Color>();
			int delta = 255 / (number+1);
			int i = 0;
			int gray = 0;
			
			while (i < number){
				colors.add(new Color(gray, gray, gray));
				gray += delta;
				i++;
			}

			this.view.model.setOriginalColors(colors);
			
			TableModel tableModel = new ChosenColorsModel(this.view.model, dualComponents, chosenColors);
			COColorChooser table = new COColorChooser(tableModel);
			
			chosenColors.setViewportView(table);
		}
	}
}
