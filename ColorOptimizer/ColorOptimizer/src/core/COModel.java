package core;
import java.awt.Color;
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
import updates.COGeneratorUpdate;

public class COModel extends Observable {

	List<Color> originalColors;
	List<Color> availableColors;
	Color[] chosenColors;
	
	COUndoRedo undoRedo;
	COColorsGenerator colorsGenerator;
	
	final Color Default_Color = new Color(230,230,230);
		
	
	/***********************************
	 * 
	 * PROTOCOL: constructors
	 * 
	 **********************************/
	
	
	public COModel(COColorsGenerator generator, List<Color> colors){
		super();
		this.setColorsGenerator(generator);
		this.setOriginalColors(colors);
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
	
	
	protected COColorsGenerator[] availableGenerators(){
		COColorsGenerator[] generators = new COColorsGenerator[4];
		generators[0] = new COStandardColorsGenerator();
		generators[1] = new COBlueGenerator();
		generators[2] = new CORedGenerator();
		generators[3] = new COGreenGenerator();
		
		return generators;
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
	
	
	
	
	
	
	/***********************************
	 * 
	 * PROTOCOL: accessing
	 * 
	 **********************************/
	
	
	
	
	public List<Color> getOriginalColors() {
		return originalColors;
	}

	public void setOriginalColors(List<Color> originalColors) {
		this.originalColors = originalColors;

		int size = originalColors.size();
		Color[] colors = new Color[size];
		
		for(int i = 0 ; i < size ; i++){
			colors[i] = Default_Color;
		}
		this.setChosenColors(colors);
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
}
