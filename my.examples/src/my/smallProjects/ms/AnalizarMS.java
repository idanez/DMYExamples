package my.smallProjects.ms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AnalizarMS {

	static int tudoEmSequencia = 0;
	static int sequencia5Primeiros = 0;
	static int sequencia4Primeiros = 0;
	static int sequencia3Primeiros = 0;
	static int sequenciaQualquerDe3=0;
	static int sequenciaQualquerDe4=0;
	static int sequenciaQualquerDe5=0;
	static int tudoImpar = 0;
	static int tudoPar = 0;
	static int cincoPrimos = 0;
	static int quatroPrimos = 0;
	static int todosPrimos = 0;

	static int acertosDeQuadra =0;
	private static int acertosDeQuina =0;
	private static int acertosDeSena=0;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		List<List<Integer>> listaDeResultados = getListaDeResultados();

		int totalDeFalhas=0;
		int totalDeJogosTestados = listaDeResultados.size();

		List<Integer[]> listaMeusNumeros = definirMeuNumeros();//new ArrayList<Integer[]>();

		int jogo=0;

		for(List<Integer> resultado: listaDeResultados) {

			int numSorteado0=resultado.get(0);
			int numSorteado1=resultado.get(1);
			int numSorteado2=resultado.get(2);
			int numSorteado3=resultado.get(3);
			int numSorteado4=resultado.get(4);
			int numSorteado5=resultado.get(5);

			Integer[] numeros = {numSorteado0,numSorteado1,numSorteado2,numSorteado3,numSorteado4,numSorteado5};

			testarSequencias(numeros);

			if(MS.isTudoImpar(numeros)) {
				tudoImpar+=1;
			} else if(MS.isTudoPar(numeros)) {
				tudoPar+=1;
			}

			//Criar metodo para testar com numeros baixos e altos
			//Criar metodo para analisar, e informar quantas vezes cada numero foi sorteado
			
			
			//testarPrimos(numeros);
			jogo+=1;
			verificaMeusNumeros(listaMeusNumeros, numeros,jogo);

		}

		int[] resTestes = {tudoEmSequencia,tudoImpar,tudoPar,todosPrimos,cincoPrimos,quatroPrimos,
				sequencia5Primeiros,sequencia4Primeiros,sequencia3Primeiros,sequenciaQualquerDe3,sequenciaQualquerDe4,sequenciaQualquerDe5};

		totalDeFalhas = somarTudo(resTestes);

		System.out.println("");
		System.out.println("***************");
		System.out.println("Acertos de quadra: "+acertosDeQuadra);
		System.out.println("Acertos de quina: "+acertosDeQuina);
		System.out.println("Acertos de sena: "+acertosDeSena);
		System.out.println("***************");
		System.out.println("");

		System.out.println("Total de jogos testados: "+totalDeJogosTestados);
		System.out.println("");

		boolean verificarZero = true;

		mostrarResultado("Todos os numeros em sequencia: ",tudoEmSequencia,verificarZero);
		mostrarResultado("5 Primeiros numeros em sequencia: ",sequencia5Primeiros,verificarZero);
		mostrarResultado("4 Primeiros numeros em sequencia: ",sequencia4Primeiros,verificarZero);
		mostrarResultado("3 Primeiros numeros em sequencia: ",sequencia3Primeiros,verificarZero);
		mostrarResultado("Jogos com sequencia de 3 qualquer: ",sequenciaQualquerDe3,verificarZero);
		mostrarResultado("Jogos com sequencia de 4 qualquer: ",sequenciaQualquerDe4,verificarZero);
		mostrarResultado("Jogos com sequencia de 5 qualquer: ",sequenciaQualquerDe5,verificarZero);
		mostrarResultado("Tudo IMPAR: ",tudoImpar,verificarZero);
		mostrarResultado("Tudo PAR: ",tudoPar,verificarZero);
		mostrarResultado("Tudo PRIMO: ",todosPrimos,verificarZero);
		mostrarResultado("Com 5 Primos: ",cincoPrimos,verificarZero);
		mostrarResultado("Com 4 Primos: ",quatroPrimos,verificarZero);

		System.out.println("");
		System.out.println("TOTAL DE FALHAS: "+totalDeFalhas);

		int porcentagem = (totalDeFalhas*100)/totalDeJogosTestados;
		System.out.println("");
		int sucesso = 100-porcentagem;
		System.out.println("Taxa de SUCESSO : "+sucesso+"%");
		System.out.println("Taxa de FALHA   : "+porcentagem+"%");


	}

	/**
	 * Adicionar nesses metodos os numeros jogados
	 * @return
	 */
	private static List<Integer[]> definirMeuNumeros() {
		//Criar um array abaixo para cada Jogo
		Integer[] meusNumeros1 = {3,6,17,26,28,40};
		Integer[] meusNumeros2 = {2,13,25,44,54,56};
		Integer[] meusNumeros3 = {7,8,24,28,47,53};
		Integer[] meusNumeros4 = {6,9,23,35,56,58};
		Integer[] meusNumeros5 = {2,4,27,34,35,47};
		Integer[] meusNumeros6 = {8,14,15,37,44,57};

		//Os Jogos abaixo sao referentes ao bolao da TOTVS para mega da virada de 2012
		Integer[] meusNumeros7 ={1,3,7,12,19,24,27,30,50,59};
		Integer[] meusNumeros8 ={7,12,14,27,35,39,41,56,60};
		Integer[] meusNumeros9 ={2,3,7,17,21,27, 33, 45, 51};
		Integer[] meusNumeros10={4, 7, 13, 25, 38, 50, 52, 56};
		Integer[] meusNumeros11={3,7, 12, 25,34, 39, 51, 53};
		Integer[] meusNumeros12={4, 7, 10, 11, 30, 35, 51};
		Integer[] meusNumeros13={17 ,26 ,30 ,34 ,39, 43, 53};
		Integer[] meusNumeros14={11, 18, 21, 23, 35, 41, 43};
		Integer[] meusNumeros15={7 ,11 ,14 ,23 ,27 ,36, 50};
		Integer[] meusNumeros16={6, 7, 10, 17, 20, 23, 47};
		Integer[] meusNumeros17={7 ,13 ,26 ,37 ,51, 54, 60};
		Integer[] meusNumeros18={13, 21, 22, 28, 30, 51, 53};
		Integer[] meusNumeros19={4, 21, 22, 30, 31, 39, 45};
		Integer[] meusNumeros20={1, 6, 19,23, 38, 54};
		Integer[] meusNumeros21={16 ,23 ,37 ,38 ,45, 52};
		Integer[] meusNumeros22={5, 19, 23 ,48, 51, 56};
		Integer[] meusNumeros23={1 ,6 ,7 ,16 ,22, 56};
		Integer[] meusNumeros24={8, 12, 14, 33, 37, 55};
		Integer[] meusNumeros25={16 ,23 ,35 ,37 ,44 ,59};
		Integer[] meusNumeros26={1, 6, 12, 14, 23, 47};
		Integer[] meusNumeros27={2 ,10 ,17 ,23 ,35 ,47};
		Integer[] meusNumeros28={19, 23, 27, 45, 51, 59};

//		Adiciona cada jogo na lista de jogos, se nao quiser testar algum(ns) jogo(s), basta comentar a linha referente ao(s) jogo(s)
		List<Integer[]> listaMeusNumeros = new ArrayList<Integer[]>();
		listaMeusNumeros.add(meusNumeros1);
		listaMeusNumeros.add(meusNumeros2);
		listaMeusNumeros.add(meusNumeros3);
		listaMeusNumeros.add(meusNumeros4);
		listaMeusNumeros.add(meusNumeros5);
		listaMeusNumeros.add(meusNumeros6);

		listaMeusNumeros.add(meusNumeros7);
		listaMeusNumeros.add(meusNumeros8);
		listaMeusNumeros.add(meusNumeros9);
		listaMeusNumeros.add(meusNumeros10);
		listaMeusNumeros.add(meusNumeros11);
		listaMeusNumeros.add(meusNumeros12);
		listaMeusNumeros.add(meusNumeros13);
		listaMeusNumeros.add(meusNumeros14);
		listaMeusNumeros.add(meusNumeros15);
		listaMeusNumeros.add(meusNumeros16);
		listaMeusNumeros.add(meusNumeros17);
		listaMeusNumeros.add(meusNumeros18);
		listaMeusNumeros.add(meusNumeros19);
		listaMeusNumeros.add(meusNumeros20);
		listaMeusNumeros.add(meusNumeros21);
		listaMeusNumeros.add(meusNumeros22);
		listaMeusNumeros.add(meusNumeros23);
		listaMeusNumeros.add(meusNumeros24);
		listaMeusNumeros.add(meusNumeros25);
		listaMeusNumeros.add(meusNumeros26);
		listaMeusNumeros.add(meusNumeros27);
		listaMeusNumeros.add(meusNumeros28);

		return listaMeusNumeros;
	}

	private static void mostrarResultado(String texto, int resultado, boolean verificarZero) {
		if(verificarZero) {
			if(resultado>0)
				System.out.println(texto+resultado);
		}else{
			System.out.println(texto+resultado);
		}
	}

	private static void verificaMeusNumeros(List<Integer[]> listaMeusNumeros, Integer[] numerosResultadoDoSorteio, int jogo) {

		for(int idxListaMeusNums=0;idxListaMeusNums < listaMeusNumeros.size();idxListaMeusNums++) {
			
			Integer[] meusNumerosNoJogo = listaMeusNumeros.get(idxListaMeusNums);

			jogo+=1;
			List<Integer> listaDeNumerosAcertados = new ArrayList<Integer>();

			for(int indexMeusNums=0;indexMeusNums<meusNumerosNoJogo.length;indexMeusNums++) {
				for(int iNumSorteado=0; iNumSorteado < numerosResultadoDoSorteio.length; iNumSorteado++) {

					if(meusNumerosNoJogo[indexMeusNums] <= numerosResultadoDoSorteio[iNumSorteado]) {
						if(meusNumerosNoJogo[indexMeusNums] == numerosResultadoDoSorteio[iNumSorteado]) 
							listaDeNumerosAcertados.add(meusNumerosNoJogo[indexMeusNums]);							
					}						
				}
			}
			int numDoJogo = idxListaMeusNums+1;
			
			if(listaDeNumerosAcertados.size() > 5) {
				System.out.println("Meu Jogo "+numDoJogo+"  - RESULTADO: "+jogo+" - ACERTOU "+listaDeNumerosAcertados.size()+" numeros: "+listaDeNumerosAcertados);
				acertosDeSena+=1;
				
			} else if(listaDeNumerosAcertados.size() > 4) {
				System.out.println("Meu Jogo "+numDoJogo+"  - RESULTADO: "+jogo+" - ACERTOU "+listaDeNumerosAcertados.size()+" numeros: "+listaDeNumerosAcertados);
				acertosDeQuina+=1;
				
			} else if(listaDeNumerosAcertados.size() > 3) {
				System.out.println("Meu Jogo "+numDoJogo+"  - RESULTADO: "+jogo+" - ACERTOU "+listaDeNumerosAcertados.size()+" numeros: "+listaDeNumerosAcertados);
				acertosDeQuadra+=1;
			}
		}
	}

	private static int somarTudo(int[] resTestes) {

		int soma = 0;

		for(int resultado: resTestes) {
			soma+=resultado;
		}

		return soma;
	}

	private static void testarSequencias(Integer[] numeros) {

		if(MS.isTudoEmSequencia(numeros, 6)) {
			tudoEmSequencia+=1;
		} else if(MS.isTudoEmSequencia(numeros, 5)) {
			sequencia5Primeiros+=1;
		} else if(MS.isTudoEmSequencia(numeros, 4)) {
			sequencia4Primeiros+=1;
		} else if(MS.isTudoEmSequencia(numeros, 3)) {
			sequencia3Primeiros+=1;
		} else if(MS.existeSequencia(numeros, 5)) {
			sequenciaQualquerDe5+=1;
		} else if(MS.existeSequencia(numeros, 4)) {
			sequenciaQualquerDe4+=1;
			//System.out.println(numeros[0]+" "+numeros[1]+" "+numeros[2]+" "+numeros[3]+" "+numeros[4]+" "+numeros[5]);
		} else if(MS.existeSequencia(numeros, 3)) {
			sequenciaQualquerDe3+=1;
		} 

	}

	private static void testarPrimos(Integer[] numeros) {

		boolean num1IsPrimo = MS.verificaPrimo((double)numeros[0]);
		boolean num2IsPrimo = MS.verificaPrimo((double)numeros[1]);
		boolean num3IsPrimo = MS.verificaPrimo((double)numeros[2]);
		boolean num4IsPrimo = MS.verificaPrimo((double)numeros[3]);
		boolean num5IsPrimo = MS.verificaPrimo((double)numeros[4]);
		boolean num6IsPrimo = MS.verificaPrimo((double)numeros[5]);

		boolean isTodosPrimos = num1IsPrimo && num2IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;

		if(isTodosPrimos)
			todosPrimos+=1;
		else if(MS.verifica5Primos(num1IsPrimo, num2IsPrimo, num3IsPrimo, num4IsPrimo, num5IsPrimo, num6IsPrimo)) 
			cincoPrimos+=1;
		else if(MS.verifica4Primos(num1IsPrimo, num2IsPrimo, num3IsPrimo, num4IsPrimo, num5IsPrimo, num6IsPrimo)) 
			quatroPrimos+=1;

	}

	private static int[] parseLinha(String line) {

		int[] numeros = {0,0,0,0,0,0};
		try{
			numeros[0] = Integer.parseInt(line.substring(0, 2));
			numeros[1] = Integer.parseInt(line.substring(3, 5));
			numeros[2] = Integer.parseInt(line.substring(6, 8));
			numeros[3] = Integer.parseInt(line.substring(9, 11));
			numeros[4] = Integer.parseInt(line.substring(12, 14));
			numeros[5] = Integer.parseInt(line.substring(15, 17));
		} catch(NumberFormatException e) {
			return numeros;
		}
		return numeros;
	}

	private static List<List<Integer>> getListaDeResultados() throws IOException {

		FileInputStream stream = new FileInputStream("resultadosMS.txt");

		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamReader);
		String line = null;	

		List<Integer> resultado = new ArrayList<Integer>();
		List<List<Integer>> listaDeResultado = new ArrayList<List<Integer>>();

		while((line=reader.readLine())!=null) {

			resultado = hardParseLinha(line);
			listaDeResultado.add(resultado);

		}
		reader.close();
		streamReader.close();

		return listaDeResultado;
	}

	private static List<Integer> hardParseLinha(String line) {

		String tmpLine="";
		boolean flagMontado = false;

		List<Integer> inteiros = new ArrayList<Integer>();

		for(int index=0;index < line.length()+1;index++) {

			if(index < line.length()) {
				if(line.charAt(index) != '\\' && line.charAt(index) != 't' && line.charAt(index) != '\t'){
					tmpLine += String.valueOf(line.charAt(index));
				} else {
					flagMontado = true;
				}
			} else {
				flagMontado = true;
			}

			if(flagMontado){
				inteiros.add(Integer.parseInt(tmpLine));
				flagMontado = false;
				tmpLine = "";
			}				

		}

		Collections.sort(inteiros);

		return inteiros;
	}

}
