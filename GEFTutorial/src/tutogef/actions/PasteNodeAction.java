package tutogef.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import tutogef.commands.PasteNodeCommand;

public class PasteNodeAction extends SelectionAction {

	public PasteNodeAction(final IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setText("Paste");
		setId(ActionFactory.PASTE.getId());
		setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}

	private Command createPasteCommand() {
		return new PasteNodeCommand();
	}

	@Override
	protected boolean calculateEnabled() {
		Command command = createPasteCommand();
		return command != null && command.canExecute();
	}

	@Override
	public void run() {
		Command command = createPasteCommand();
		if (command != null && command.canExecute()) {
			execute(command);
		}
	}

}
