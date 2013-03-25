package core;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import color.generator.COBlueGenerator;
import color.generator.COColorsGenerator;
import color.generator.COGreenGenerator;
import color.generator.CORedGenerator;
import color.generator.COStandardColorsGenerator;
import updates.COAvailableColorsUpdate;
import updates.COChosenColorUpdate;
import updates.COClipBoardUpdate;
import updates.COGeneratorUpdate;


/**
 * The model that compute colors and generator.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class COModel extends Observable {

	/**
	 * The list of original grey scale colors (left column)
	 * 
	 * @uml.property  name="originalColors"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.awt.Color"
	 */
	List<Color> originalColors;
	
	/**
	 * The list of available colors
	 * 
	 * @uml.property  name="availableColors"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.awt.Color"
	 */
	List<Color> availableColors;
	
	/**
	 * An array of the chosen colors
	 * 
	 * @uml.property  name="chosenColors"
	 */
	Color[] chosenColors;
	
	/**
	 * The color in the clipboard
	 * 
	 * @uml.property  name="colorClipBoard"
	 */
	Color colorClipBoard;
	
	/**
	 * The color generator
	 * @uml.property  name="colorsGenerator"
	 * @uml.associationEnd  
	 */
	COColorsGenerator colorsGenerator;
	
	/**
	 * The default color
	 * @uml.property  name="default_Color"
	 */
	final Color Default_Color = new Color(230,230,230);
		
	
	/***********************************
	 * 
	 * PROTOCOL: constructors
	 * 
	 **********************************/
	/**
	 * The basic constructor
	 * 
	 * set the generator and compute numberOfColors colors
	 * 
	 * @param generator the generator to be set
	 * @param numberOfColors the number of colors that will be computed
	 */
	public COModel(COColorsGenerator generator, int numberOfColors) {
		super();
		
		this.setColorsGenerator(generator);
		this.setOriginalColors(ComputeInitialColors(numberOfColors));
		
		colorClipBoard = Default_Color;
	}
	
	
	/***********************************
	 * 
	 * PROTOCOL: computing
	 * 
	 **********************************/
	
	/**
	 * Compute the initial colors.
	 * 
	 * @param numberOfColors the number of colors that will be computed
	 * 
	 * @return a list of colors
	 */
	private List<Color> ComputeInitialColors(int numberOfColors) {
		
		List<Color> colors = new ArrayList<Color>();
		int delta = 255 / (numberOfColors+1);
		int i = 0;
		int gray = 0;
		
		while (i < numberOfColors){
			colors.add(new Color(gray, gray, gray));
			gray += delta;
			i++;
		}
		
		return colors;
	}
	
	
	/**
	 * Compute the available generator.
	 * 
	 * @return an array containing the available color generator
	 */
	protected COColorsGenerator[] availableGenerators(){
		COColorsGenerator[] generators = new COColorsGenerator[4];
		generators[0] = new COStandardColorsGenerator();
		generators[1] = new COBlueGenerator();
		generators[2] = new CORedGenerator();
		generators[3] = new COGreenGenerator();
		
		return generators;
	}
	
	/**
	 * Compute chosen colors.
	 * 
	 * @return an Array of colors with are the closest color of the grey scale (left column)
	 */
	protected Color[] computeChosenColors(){
		int size = originalColors.size();
		Color[] result = new Color[size];
		int index = 0;
		
		for (Color color : originalColors){
			result[index] = this.closestColorFrom(color);
			index++;
		}
		
		return result;
	}
	
	/**
	 * Get the closest color of the gray scale
	 * 
	 * @param gray the gray scale
	 * @return the closest color of the gray scale
	 */
	protected Color closestColorFrom(Color gray){
		Color result = availableColors.get(0);
		double delta = this.distanceBetween(gray, result);
		
		for(Color current : availableColors){
			double newDelta = this.distanceBetween(gray, current);
			if( newDelta < delta ){
				delta = newDelta;
				result = current;
			}
		}
		
		return result;
	}
	
	/**
	 * Get the distance between the grays scale and the color
	 * 
	 * @param gray the gray scale
	 * @param color the color
	 * 
	 * @return the distance between them
	 */
	protected double distanceBetween(Color gray, Color color){
		int gr = gray.getRed();
		
		int r = color.getRed(); 
		int g = color.getGreen();
		int b = color.getBlue();
		
		return Math.abs(gr - ((r*0.3)+(g*0.59)+(b*0.11)));
	}
	
	/***********************************
	 * 
	 * PROTOCOL: updating
	 * 
	 **********************************/
	
	/**
	 * Notify the observer of this model when it is changed
	 */
	public void notifyObservers(Object arg){
		this.setChanged();
		super.notifyObservers(arg);
	}
	
	/**
	 * Reset the chosen colors with the Default_color
	 */
	protected void resetChosenColors(){
		int size = chosenColors.length;
		for (int i = 0; i < size; i++) {
			if(!chosenColors[i].equals(Default_Color))
				this.setChosenColor(i, Default_Color);
		}
	}
	
	
	/**
	 * Set a color at the index of the list chosenColors
	 * 
	 * @param index the index of the changed color.
	 * @param color the color that will replace the ancient value.
	 */
	public void setChosenColor(int index, Color color){
		chosenColors[index] = color;
		
		this.notifyObservers(new COChosenColorUpdate(index, color));
	}
	
	/**
	 * Recompute the chosen colors (called when clicking on compute)
	 */
	public void recomputeChosenColors() {
		Color[] colors = this.computeChosenColors();
		for (int i = 0; i < colors.length; i++) {
			Color color = colors[i];
			this.setChosenColor(i, color);
		}
	}	
	
	
	/***********************************
	 * 
	 * PROTOCOL: accessing
	 * 
	 **********************************/
	
	/**
	 * Copy the color in the clip board and notify COClipBoardUpdate
	 * 
	 * @param color the color copied
	 */
	public void copy(Color color){
		colorClipBoard = color;
		this.notifyObservers(new COClipBoardUpdate());
	}
	
	/**
	 * Return the color stored in the clip board
	 * 
	 * @return
	 */
	public Color paste(){
		return colorClipBoard;
	}
	
	/**
	 * Get the original colors
	 * 
	 * @return a list of the original colors
	 */
	public List<Color> getOriginalColors() {
		return originalColors;
	}

	/**
	 * Set the original colors with originalColors and recompute the chosen colors
	 * 
	 * @param originalColors the colors that will replace the elements of the list originalColors
	 */
	public void setOriginalColors(List<Color> originalColors) {
		this.originalColors = originalColors;

		this.setChosenColors(this.computeChosenColors());
	}

	/**
	 * Get the chosen colors 
	 * 
	 * @return the array of chosen colors
	 * 
	 * @uml.property  name="chosenColors"
	 */
	public Color[] getChosenColors() {
		return chosenColors;
	}


	/**
	 * Set the chosen colors
	 * 
	 * @param chosenColors the new array to be set
	 * 
	 * @uml.property  name="chosenColors"
	 */
	public void setChosenColors(Color[] chosenColors) {
		this.chosenColors = chosenColors;
	}

	/**
	 * Get the list of available colors
	 * 
	 * @return the list of available colors
	 */
	public List<Color> getAvailableColors() {
		return availableColors;
	}

	/**
	 * Set the available colors and notify COAvailableColorsUpdate
	 * 
	 * @param availableColors the list to be set
	 */
	public void setAvailableColors(List<Color> availableColors) {
		this.availableColors = availableColors;
		this.notifyObservers(new COAvailableColorsUpdate());
	}

	/**
	 * Get the color generator
	 * 
	 * @return the color generator
	 * 
	 * @uml.property  name="colorsGenerator"
	 */
	public COColorsGenerator getColorsGenerator() {
		return colorsGenerator;
	}

	/**
	 * Set the color generator
	 * 
	 * @param colorsGenerator the COColorsGenerator to be set
	 * @uml.property  name="colorsGenerator"
	 */
	public void setColorsGenerator(COColorsGenerator colorsGenerator) {
		this.colorsGenerator = colorsGenerator;
		this.notifyObservers(new COGeneratorUpdate());
		
		this.setAvailableColors(colorsGenerator.generateColors());
	}

	/**
	 * Copy to the clip board the chosen colors
	 */
	public void exportChosenColors() {
		Color[] colors = this.chosenColors;
		String export = "";
		for (int i = 0; i < colors.length; i++) {
			Color color = colors[i];
			export += "{" + color.getRed()+"; "+ color.getGreen() + "; "+ color.getBlue()+ "}\n";
		}
		StringSelection stringSelection = new StringSelection (export);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}
	
	/**
	 * The entry point of the software
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		COModel model = new COModel(new COStandardColorsGenerator(),4);
		COView view = new COView(model);
		view.show();
	}
}
