package tutogef.part.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import tutogef.model.Entreprise;
import tutogef.model.Node;

public class EntrepriseTreeEditPart extends AppAbstractTreeEditPart {
	@Override
	protected List<Node> getModelChildren() {
		return ((Entreprise) getModel()).getChildrenArray();
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