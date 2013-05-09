package color.generator;

import java.awt.Color;
import java.util.List;

import java.util.ArrayList;

/**
 * I am a class that generate green colors from dark to light
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 *
 */
public class COGreenGenerator extends COColorsGenerator {

	@Override
	public List<Color> generateColors() {
		List<Color> colors = new ArrayList<Color>();
		for(int i = 1 ; i <= 20 ; i++){
			colors.add(new Color((float)0.01*i,(float)0.05*i,((float)0.01*i)));
		}
		return colors;
	}

	@Override
	public String label() {
		return "Green Generator";
	}

}
