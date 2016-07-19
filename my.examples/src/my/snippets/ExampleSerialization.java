package my.snippets;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ExampleSerialization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private int age;
	private ExampleSerialization spouse;
	
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public int getAge() { return age; }
	public ExampleSerialization getSpouse() { return spouse; }
	public void setFirstName(String value) { firstName = value; }
	public void setLastName(String value) { lastName = value; }
	public void setAge(int value) { age = value; }
	public void setSpouse(ExampleSerialization value) { spouse = value; }
	
	public ExampleSerialization(String fn, String ln, int a) {
		this.firstName = fn; this.setLastName(ln); this.age = a;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ExampleSerialization s = new ExampleSerialization("Daniel", "Motta", 30);
		
		ExampleSerialization S = new ExampleSerialization("Cami", "Brag", 29);
		
		System.out.println("Objeto gravado: "+s.toString());
		System.out.println("Objeto gravado: "+S.toString());
		FileOutputStream fo;
		try {
			fo = new FileOutputStream("test.ser");
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			
			oo.writeObject(s); // serializo objeto cat
			oo.writeObject(S);
		    oo.close();
			
			ExampleSerialization s2 = new ExampleSerialization("t", "t", 1);
			
			FileInputStream fi = new FileInputStream("test.ser");
			ObjectInputStream oi = new ObjectInputStream(fi);
			
			boolean terminou = false;
			
			while(!terminou) {
				try {
					s2 =(ExampleSerialization) oi.readObject();
					System.out.println("Objeto recuperado: "+s2.toString());
				}catch (EOFException e) {
					terminou = true;
				}			
			}
						
			oi.close();
								
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public String toString()
    {
        return "[Person: firstName=" + firstName + 
            " lastName=" + lastName +
            " age=" + age +
             "]";
    }    
	
	public void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException
	{
		// "Encrypt"/obscure the sensitive data
		age = age << 2;
		stream.defaultWriteObject();
	}

	public void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException
	{
		stream.defaultReadObject();

		// "Decrypt"/de-obscure the sensitive data
		age = age << 2;
	}

}
