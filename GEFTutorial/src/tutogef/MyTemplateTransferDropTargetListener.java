package tutogef;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;

public class MyTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {

	public MyTemplateTransferDropTargetListener(final EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	protected CreationFactory getFactory(final Object template) {
		return new NodeCreationFactory((Class<?>) template);
	}

}
