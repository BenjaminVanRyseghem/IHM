package color.generator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class COBlueGenerator extends COColorsGenerator {

	@Override
	public List<Color> generateColors() {
		List<Color> colors = new ArrayList<Color>();
		for(int i = 1 ; i <= 20 ; i++){
			colors.add(new Color((float)0.01*i,(float)0.01*i,((float)0.05*i)));
		}
		return colors;
	}

	@Override
	public String label() {
		return "Blue Generator";
	}

}
