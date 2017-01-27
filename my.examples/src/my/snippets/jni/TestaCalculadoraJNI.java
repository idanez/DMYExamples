package my.snippets.jni;

public class TestaCalculadoraJNI {
	public static void main(final String[] args) {
		System.out.println("Iniciando JNI");
		int num1 = Integer.parseInt(args[0]);
		int num2 = Integer.parseInt(args[1]);
		System.out.println("Lendo numeros: " + num1 + " e " + num2);
		CalculadoraJNI calc = new CalculadoraJNI();
		System.out.println("Iniciado");

		int resultado = calc.soma(num1, num2);
		System.out.println("A soma é: " + resultado);
	}
}
