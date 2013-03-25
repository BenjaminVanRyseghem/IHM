	import java.awt.event.ActionEvent;

	import javax.swing.AbstractAction;
	
public class RemoveItemFromListAction extends AbstractAction {

	    /** */
		private static final long serialVersionUID = 1228528640309618136L;

		UndoGUI undoGUI;
		
		public RemoveItemFromListAction(String text, String desc, Integer mnemonic,UndoGUI undoGUI) {
	        super(text);
	        this.undoGUI = undoGUI;
	        putValue(SHORT_DESCRIPTION, desc);
	        putValue(MNEMONIC_KEY, mnemonic);
	    }
	    
	    public void actionPerformed(ActionEvent e) {
	    	int index = this.undoGUI.getList().getSelectedIndex();
		    if (index != -1) this.undoGUI.getListModel().remove(index);
	    }
}
