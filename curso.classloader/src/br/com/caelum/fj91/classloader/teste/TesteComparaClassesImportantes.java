package br.com.caelum.fj91.classloader.teste;

import java.net.URL;
import java.net.URLClassLoader;

public class TesteComparaClassesImportantes {

	public static void main(String[] args) throws Exception {
		
		// cria um novo classloader a partir da pasta bin
		ClassLoader loader = new URLClassLoader(new URL[] { new URL("file:bin/") }, null);
		
		// carrega a classe pelo novo classloader
		Class<?> classe = loader.loadClass("java.lang.String");

		// carrega a mesma classe pelo classloader atual, do main, o application classloader
		Class<?> outraClasse = Class.forName("java.lang.String");

		// executa alguns testes
		System.out.println("ClassLoader da 1a classe: " + classe.getClassLoader());
		System.out.println("ClassLoader da 2a classe: " + outraClasse.getClassLoader());
		System.out.println("Classes são iguais? " + (classe == outraClasse));

		Object o = classe.newInstance();

	}
}
