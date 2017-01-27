package my.snippets.jni;

public class HelloWorld {
	private native void print();

	public static void main(final String[] args) {
		new HelloWorld().print();
	}

	static {
		System.loadLibrary("HelloWorld");
	}
}
