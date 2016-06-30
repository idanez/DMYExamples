package tutogef.part.tree;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import tutogef.model.Employe;
import tutogef.model.Entreprise;
import tutogef.model.Service;

public class AppTreeEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(final EditPart context, final Object model) {
		EditPart part = null;
		if (model instanceof Entreprise) {
			part = new EntrepriseTreeEditPart();
		} else if (model instanceof Service) {
			part = new ServiceTreeEditPart();
		} else if (model instanceof Employe) {
			part = new EmployeTreeEditPart();
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}

}
