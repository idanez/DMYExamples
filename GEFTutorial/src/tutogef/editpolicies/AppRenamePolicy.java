package tutogef.editpolicies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import tutogef.commands.RenameCommand;

public class AppRenamePolicy extends AbstractEditPolicy {

	@Override
	public Command getCommand(final Request request) {
		if (request.getType().equals("rename")) {
			return createRenameCommand(request);
		}
		return null;
	}

	protected Command createRenameCommand(final Request renameRequest) {
		RenameCommand command = new RenameCommand();
		command.setModel(getHost().getModel());
		command.setNewName((String) renameRequest.getExtendedData().get("newName"));
		return command;
	}
}
