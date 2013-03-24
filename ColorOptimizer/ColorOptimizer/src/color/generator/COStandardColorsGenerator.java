package color.generator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * I am use to generate standard colors, aka colors from the full spectrum 
 */
public class COStandardColorsGenerator extends COColorsGenerator {
	
	@Override
	public List<Color> generateColors() {
		List<Color> colors = new ArrayList<Color>();
		int shades = 20;
		
		for(int i = 0 ; i < shades ; i++){
			float red = ((float)0.30*i);
			red = red - ((int)red);
			
			float green = ((float)0.59*i);
			green = green- ((int)green);
			
			float blue = ((float)0.11*i);
			blue = blue- ((int)blue);
			
			colors.add(new Color(red, green, blue));
		}
		return colors;
	}

	@Override
	public String label() {
		return "Standard colors";
	}

}
