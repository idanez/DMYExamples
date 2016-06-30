package tutogef;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MyEditorInput implements IEditorInput {

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return null;
	}

	public String name = null;

	public MyEditorInput(final String name) {
		this.name = name;
	}

	@Override
	public boolean exists() {
		return (this.name != null);
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof MyEditorInput)) {
			return false;
		}
		return ((MyEditorInput) o).getName().equals(getName());
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.name;
	}

}
