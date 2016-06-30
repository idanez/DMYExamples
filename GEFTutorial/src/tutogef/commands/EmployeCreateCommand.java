package tutogef.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import tutogef.model.Employe;
import tutogef.model.Service;

public class EmployeCreateCommand extends Command {

	private Service srv;
	private Employe emp;

	public EmployeCreateCommand() {
		super();
		srv = null;
		emp = null;
	}

	public void setService(final Object s) {
		if (s instanceof Service) {
			this.srv = (Service) s;
		}
	}

	public void setEmploye(final Object e) {
		if (e instanceof Employe) {
			this.emp = (Employe) e;
		}
	}

	public void setLayout(final Rectangle r) {
		if (emp == null) {
			return;
		}
		emp.setLayout(r);
	}

	@Override
	public boolean canExecute() {
		if (srv == null || emp == null) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		srv.addChild(emp);
	}

	@Override
	public boolean canUndo() {
		if (srv == null || emp == null) {
			return false;
		}
		return srv.contains(emp);
	}

	@Override
	public void undo() {
		srv.removeChild(emp);
	}

}
