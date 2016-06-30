package tutogef.editpart;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import tutogef.editpolicies.AppDeletePolicy;
import tutogef.figure.EmployeFigure;
import tutogef.model.Employe;
import tutogef.model.Node;

public class EmployePart extends AppAbstractEditPart {
	@Override
	protected IFigure createFigure() {
		IFigure figure = new EmployeFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
	}

	@Override
	protected void refreshVisuals() {
		EmployeFigure figure = (EmployeFigure) getFigure();
		Employe model = (Employe) getModel();
		figure.setName(model.getName());
		figure.setFirstName(model.getPrenom());
		figure.setLayout(model.getLayout());
	}

	@Override
	public List<Node> getModelChildren() {
		return new ArrayList<Node>();
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) {
			refreshVisuals();
		}
	}
}
