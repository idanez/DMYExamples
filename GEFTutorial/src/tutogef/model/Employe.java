package tutogef.model;

import org.eclipse.draw2d.geometry.Rectangle;

public class Employe extends Node {

	private String prenom;
	public static final String PROPERTY_FIRSTNAME = "EmployePrenom";

	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Employe emp = new Employe();
		emp.setName(this.getName());
		emp.setParent(this.getParent());
		emp.setPrenom(this.prenom);
		emp.setLayout(new Rectangle(getLayout().x + 10, getLayout().y + 10, getLayout().width, getLayout().height));
		return emp;
	}

}
