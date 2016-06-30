package tutogef.commands;

import org.eclipse.gef.commands.Command;

import tutogef.model.Node;

public class DeleteCommand extends Command {
	private Node model;
	private Node parentModel;

	@Override
	public void execute() {
		this.parentModel.removeChild(model);
	}

	public void setModel(final Object model) {
		this.model = (Node) model;
	}

	public void setParentModel(final Object model) {
		parentModel = (Node) model;
	}

	@Override
	public void undo() {
		this.parentModel.addChild(model);
	}
}