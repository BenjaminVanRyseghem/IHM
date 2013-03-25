/**
 * @author <a href="mailto:gery.casiez@lifl.fr">Gery Casiez</a>
 */

import java.awt.event.*;

import javax.swing.*;

public class UndoGUI {
	
	private DefaultListModel listModel;
	private JTextField text;
	private JList list;
	
	public UndoGUI() {
		// JFrame
		JFrame fen = new JFrame("Undo/Redo");
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// text field
		this.text = new JTextField();
		
		// actions
		Action addItemToList = new AddItemToListAction(	"Add", 
														"Add the text of the text field to the list", 
														KeyEvent.VK_ENTER, this);
		
		Action removeItemFromList = new RemoveItemFromListAction(	"Remove Item", 
																	"Remove the selected item of the list", 
																	KeyEvent.VK_DELETE, this);
		
		// Initialize the List
		this.listModel = new DefaultListModel();
		this.listModel.addElement("Universite Lille 1");
		this.listModel.addElement("Master informatique");
		this.listModel.addElement("IHM");
		this.list = new JList(this.listModel);
		JScrollPane listScroller = new JScrollPane(list);
		
		// Create the buttons
		JButton addButton = new JButton(addItemToList);
		JButton removeButton = new JButton(removeItemFromList);
		JButton undoButton = new JButton("Annuler");
		JButton redoButton = new JButton("Refaire");
		
		
		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Edition");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem(removeItemFromList);
		menu.add(menuItem);
		menuItem = new JMenuItem("Annuler");
		menu.add(menuItem);
		menuItem = new JMenuItem("Refaire");
		menu.add(menuItem);
		
		// ToolBar
		JToolBar toolBar = new JToolBar("Barre d'outils");
		

		toolBar.add(removeButton);
		toolBar.add(undoButton);
		toolBar.add(redoButton);
		
		
		// Add items to the frame
		fen.setJMenuBar(menuBar);
		fen.add(toolBar);
		fen.getContentPane().setLayout(new BoxLayout(fen.getContentPane(),BoxLayout.Y_AXIS));
		fen.getContentPane().add(listScroller);
		fen.getContentPane().add(text);
		fen.getContentPane().add(addButton);
		fen.pack();
		fen.setVisible(true);		
	}
	
	public DefaultListModel getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel listModel) {
		this.listModel = listModel;
	}

	public JTextField getText() {
		return text;
	}

	public void setText(JTextField text) {
		this.text = text;
	}

	public JList getList() {
		return list;
	}

	public void setList(JList list) {
		this.list = list;
	}

	public static void main(String[] args) {
	    //Schedule a job for the event-dispatching thread:
	    //creating and showing this application's GUI.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			new UndoGUI();
		    }
		});
	}

}