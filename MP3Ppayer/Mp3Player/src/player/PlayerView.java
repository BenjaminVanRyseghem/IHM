package player;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class PlayerView implements Observer {
	
	protected JFrame frame;
	protected JButton play;
	protected JButton rewind;
	protected JButton fastForward;
	protected JSlider progressBar;
	protected JTextField search;
	protected JList musicList;

	
	protected PlayerModel model;
	
	protected Timer timer;
	
	protected MusicListModel listModel;
	
	public PlayerView() {
		timer = new Timer(200, new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				PlayerView.this.model.setSearch(PlayerView.this.search.getText());
			}
			
		});
		this.model = new PlayerModel();
		this.model.addObserver(this);
		this.setUpFrame();
	}
	
	public void setUpFrame() {
		this.frame = new JFrame("jTunes");
		this.frame.setLayout(new GridBagLayout());
		
		this.setUpButtons();
		this.setUpSlider();
		this.setUpSearchField();
		this.setUpList();
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);
		
	}
	
	public void setUpButtons() {
		this.play = new JButton("play/pause");
		this.rewind = new JButton("rewind");
		this.fastForward = new JButton("fast forward");
		
		this.frame.add(this.rewind,			this.constraint(0, 1, 1, 1, GridBagConstraints.CENTER));
		this.frame.add(this.play,			this.constraint(1, 1, 1, 1, GridBagConstraints.CENTER));
		this.frame.add(this.fastForward,	this.constraint(2, 1, 1, 1, GridBagConstraints.CENTER));
	}
	
	public void setUpSlider() {
		this.progressBar = new JSlider();
		this.progressBar.setPreferredSize(new Dimension(300, 30));
		
		this.frame.add(this.progressBar, this.constraint(0, 0, 3, 1, GridBagConstraints.CENTER));
	}
	
	protected void keyTyped(KeyEvent event){
		timer.restart();
	}
	
	public void setUpSearchField() {
		this.search = new JTextField("", 10);
		this.search.addKeyListener(new KeyListener(){

					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						PlayerView.this.keyTyped(arg0);
					}
			
		});
		this.frame.add(this.search, this.constraint(4, 0, 1, 1, GridBagConstraints.CENTER));
	}
	
	public void setUpList() {
		this.listModel = new MusicListModel();
		this.listModel.addElements(this.model.getMusics());
		
		this.musicList = new JList(this.listModel);
		
		JScrollPane sc = new JScrollPane(this.musicList);
		sc.setPreferredSize(new Dimension(400,400));
		
		this.frame.add(sc, this.constraint(0, 2, 4, 1, GridBagConstraints.CENTER));
	}
	
	public GridBagConstraints constraint(int gridX, int gridY, int gridWidth, int gridHeight, int anchor) {
		return new GridBagConstraints(gridX, gridY, gridWidth, gridHeight, 0, 0, 
				anchor, 0, new Insets(0, 0, 0, 0), 0, 0);
	}

	@Override
	public void update(Observable o, Object arg) {
		MPUpdate update = (MPUpdate) arg;
		update.applyTo(this);
//		if(bool){
//			this.listModel.removeAllElements();
//			this.listModel.addElements(this.model.getMusics());
//		
//		}
		
		
	}

	public void update(MPOneUpdate mpOneUpdate) {
		listModel.addElement(mpOneUpdate.getElement());
		musicList.validate();
	}

	public void update(MPAllUpdate mpAllUpdate) {
		this.listModel.removeAllElements();
		this.listModel.addElements(this.model.getMusics());
	}

	public void update(MPUpdate mpUpdate) {
		// Do not do anything
	}
}
