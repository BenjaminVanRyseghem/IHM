package core;
import java.awt.Color;
import java.util.List;
import java.util.Observable;

import color.generator.COColorGenerator;
import undo.redo.COUndoRedo;

public class COModel extends Observable {

	List<Color> originalColors;
	List<Color> availableColors;
	COUndoRedo undoRedo;
	COColorGenerator colorsGenerator;
	
	public COModel(COColorGenerator generator, COUndoRedo ur){
		colorsGenerator = generator;
		undoRedo = ur;
	}
	
	public COModel(COColorGenerator generator, COUndoRedo ur, List<Color> colors){
		this(generator, ur);
		originalColors = colors;
	}
	
}
