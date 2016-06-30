package tutogef.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import tutogef.NodePropertySource;

public class Node implements IAdaptable {

	private String name;
	private Rectangle layout;
	private List<Node> children;
	private Node parent;

	private PropertyChangeSupport listeners;
	public static final String PROPERTY_LAYOUT = "NodeLayout";

	public static final String PROPERTY_ADD = "NodeAddChild";
	public static final String PROPERTY_REMOVE = "NodeRemoveChild";
	public static final String PROPERTY_RENAME = "NodeRename";

	// mise en cache de la IPropertySource pour ne la créer qu'au premier appel de getAdapter()
	private IPropertySource propertySource = null;

	public Node() {
		this.name = "Unknown";
		this.layout = new Rectangle(10, 10, 100, 100);
		this.children = new ArrayList<Node>();
		this.parent = null;

		this.listeners = new PropertyChangeSupport(this);
	}

	public void setName(final String name) {
		String oldName = this.name;
		this.name = name;
		getListeners().firePropertyChange(PROPERTY_RENAME, oldName, this.name);
	}

	public String getName() {
		return this.name;
	}

	public void setLayout(final Rectangle newLayout) {
		Rectangle oldLayout = this.layout;
		this.layout = newLayout;
		getListeners().firePropertyChange(PROPERTY_LAYOUT, oldLayout, newLayout);
	}

	public Rectangle getLayout() {
		return this.layout;
	}

	public boolean addChild(final Node child) {
		boolean b = this.children.add(child);
		if (b) {
			child.setParent(this);
			getListeners().firePropertyChange(PROPERTY_ADD, null, child);
		}
		return b;
	}

	public void setParent(final Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public boolean removeChild(final Node child) {
		boolean removed = this.children.remove(child);
		if (removed) {
			getListeners().firePropertyChange(PROPERTY_REMOVE, child, null);
		}
		return removed;
	}

	public List<Node> getChildrenArray() {
		return this.children;
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter == IPropertySource.class) {
			if (propertySource == null) {
				propertySource = new NodePropertySource(this);
			}
			return propertySource;
		}
		return null;
	}

	// ...
	public boolean contains(final Node child) {
		return children.contains(child);
	}

}
