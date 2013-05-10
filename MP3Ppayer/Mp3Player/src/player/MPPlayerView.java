package player;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import support.MPListDropHandler;
import support.MPMusicListModel;
import support.MPMusicListener;
import updates.MPActionUpdate;
import updates.MPAllUpdate;
import updates.MPOneUpdate;
import updates.MPSwitchUpdate;
import updates.MPUpdate;

/**
 * This class is the main view of @MPPlayerModel. It's composed one multiple swing components
 * @author Lepan François, Benjamin Van Ryseghem
 *
 */
public class MPPlayerView implements Observer, DragGestureListener, DragSourceListener, MPMusicListener {
	
	private boolean mustUpdateSlider = true;
	
	protected DragSource dragSource;
	protected JFrame frame;
	
	protected JButton play;
	protected JButton next;
	protected JButton previous;
	protected JButton randomize;
	protected JButton undo;
	protected JButton redo;
	
	protected JSlider slider;
	protected JSlider soundSlider;
	
	protected JTextField search;
	
	public JList musicList;
	
	protected MPPlayerModel model;
	
	protected Timer timer;
	
	protected MPMusicListModel listModel;
	
	protected JLabel timeElapsed;
	protected JLabel remainingTime;
	protected JLabel currentSongName;
	
	public MPPlayerView(MPPlayerModel model) {
		this.timer = new Timer(200, new SeachActionListener());
		this.model = model;
		this.model.addObserver(this);
		this.dragSource = new DragSource();
		
		// initialize the components and their place in the frame
		this.setUpFrame();
		
		this.model.addPlayerListener(this);
	}
	
	public void setUpFrame() {
		this.frame = new JFrame("jTunes");
		this.frame.setLayout(new GridBagLayout());
		this.frame.setPreferredSize(new Dimension(560,530));
		this.frame.setMinimumSize(new Dimension(560,530));
		// initialize the components
		this.setUpButtons();
		this.setUpSlider();
		this.setUpSearchField();
		this.setUpList();
		this.setUpLabels();
		
		// place the component in the frame
		this.setUpUI();
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	public void setUpLabels() {
		this.timeElapsed  = new JLabel("0");
		this.remainingTime  = new JLabel("0");
		this.currentSongName = new JLabel("...");
	}
	
	public void setUpButtons() {
		this.play = new JButton(new ImageIcon(getClass().getClassLoader().getResource("./icons/play.png")));
		this.next = new JButton(new ImageIcon(getClass().getClassLoader().getResource("./icons/next.png")));
		this.previous = new JButton(new ImageIcon(getClass().getClassLoader().getResource("./icons/previous.png")));
		this.randomize = new JButton(new ImageIcon(getClass().getClassLoader().getResource("./icons/random.png")));
		this.undo = new JButton(new ImageIcon(getClass().getClassLoader().getResource("./icons/arrow_undo.png")));
		this.redo = new JButton(new ImageIcon(getClass().getClassLoader().getResource("./icons/arrow_redo.png")));
		
		this.undo.setEnabled(this.model.canUndo());
		this.redo.setEnabled(this.model.canRedo());
		
		
		next.addActionListener(new NextActionListener());

		previous.addActionListener(new PreviousActionListener());
		
		play.addActionListener(new PlayActionListener(this.model));
		undo.addActionListener(new UndoActionListener(this.model));
		redo.addActionListener(new RedoActionListener(this.model));
		randomize.addActionListener(new RandomizeActionListener(this.model));
	}
	
	public void setUpSlider() {
		this.slider = new JSlider(0, 100, 0);
		this.slider.setPreferredSize(new Dimension(200, 30));
		this.slider.addMouseListener(new SliderMouseListener());
		
		this.soundSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, 0);
		this.soundSlider.setPreferredSize(new Dimension(30, 100));
		this.soundSlider.setValue((int)(this.model.getVolume() * 100));
		this.soundSlider.addChangeListener(new SoundSliderChangeListener());
	}
	
	public void setUpList() {
		this.listModel = new MPMusicListModel(this.model.getMusics());
		
		this.musicList = new JList(this.listModel);
		
		dragSource.createDefaultDragGestureRecognizer(musicList, DnDConstants.ACTION_COPY_OR_MOVE, this);
		musicList.setTransferHandler(new MPListDropHandler(musicList, this.model));
	}
	
	public void setUpSearchField() {
		this.search = new JTextField("", 10);
		this.search.addKeyListener(new SearchFieldKeyListener());
	}
	
	private void setUpUI() {
		this.frame.add(this.timeElapsed, this.constraint(0, 1, 1, 1, GridBagConstraints.CENTER));
		this.frame.add(this.slider, this.constraint(1, 1, 3, 1, GridBagConstraints.CENTER));
		this.frame.add(this.remainingTime, this.constraint(4, 1, 1, 1, GridBagConstraints.CENTER));

		this.frame.add(this.currentSongName, this.constraint(1, 0, 3, 1, GridBagConstraints.CENTER));
		
		this.frame.add(this.previous, this.constraint(3, 2, 1, 1, GridBagConstraints.WEST));
		this.frame.add(this.play, this.constraint(3, 2, 1, 1, GridBagConstraints.CENTER,new Insets(0, 0, 0, 30)));
		this.frame.add(this.randomize, this.constraint(3, 2, 1, 1, GridBagConstraints.CENTER,new Insets(0, 30, 0, 0)));
		this.frame.add(this.next, this.constraint(3, 2, 1, 1, GridBagConstraints.EAST));

		this.frame.add(new JLabel("Search for:"), this.constraint(5, 0, 1, 1, GridBagConstraints.CENTER, new Insets(0, 20, 0, 0)));
		this.frame.add(this.search, this.constraint(6, 0, 2, 1, GridBagConstraints.CENTER));
		
		
		this.frame.add(this.undo, this.constraint(6, 2, 1, 1, GridBagConstraints.EAST));
		this.frame.add(this.redo, this.constraint(7, 2, 1, 1, GridBagConstraints.WEST));
		
		this.frame.add(this.soundSlider, this.constraint(8, 0, 1, 3, GridBagConstraints.CENTER, new Insets(0, 20, 0, 0)));
		
		JScrollPane sc = new JScrollPane(this.musicList);
		sc.setPreferredSize(new Dimension(500,400));
		this.frame.add(sc, this.constraint(0, 4, 10, 1, GridBagConstraints.CENTER));
	}
	
	protected void keyTyped(KeyEvent event){
		timer.restart();
	}
	
	/**
	 * Create some constraints. This is a convenient method
	 * @param gridX
	 * @param gridY
	 * @param gridWidth
	 * @param gridHeight
	 * @param anchor
	 * @return
	 */
	private GridBagConstraints constraint(int gridX, int gridY, int gridWidth, int gridHeight, int anchor) {
		return this.constraint(gridX, gridY, gridWidth, gridHeight, anchor, new Insets(0, 0, 0, 0));
	}
	
	public GridBagConstraints constraint(int gridX, int gridY, int gridWidth, int gridHeight, int anchor, Insets insets) {
		return new GridBagConstraints(gridX, gridY, gridWidth, gridHeight, 0, 0, 
				anchor, 0, insets, 0, 0);
	}

	@Override
	public void update(Observable o, Object arg) {
		MPUpdate update = (MPUpdate) arg;
		update.applyTo(this);
	}

	public void update(MPOneUpdate mpOneUpdate) {
		listModel.fireAllChanged();
	}
	
	public void update(MPSwitchUpdate update) {
		this.listModel.fireIntervalChanged(update.getDragIndex(), update.getDropIndex());
	}

	public void update(MPAllUpdate mpAllUpdate) {
		this.listModel.setElements(this.model.getMusics());
	}
	
	public void update(MPActionUpdate update) {
		this.undo.setEnabled(update.canUndo());
		this.redo.setEnabled(update.canRedo());
	}

	public void update(MPUpdate mpUpdate) {
		// Do not do anything
	}

	
	public int getSelectedIndex() {
		return this.musicList.getSelectedIndex();
	}
	
	public int locationToIndex(Point point){
		return musicList.locationToIndex(point);
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {}

	@Override
	public void dragExit(DragSourceEvent dse) {}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		StringSelection transferable = new StringSelection(Integer.toString(musicList.getSelectedIndex()));
	    dragSource.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
	}

	@Override
	public void musicStarted(int duration) {
		this.slider.setMaximum(duration);
		this.currentSongName.setText(this.model.getCurrentMp3Name());
		
		this.remainingTime.setText(this.model.getRemainingTime());
		this.timeElapsed.setText(this.model.getTimeElapsed());
	}

	@Override
	public void musicPositionChanged(int position) {
		if(mustUpdateSlider)
			this.slider.setValue(position);
		
		this.remainingTime.setText(this.model.getRemainingTime());
		this.timeElapsed.setText(this.model.getTimeElapsed());
	}

	@Override
	public void musicEnded() {
		this.model.playNextSong();
	}
	
	private class PreviousActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MPPlayerView.this.model.playPreviousSong();
		}
	}

	private class NextActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MPPlayerView.this.model.playNextSong();	
		}
	}

	private class SoundSliderChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			int value = MPPlayerView.this.soundSlider.getValue();
			MPPlayerView.this.model.setVolume((float)(value) / 100);
		}
	}

	private class SliderMouseListener implements MouseListener {
		@Override
		public void mouseReleased(MouseEvent arg0) {
			JSlider slider = MPPlayerView.this.slider;
			int value = slider.getValue();
			MPPlayerView.this.model.setPlayerPosition(value);
			MPPlayerView.this.mustUpdateSlider = true;
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			MPPlayerView.this.mustUpdateSlider = false;
		}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseClicked(MouseEvent arg0) {}
	}

	private class PlayActionListener implements ActionListener {
		MPPlayerModel model;
		public PlayActionListener(MPPlayerModel model) {
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			model.playOrPause();
		}
	}

	private class RedoActionListener implements ActionListener {
		MPPlayerModel model;
		public RedoActionListener(MPPlayerModel model) {
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.model.redo();
		}
	}

	private class UndoActionListener implements ActionListener {
		MPPlayerModel model;
		public UndoActionListener(MPPlayerModel model) {
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.model.undo();
		}
	}

	private class RandomizeActionListener implements ActionListener {
		MPPlayerModel model;

		public RandomizeActionListener(MPPlayerModel m) {
			model = m;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.randomize();
		}
	}

	private class SearchFieldKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {
			MPPlayerView.this.keyTyped(arg0);
		}
	}

	private class SeachActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MPPlayerView.this.model.setSearch(MPPlayerView.this.search.getText());
		}
	}
	
}
