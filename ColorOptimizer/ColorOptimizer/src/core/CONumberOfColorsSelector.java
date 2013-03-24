package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import color.generator.COStandardColorsGenerator;

public class CONumberOfColorsSelector {

	JFrame frame;
	JLabel label;
	JSlider slider;
	JLabel numbers;
	JButton ok;
	
	public CONumberOfColorsSelector(){
		frame = new JFrame("Choose number of colors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("Select the number of wanted colors");
		slider = new JSlider(1, 10);
		slider.setValue(4);
		slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				numbers.setText(CONumberOfColorsSelector.this.slider.getValue() + "");
			}
		});
		numbers = new JLabel("4");
		ok = new JButton("Ok");
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				int number = CONumberOfColorsSelector.this.slider.getValue();
				List<Color> colors = new ArrayList<Color>();
				int delta = 255 / (number+1);
				int i = 0;
				int gray = 0;
				
				while (i < number){
					colors.add(new Color(gray, gray, gray));
					gray += delta;
					i++;
				}
				
				COModel model = new COModel(new COStandardColorsGenerator(), colors);
				COView view = new COView(model);
				view.show();
				CONumberOfColorsSelector.this.frame.setVisible(false);
			}

		});
	}
	
		
	protected void show(){
		Container buttons = new Container();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		buttons.add(Box.createHorizontalGlue());
		buttons.add(this.ok);
		
		Container container = this.frame.getContentPane();
		
		container.setLayout(new BorderLayout());
		container.add(this.label, BorderLayout.NORTH);
		container.add(this.slider, BorderLayout.CENTER);
		container.add(this.numbers, BorderLayout.WEST);
		container.add(buttons, BorderLayout.SOUTH);
		
		this.frame.setVisible(true);
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		
		new CONumberOfColorsSelector().show();
	}	
	
}
