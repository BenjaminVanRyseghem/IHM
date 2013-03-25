import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class RgbControler implements ActionListener, ChangeListener {

	RgbView view;
	
	public RgbControler(RgbView view) {
		this.view = view;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		
		if (source == this.view.sliderR) {
			int value = this.view.sliderR.getValue();
			this.setComponentValues(value, this.view.tfRed, this.view.sliderR);
			Color col = this.view.color.getBackground();
			this.setBackgroundColor(value,col.getGreen(),col.getBlue());
		
		} else if (source == this.view.sliderG) {
			int value = this.view.sliderG.getValue();
			this.setComponentValues(value, this.view.tfGreen, this.view.sliderG);
			Color col = this.view.color.getBackground();
			this.setBackgroundColor(col.getRed(),value,col.getBlue());
		
		} else {
			int value = this.view.sliderB.getValue();
			this.setComponentValues(value, this.view.tfBlue, this.view.sliderB);
			Color col = this.view.color.getBackground();
			this.setBackgroundColor(col.getRed(),col.getGreen(),value);
			
		}
		
		this.setHexaFieldFromSliders();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		try {
			if (source == this.view.tfRed) {
				String s = this.view.tfRed.getText();
				int i = Integer.parseInt(s);
				this.setComponentValues(i, this.view.tfRed, this.view.sliderR);
				Color col = this.view.color.getBackground();
				this.setBackgroundColor(i,col.getGreen(),col.getBlue());
				this.setHexaFieldFromTextField();
				
			} else if (source == this.view.tfGreen) {
				String s = this.view.tfGreen.getText();
				int i = Integer.parseInt(s);
				this.setComponentValues(i, this.view.tfGreen, this.view.sliderG);
				Color col = this.view.color.getBackground();
				this.setBackgroundColor(col.getRed(),i,col.getBlue());
				this.setHexaFieldFromTextField();
				
			} else if (source == this.view.tfBlue) {
				String s = this.view.tfBlue.getText();
				int i = Integer.parseInt(s);
				this.setComponentValues(i, this.view.tfBlue, this.view.sliderB);
				Color col = this.view.color.getBackground();
				this.setBackgroundColor(col.getRed(),col.getGreen(),i);		
				this.setHexaFieldFromTextField();
				
			} else {
				this.setComponentsValuesFromHexaTextField();
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this.view.frame, "You must enter an Integer in the fields");
		}
	}
	
	private void setComponentValues(int value, JTextField tf, JSlider slider) {
		if (value >= 0 && value <= 255) {
			slider.removeChangeListener(this);
			tf.removeActionListener(this);
			
			tf.setText(String.valueOf(value));
			slider.setValue(value);
			
			tf.addActionListener(this);
			slider.addChangeListener(this);
			
		} else {
			JOptionPane.showMessageDialog(this.view.frame, "You must enter an Integer between 0 and 255");
		}
	}
	
	private void setHexaFieldFromTextField() throws NumberFormatException{
		this.view.tfHexaCode.removeActionListener(this);
		int b = Integer.parseInt(this.view.tfBlue.getText());
		int r = Integer.parseInt(this.view.tfRed.getText());
		int g = Integer.parseInt(this.view.tfGreen.getText());
		if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
			String hexa = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
			this.view.tfHexaCode.setText(hexa);
		}
		this.view.tfHexaCode.addActionListener(this);
	}
	
	private void setHexaFieldFromSliders() {
		this.view.tfHexaCode.removeActionListener(this);
		int b = this.view.sliderB.getValue();
		int g = this.view.sliderG.getValue();
		int r = this.view.sliderR.getValue();
		String hexa = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
		this.view.tfHexaCode.setText(hexa);
		this.view.tfHexaCode.addActionListener(this);
	}
	
	private void setComponentsValuesFromHexaTextField() {
		try {
		Color color = Color.decode("0x"+this.view.tfHexaCode.getText());
		
		this.setComponentValues(color.getBlue(),this.view.tfBlue,this.view.sliderB);
		this.setComponentValues(color.getRed(),this.view.tfRed,this.view.sliderR);
		this.setComponentValues(color.getGreen(),this.view.tfGreen,this.view.sliderG);
		this.view.color.setBackground(new Color(color.getRed(),color.getGreen(),color.getBlue()));
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this.view.frame, "You must enter an Hexa Representation of the color");
		}
	}
	
	private void setBackgroundColor(int r, int g, int b) {
		if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255)
			this.view.color.setBackground(new Color(r,g,b));
	}
}
