package tutogef.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import tutogef.model.Employe;

public class EmployeChangeLayoutCommand extends AbstractLayoutCommand {
	private Employe model;
	private Rectangle layout;
	private Rectangle oldLayout;

	@Override
	public void execute() {
		model.setLayout(layout);
	}

	@Override
	public void setConstraint(final Rectangle rect) {
		this.layout = rect;
	}

	@Override
	public void setModel(final Object model) {
		this.model = (Employe) model;
		this.oldLayout = ((Employe) model).getLayout();
	}

	@Override
	public void undo() {
		this.model.setLayout(this.oldLayout);
	}

}