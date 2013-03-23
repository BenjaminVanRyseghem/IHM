package color.generator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CORedGenerator extends COColorsGenerator {

	@Override
	public List<Color> generateColors() {
		List<Color> colors = new ArrayList<Color>();
		for(int i = 1 ; i <= 10 ; i++){
			colors.add(new Color((float)0.1*i,(float)0.02*i,((float)0.02*i)));
		}
		return colors;
	}

	@Override
	public String label() {
		return "Red Generator";
	}

}
