package tutogef.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public abstract class AbstractLayoutCommand extends Command {
	public abstract void setConstraint(Rectangle rect);

	public abstract void setModel(Object model);
}
