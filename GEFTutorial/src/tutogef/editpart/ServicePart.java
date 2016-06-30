package tutogef.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import tutogef.editpolicies.AppDeletePolicy;
import tutogef.editpolicies.AppEditLayoutPolicy;
import tutogef.editpolicies.AppRenamePolicy;
import tutogef.figure.ServiceFigure;
import tutogef.model.Node;
import tutogef.model.Service;

public class ServicePart extends AppAbstractEditPart {
	@Override
	protected IFigure createFigure() {
		IFigure figure = new ServiceFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AppEditLayoutPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AppRenamePolicy());

	}

	@Override
	protected void refreshVisuals() {
		ServiceFigure figure = (ServiceFigure) getFigure();
		Service model = (Service) getModel();
		figure.setName(model.getName());
		figure.setEtage(model.getEtage());
		figure.setLayout(model.getLayout());
	}

	@Override
	public List<Node> getModelChildren() {
		return ((Service) getModel()).getChildrenArray();
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) {
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(Node.PROPERTY_ADD)) {
			refreshChildren();
		}
		if (evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) {
			refreshChildren();
		}
		if (evt.getPropertyName().equals(Node.PROPERTY_RENAME)) {
			refreshVisuals();
		}
	}
}
