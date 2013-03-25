import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class RgbView {
	
	protected JSlider sliderR;
	protected JSlider sliderG;
	protected JSlider sliderB;
	
	protected JTextField tfHexaCode;
	protected JTextField tfRed;
	protected JTextField tfGreen;
	protected JTextField tfBlue;
	
	protected JPanel color;
	
	protected JFrame frame;
	
	protected RgbControler controler;
	
	public RgbView() {
		this.controler = new RgbControler(this);
		
		this.sliderR = new JSlider(0, 255);
		this.sliderG = new JSlider(0, 255);
		this.sliderB = new JSlider(0, 255);
		
		this.tfRed = new JTextField("127",5);
		this.tfGreen = new JTextField("127",5);
		this.tfBlue = new JTextField("127",5);
		
		this.color = new JPanel();
		this.color.setBackground(new Color(127,127,127));
		this.color.setPreferredSize(new Dimension(83, 80));
		
		this.tfHexaCode = new JTextField("7f7f7f",6);
		
		this.frame = new JFrame();
		
		this.sliderR.addChangeListener(this.controler);
		this.sliderG.addChangeListener(this.controler);
		this.sliderB.addChangeListener(this.controler);
		this.tfRed.addActionListener(this.controler);
		this.tfGreen.addActionListener(this.controler);
		this.tfBlue.addActionListener(this.controler);
		this.tfHexaCode.addActionListener(this.controler);
	}
	
	private JPanel globalPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(this.sliderR, this.getGridConst(0, 1));
		panel.add(this.sliderG, this.getGridConst(0, 2));
		panel.add(this.sliderB, this.getGridConst(0, 3));
		panel.add(this.tfRed, this.getGridConst(2, 1));
		panel.add(this.tfGreen, this.getGridConst(2, 2));
		panel.add(this.tfBlue, this.getGridConst(2, 3));
		panel.add(this.tfHexaCode, this.getGridConst(3, 0));
		panel.add(this.color, this.getGridConst(3, 1, 1, 3));
		return panel;
	}
	
	public void displayFrame() {
		this.frame.add(this.globalPanel());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setTitle("RGB Selector");
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	public GridBagConstraints getGridConst(int gridx, int gridy) {
		return new GridBagConstraints(gridx, gridy, 1, 1, 
				0, 0, GridBagConstraints.LINE_START, 0,
				new Insets(0, 0, 0, 0), 0, 0);
	}
	
	public GridBagConstraints getGridConst(int gridx, int gridy, int gridW, int gridH) {
		return new GridBagConstraints(gridx, gridy, gridW, gridH, 
				0, 0, GridBagConstraints.LINE_START, 0,
				new Insets(0, 0, 0, 0), 0, 0);
	}
	
	public static void main(String[] args) {
		RgbView view = new RgbView();
		view.displayFrame();
	}
}
