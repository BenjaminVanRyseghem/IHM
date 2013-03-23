package color.generator;
import java.awt.Color;
import java.util.List;

/**
 * I am an abstract class defining the protocol for the colors generator
 * 
 * Here is the formula used to convert a color into gray scale:
 * 		0.3*red + 0.59*green + 0.11*blue
 */

public abstract class COColorsGenerator {	

	public abstract List<Color> generateColors();
	public abstract String label();

	public String toString(){
		return this.label();
	}
	
}
