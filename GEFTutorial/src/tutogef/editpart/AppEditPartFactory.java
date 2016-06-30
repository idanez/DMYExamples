package tutogef.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import tutogef.model.Employe;
import tutogef.model.Entreprise;
import tutogef.model.Service;

public class AppEditPartFactory implements EditPartFactory {
	@Override
	public EditPart createEditPart(final EditPart context, final Object model) {
		AbstractGraphicalEditPart part = null;
		if (model instanceof Entreprise) {
			part = new EntreprisePart();
		} else if (model instanceof Service) {
			part = new ServicePart();
		} else if (model instanceof Employe) {
			part = new EmployePart();
		}
		part.setModel(model);
		return part;
	}
}