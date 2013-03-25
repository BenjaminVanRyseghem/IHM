import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;


public class PlacementComposant {
	
	JWindow window;
	JFrame frame;
	
	public PlacementComposant() {
		this.frame = new JFrame();
		this.window = new JWindow();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
	}
		
	public void setWindow(int width, int height, int x, int y) {
		this.window.setSize(new Dimension(width, height));
		this.window.setLocation(x, y);
	}
	
	public void setFrame(int width, int height, int x, int y) {
		this.frame.setSize(new Dimension(width, height));
		this.frame.setLocation(x, y);
	}
	
	public void addComponentToWindow(JComponent c) {
		this.window.getContentPane().add(c);
	}
	
	public void addComponentToFrame(JComponent c) {
		this.frame.getContentPane().add(c);
	}
	
	public void addComponentToFrame(JComponent c, Object constraints) {
		this.frame.getContentPane().add(c, constraints);
	}
	
	
	public void showFrame() {
		this.toogleShowFrame(true);
	}
	
	public void hideFrame() {
		this.toogleShowFrame(false);
	}
	
	private void toogleShowFrame(boolean b) {
		this.frame.setVisible(b);
	} 
	
	public void showWindow() {
		this.toogleShowWindow(true);
	}
	
	public void hideWindow() {
		this.toogleShowWindow(false);
	}
	
	private void toogleShowWindow(boolean b) {
		this.window.setVisible(b);
	}
	
	public JDialog createDialogModalOnFrame(String title) {
		 return new JDialog(this.frame,title, true);
	}
	
	public JDialog createDialogNonModalOnFrame(String title) {
		 return new JDialog(this.frame, title, false);
	}
	
	public void centerWindow() {
		this.window.setLocationRelativeTo(null);
	}
	
	public void centerFrame() {
		this.frame.setLocationRelativeTo(null);
	}
	
	public void createBorderLayoutForFrame() {
		this.frame.setLayout(new BorderLayout());
		this.addComponentToFrame(new JButton("North"), BorderLayout.NORTH);
		this.addComponentToFrame(new JButton("South"), BorderLayout.SOUTH);
		this.addComponentToFrame(new JButton("East"), BorderLayout.EAST);
		this.addComponentToFrame(new JButton("West"), BorderLayout.WEST);
		this.addComponentToFrame(new JButton("Center"), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		PlacementComposant pc = new PlacementComposant();
		
//		pc.addComponentToWindow(new JLabel("Suce mes boules Ben"));
//		pc.setWindow(200, 200, 500, 500);
//		pc.showWindow();
		
		//pc.addComponentToFrame(new JLabel("Suce mes boules Ben"));
		pc.createBorderLayoutForFrame();
		JDialog d = pc.createDialogModalOnFrame("plop");
		pc.setFrame(200, 200, 500, 500);
		pc.showFrame();
		d.pack();
		d.setLocationRelativeTo(pc.frame);
		d.setVisible(true);
	}
}
