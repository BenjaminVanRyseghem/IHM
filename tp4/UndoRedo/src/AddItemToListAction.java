import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class AddItemToListAction extends AbstractAction {
    /** */
	private static final long serialVersionUID = 1228528640309618136L;

	UndoGUI undoGUI;
	
	public AddItemToListAction(String text, String desc, Integer mnemonic,UndoGUI undoGUI) {
        super(text);
        this.undoGUI = undoGUI;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }
    
    public void actionPerformed(ActionEvent e) {
    	if (this.undoGUI.getText().getText().length() > 0) {
    		this.undoGUI.getListModel().addElement(this.undoGUI.getText().getText());
    		this.undoGUI.getText().setText("");
		} 
    }
}
