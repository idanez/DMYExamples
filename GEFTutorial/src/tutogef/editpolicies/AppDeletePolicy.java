package tutogef.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tutogef.commands.DeleteCommand;

public class AppDeletePolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(final GroupRequest deleteRequest) {
		DeleteCommand command = new DeleteCommand();
		command.setModel(getHost().getModel());
		command.setParentModel(getHost().getParent().getModel());
		return command;
	}
}
