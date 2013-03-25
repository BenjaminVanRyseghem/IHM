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
import undo.redo.COUndoRedo;
import updates.COAvailableColorsUpdate;
import updates.COChosenColorUpdate;
import updates.COClipBoardUpdate;
import updates.COGeneratorUpdate;

public class COModel extends Observable {

	List<Color> originalColors;
	List<Color> availableColors;
	Color[] chosenColors;
	Color colorClipBoard;
	
	COUndoRedo undoRedo;
	COColorsGenerator colorsGenerator;
	
	final Color Default_Color = new Color(230,230,230);
		
	
	/***********************************
	 * 
	 * PROTOCOL: constructors
	 * 
	 **********************************/
	
	public COModel(COColorsGenerator generator) {
		super();
		
		this.setColorsGenerator(generator);
		this.setOriginalColors(ComputeInitialColors());
		
		colorClipBoard = Default_Color;
	}
	
	
	public COModel(COColorsGenerator generator, List<Color> colors){
		super();
		this.setColorsGenerator(generator);
		this.setOriginalColors(colors);
		
		colorClipBoard = Default_Color;
	}
	
	
	public COModel(COColorsGenerator generator, COUndoRedo ur, List<Color> colors){
		this(generator, colors);
		undoRedo = ur;
	}
	
	/***********************************
	 * 
	 * PROTOCOL: computing
	 * 
	 **********************************/
	
	private List<Color> ComputeInitialColors() {
		int number = 4;
		
		List<Color> colors = new ArrayList<Color>();
		int delta = 255 / (number+1);
		int i = 0;
		int gray = 0;
		
		while (i < number){
			colors.add(new Color(gray, gray, gray));
			gray += delta;
			i++;
		}
		return colors;
	}
	
	
	protected COColorsGenerator[] availableGenerators(){
		COColorsGenerator[] generators = new COColorsGenerator[4];
		generators[0] = new COStandardColorsGenerator();
		generators[1] = new COBlueGenerator();
		generators[2] = new CORedGenerator();
		generators[3] = new COGreenGenerator();
		
		return generators;
	}
	
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
	
	
	public void notifyObservers(Object arg){
		this.setChanged();
		super.notifyObservers(arg);
	}
	
	
	protected void resetChosenColors(){
		int size = chosenColors.length;
		for (int i = 0; i < size; i++) {
			if(!chosenColors[i].equals(Default_Color))
				this.setChosenColor(i, Default_Color);
		}
	}
	
	public void setChosenColor(int index, Color color){
		chosenColors[index] = color;
		
		this.notifyObservers(new COChosenColorUpdate(index, color));
	}
	
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
	
	
	public void copy(Color color){
		colorClipBoard = color;
		this.notifyObservers(new COClipBoardUpdate());
	}
	
	public Color paste(){
		return colorClipBoard;
	}
	
	public List<Color> getOriginalColors() {
		return originalColors;
	}

	public void setOriginalColors(List<Color> originalColors) {
		this.originalColors = originalColors;

		this.setChosenColors(this.computeChosenColors());
	}

	public Color[] getChosenColors() {
		return chosenColors;
	}


	public void setChosenColors(Color[] chosenColors) {
		this.chosenColors = chosenColors;
	}


	public COUndoRedo getUndoRedo() {
		return undoRedo;
	}


	public void setUndoRedo(COUndoRedo undoRedo) {
		this.undoRedo = undoRedo;
	}


	public List<Color> getAvailableColors() {
		return availableColors;
	}

	public void setAvailableColors(List<Color> availableColors) {
		this.availableColors = availableColors;
		this.notifyObservers(new COAvailableColorsUpdate());
	}

	public COColorsGenerator getColorsGenerator() {
		return colorsGenerator;
	}

	public void setColorsGenerator(COColorsGenerator colorsGenerator) {
		this.colorsGenerator = colorsGenerator;
		this.notifyObservers(new COGeneratorUpdate());
		
		this.setAvailableColors(colorsGenerator.generateColors());
	}


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
	
	public static void main(String[] args) {
		
		COModel model = new COModel(new COStandardColorsGenerator());
		COView view = new COView(model);
		view.show();
	}
}
