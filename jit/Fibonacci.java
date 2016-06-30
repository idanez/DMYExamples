
public class Fibonacci {
	public static void main(String[] args) {
		long num = Long.parseLong(args[0]);
		System.out.println(fibonacci(num));
	}

	private static long fibonacci(long num) {
		if (num < 2)
			return 1;
		return fibonacci(num - 1) + fibonacci(num - 2);
	}
}

