package my.smallProjects.ms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CheckMS {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		List<List<Integer>> listaDeResultados = getListaDeResultados();
						
		int senasAcertadas = 0;
		int quinzenaAcertadas = 0;
		int quadraAcertadas = 0;
		int numeroDeJogosTestados = listaDeResultados.size();
		
		long inicio = System.currentTimeMillis();
		
		for(List<Integer> resultado: listaDeResultados) {
			
			int numSorteado0=resultado.get(0);
			int numSorteado1=resultado.get(1);
			int numSorteado2=resultado.get(2);
			int numSorteado3=resultado.get(3);
			int numSorteado4=resultado.get(4);
			int numSorteado5=resultado.get(5);
			
			FileInputStream stream = new FileInputStream("resultados.log");
	
			InputStreamReader streamReader = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(streamReader);
			String line = null;	
			
			System.out.println("Verificando resultado: "+numSorteado0+" "+numSorteado1+" "+numSorteado2+" "+numSorteado3+" "+numSorteado4+" "+numSorteado5+" ");
						
			while((line=reader.readLine())!=null) {
				
				int numeros[] = parseLinha(line);
				
				if((numeros[0] == numSorteado0) && (numeros[1] == numSorteado1) && (numeros[2] == numSorteado2) && 
						(numeros[3] == numSorteado3) && (numeros[4] == numSorteado4) && (numeros[5] == numSorteado5)) {
					
					senasAcertadas += 1;
					
				} else if((numeros[0] == numSorteado0) && (numeros[1] == numSorteado1) && (numeros[2] == numSorteado2) && 
						(numeros[3] == numSorteado3) && (numeros[4] == numSorteado4)) {
					
					quinzenaAcertadas +=1;
					
				} else if((numeros[0] == numSorteado0) && (numeros[1] == numSorteado1) && (numeros[2] == numSorteado2) && (numeros[3] == numSorteado3)) {
					quadraAcertadas +=1;				
				}
				
			}
			
			reader.close();
			stream.close();
		}
		
		long fim = System.currentTimeMillis();
		System.out.println("Fim da verifica��o.");
		
		long duracao = fim - inicio;
		Date tempoTotal = new Date(duracao);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		
		System.out.println("Tempo de execu��o: "+sdf.format(tempoTotal));
		System.out.println("Total de Jogos testados: "+numeroDeJogosTestados);
		System.out.println("6 dezenas: "+senasAcertadas);
		System.out.println("5 dezenas: "+quinzenaAcertadas);
		System.out.println("4 dezenas: "+quadraAcertadas);

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

}
