package tutogef.part.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import tutogef.editpolicies.AppDeletePolicy;
import tutogef.model.Employe;
import tutogef.model.Node;

public class EmployeTreeEditPart extends AppAbstractTreeEditPart {

	@Override
	protected List<Node> getModelChildren() {
		return ((Employe) getModel()).getChildrenArray();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
	}

	@Override
	public void refreshVisuals() {
		Employe model = (Employe) getModel();
		setWidgetText(model.getName() + " " + model.getPrenom());
		setWidgetImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW));
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROPERTY_ADD)) {
			refreshChildren();
		}
		if (evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) {
			refreshChildren();
		}

	}

}
