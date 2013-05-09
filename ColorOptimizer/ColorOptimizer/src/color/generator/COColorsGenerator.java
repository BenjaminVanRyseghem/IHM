package color.generator;
import java.awt.Color;
import java.util.List;

/**
 * I am an abstract class defining the protocol for the colors generator
 * 
 * Here is the formula used to convert a color into gray scale:
 * 		0.3*red + 0.59*green + 0.11*blue
 * 
 * @author Benjamin Van Ryseghem, Lepan Francois
 */
public abstract class COColorsGenerator {	

	/**
	 * Generate the colors.
	 * 
	 * @return a list of colors.
	 */
	public abstract List<Color> generateColors();
	
	/**
	 * The label of the generator.
	 * 
	 * @return the name associated to the generator.
	 */
	public abstract String label();

	public String toString(){
		return this.label();
	}
	
}
