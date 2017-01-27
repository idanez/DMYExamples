package my.snippets.jni;

public class CalculadoraJNI {
	// declaração do método nativo
	public native int soma(int num1, int num2);

	// Bloco estático para carregar a biblioteca "somador"
	static {
		System.out.println("entrei");
		try {
			System.loadLibrary("ProjetoC");
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
