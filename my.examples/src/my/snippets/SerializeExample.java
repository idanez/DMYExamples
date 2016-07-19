package my.snippets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeExample implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private int age;
	private SerializeExample spouse;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public SerializeExample getSpouse() {
		return spouse;
	}

	public void setFirstName(String value) {
		firstName = value;
	}

	public void setLastName(String value) {
		lastName = value;
	}

	public void setAge(int value) {
		age = value;
	}

	public void setSpouse(SerializeExample value) {
		spouse = value;
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		// "Encrypt"/obscure the sensitive data
		age = age << 2;
		stream.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
		stream.defaultReadObject();

		// "Decrypt"/de-obscure the sensitive data
		age = age << 2;
	}

	public SerializeExample(String fn, String ln, int a) {
		this.firstName = fn;
		this.setLastName(ln);
		this.age = a;
	}

	public static void Main(String args[]) {

		SerializeExample s = new SerializeExample("Daniel", "Motta", 30);
		System.out.println("Objeto gravado: " + s.getFirstName() + " - " + s.getLastName() + " - " + s.getAge());
		FileOutputStream fo;
		try {
			fo = new FileOutputStream("test.ser");
			ObjectOutputStream oo = new ObjectOutputStream(fo);

			s.writeObject(oo);

			SerializeExample s2 = new SerializeExample("t", "t", 1);

			FileInputStream fi = new FileInputStream("test.ser");
			ObjectInputStream oi = new ObjectInputStream(fi);
			s2 = (SerializeExample) oi.readObject();
			oi.close();

			System.out.println("Objeto recuperado: " + s2.getFirstName() + " - " + s2.getLastName() + " - " + s2.getAge());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String toString() {
		return "[Person: firstName=" + firstName + " lastName=" + getLastName() + " age=" + age + " spouse="
				+ (spouse != null ? spouse.getFirstName() : "[null]") + "]";
	}

}