package my.snippets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralTestsComMetodoMain {


	public static void main(String args[]) throws Exception {
		Pattern p = Pattern.compile("..ava*");
		String input = "this is a Java test";
		Matcher m = p.matcher(input);

		System.out.println("result=" + m.find());
	}

}
