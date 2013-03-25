import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VBoxLayout implements LayoutManager {
	
	List<Component> list;
	Container parent;
	
	public VBoxLayout() {
		this.list = new ArrayList<Component>();
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		this.list.add(comp);
		this.compute();
	}

	@Override
	public void layoutContainer(Container parent) {
		this.parent = parent;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getMinimumSize();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getPreferredSize();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.list.remove(comp);
		this.compute();
	}
	
	private void compute() {
		
	}
}
