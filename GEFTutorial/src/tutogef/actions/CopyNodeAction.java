package tutogef.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import tutogef.commands.CopyNodeCommand;
import tutogef.model.Node;

public class CopyNodeAction extends SelectionAction {

	public CopyNodeAction(final IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setText("Copy");
		setId(ActionFactory.COPY.getId());
		setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	private Command createCopyCommand(final List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}
		CopyNodeCommand cmd = new CopyNodeCommand();
		Iterator<Object> it = selectedObjects.iterator();
		while (it.hasNext()) {
			EditPart ep = (EditPart) it.next();
			Node node = (Node) ep.getModel();
			if (!cmd.isCopyableNode(node)) {
				return null;
			}
			cmd.addElement(node);
		}
		return cmd;
	}

	@Override
	protected boolean calculateEnabled() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd == null) {
			return false;
		}
		return cmd.canExecute();
	}

	@Override
	public void run() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			cmd.execute();
		}
	}
}