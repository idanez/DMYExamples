package tutogef.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import tutogef.model.Service;

public class ServiceChangeLayoutCommand extends AbstractLayoutCommand {
	private Service model;
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
		this.model = (Service) model;
		this.oldLayout = ((Service) model).getLayout();
	}

	// Méthode hérité appelé lors de l'action undo.
	@Override
	public void undo() {
		this.model.setLayout(this.oldLayout);
	}

}