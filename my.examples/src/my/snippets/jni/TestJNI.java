package my.snippets.jni;

public class TestJNI {
	public native void greetings();

	static {
		System.loadLibrary("greet");
	}

	public static void main(final String args[]) {
		new TestJNI().greetings();
	}
}
