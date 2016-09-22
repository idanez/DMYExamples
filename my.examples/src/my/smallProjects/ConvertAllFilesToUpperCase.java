package my.smallProjects;
import java.io.File;
import java.util.Scanner;

public class ConvertAllFilesToUpperCase {

	public static void main(final String[] args) {
		ConvertAllFilesToUpperCase convert = new ConvertAllFilesToUpperCase();
		convert.getDirectoryFromUser().doIt();
	}

	private String userInput;

	private void doIt() {
		System.out.println("Iniciando processamento");
		System.out.println("");
		File file = new File(userInput);
		if (file.exists()) {
			process(file);
			System.out.println();
			System.out.println("Processo finalizado.");
		} else {
			System.out.println("O caminho informado nao existe...");
		}
	}

	private void process(final File file) {
		File[] listFiles = file.listFiles();
		for (File childFile : listFiles) {
			if (childFile.isDirectory()) {
				System.out.println("----------------------------------------------");
				System.out.println("Lendo diretorio: " + childFile.getAbsolutePath());
				System.out.println("----------------------------------------------");
				process(childFile);
			} else {
				String absolutePath = childFile.getAbsolutePath();
				String name = childFile.getName();
				System.out.println("Renomeando: " + name);
				String tmpPathName = absolutePath.replace(name, name.toUpperCase());
				childFile.renameTo(new File(tmpPathName));
			}
		}
	}

	private ConvertAllFilesToUpperCase getDirectoryFromUser() {
		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.print("Informe o diretorio ou o arquivo: ");
		userInput = reader.next();
		reader.close();
		return this;
	}

}
