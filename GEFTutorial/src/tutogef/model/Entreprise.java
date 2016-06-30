package tutogef.model;

public class Entreprise extends Node {

	private String address;
	private int capital;

	public static final String PROPERTY_CAPITAL = "EntrepriseCapital";

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setCapital(final int capital) {
		this.capital = capital;
	}

	public String getAddress() {
		return this.address;
	}

	public int getCapital() {
		return this.capital;
	}

}
