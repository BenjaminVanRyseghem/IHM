package support;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import core.COModel;

/**
 * I am a class that is used for the copy paste action in the interface.
 * 
 * @author Benjamin Van Ryseghem, Francois Lepan
 */
public class ClipBoardComponent extends DualColorComponent {

	private static final long serialVersionUID = 5237526207016737544L;

	public ClipBoardComponent(COModel m, Color col) {
		super(m, col);
		this.setMaximumSize(new Dimension(100,20));
		this.color.setMaximumSize(new Dimension(50,20));
		this.gray.setMaximumSize(new Dimension(50,20));
		
		this.setBorder(new LineBorder(Color.black, 1));
		
		new DropTargetListener(this);
	}

	protected void addFocusHandling() {}
	
	public ClipBoardComponent(COModel m) {
		this(m, m.paste());
	}
	
	public void setBackground(Color col){
		this.model.copy(col);
	}

	public void update() {
		Color col = this.model.paste();
		this.update(col);
	}
	
}
