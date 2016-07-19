package my.smallProjects.ms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			verificarJogosCom6();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//verificarJogosCom10();
						
		

	}

	private static void verificarJogosCom10() {
		
		
	}

	private static void verificarJogosCom6() throws IOException {
		
		int custo = 0;
		int totalDeJogos = 0;
		
		long inicio = System.currentTimeMillis();
		
		File file = new File("resultados.log");
		
		FileWriter writer = new FileWriter(file, true);
				
		for(int num1=1;num1<61;num1++) {
			
			//System.out.println("Mudou num1");
			
			for(int num2=num1+1;num2<61;num2++) {
				
				//System.out.println("Mudou num2");
				
				for(int num3=num2+1;num3<61;num3++) {

					//System.out.println("Mudou num3");
					
					for(int num4=num3+1;num4<61;num4++) {
						
						//System.out.println("Mudou num4");
						
						for(int num5=num4+1;num5<61;num5++) {
							
							//System.out.println("Mudou num5");
							
							for(int num6=num5+1;num6<61;num6++) {
																
								Integer[] numeros = {num1,num2,num3,num4,num5,num6};
																
								if(!isTudoImpar(numeros) && !isTudoPar(numeros) && !isTudoEmSequencia(numeros, numeros.length) &&
										!isTudoEmSequencia(numeros, numeros.length-1) && !isTudoEmSequencia(numeros, numeros.length-2)) {
								
									boolean num1IsPrimo = verificaPrimo((double)num1);
									boolean num2IsPrimo = verificaPrimo((double)num2);
									boolean num3IsPrimo = verificaPrimo((double)num3);
									boolean num4IsPrimo = verificaPrimo((double)num4);
									boolean num5IsPrimo = verificaPrimo((double)num5);
									boolean num6IsPrimo = verificaPrimo((double)num6);
									
									boolean isTodosPrimos = num1IsPrimo && num2IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;
									
									if(!isTodosPrimos) {
									
										boolean isCincoPrimos = verifica5Primos(num1IsPrimo,num2IsPrimo,num3IsPrimo,num4IsPrimo,num5IsPrimo,num6IsPrimo);
																								
										if(!isCincoPrimos) {
											
											boolean isQuatroPrimos = verifica4Primos(num1IsPrimo,num2IsPrimo,num3IsPrimo,num4IsPrimo,num5IsPrimo,num6IsPrimo);
											
											if(!isQuatroPrimos) {
												custo += 2;
												totalDeJogos +=1;
											
												
												
												writer.write(formataNum(num1)+" "+formataNum(num2)+" "+formataNum(num3)+" "+formataNum(num4)+" "+formataNum(num5)+" "+formataNum(num6));
												writer.write("\n");																															
											}				
										}
									}
								}							
							}
							
						}
						
					}
					
				}
				
			}
			
		}
								
		long fim = System.currentTimeMillis();
		
		long duracao = fim - inicio;
		
		Date date = new Date(duracao);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		System.out.println("Dura��o do c�digo: "+sdf.format(date));
		System.out.println("Custo dos jogos � de: R$ "+custo);
		System.out.println("Quantidade de jogos: "+totalDeJogos);
		
		writer.write("Dura��o do c�digo: "+sdf.format(date)+"\n");
		writer.write("Custo dos jogos � de: R$ "+custo+"\n");
		writer.write("Quantidade de jogos: "+totalDeJogos+"\n");
				
		writer.close();	
		
	}

	public static String formataNum(int num) {
		
		String numeroFormatado = "";
		
		if(num < 10) {
			numeroFormatado += "0";
		}
		
		numeroFormatado += String.valueOf(num);
		
		return numeroFormatado;
	}

	public static boolean verifica4Primos(boolean num1IsPrimo,boolean num2IsPrimo, boolean num3IsPrimo, boolean num4IsPrimo,boolean num5IsPrimo, boolean num6IsPrimo) {
		
		boolean isQuatroPrimos1 = num1IsPrimo && num2IsPrimo && num3IsPrimo && num4IsPrimo;
		boolean isQuatroPrimos2 = num1IsPrimo && num2IsPrimo && num3IsPrimo && num5IsPrimo;
		boolean isQuatroPrimos3 = num1IsPrimo && num2IsPrimo && num3IsPrimo && num6IsPrimo;
		
		boolean isQuatroPrimos4 = num1IsPrimo && num2IsPrimo && num4IsPrimo && num5IsPrimo;
		boolean isQuatroPrimos5 = num1IsPrimo && num2IsPrimo && num4IsPrimo && num6IsPrimo;
		boolean isQuatroPrimos6 = num1IsPrimo && num2IsPrimo && num5IsPrimo && num6IsPrimo;
				
		boolean isQuatroPrimos7 = num1IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo;
		boolean isQuatroPrimos8 = num1IsPrimo && num3IsPrimo && num4IsPrimo && num6IsPrimo;
		boolean isQuatroPrimos9 = num1IsPrimo && num3IsPrimo && num5IsPrimo && num6IsPrimo;
		
		boolean isQuatroPrimos10= num2IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo;
		boolean isQuatroPrimos11= num2IsPrimo && num3IsPrimo && num4IsPrimo && num6IsPrimo;
		boolean isQuatroPrimos12= num2IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;
		
		boolean isQuatroPrimos13= num3IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;
				
		return isQuatroPrimos1 || isQuatroPrimos2 || isQuatroPrimos3 || isQuatroPrimos4 || isQuatroPrimos5 || isQuatroPrimos6 ||
				isQuatroPrimos7 || isQuatroPrimos8 || isQuatroPrimos9 || isQuatroPrimos10 || isQuatroPrimos11 || isQuatroPrimos12 ||
				isQuatroPrimos13;
				
	}

	public static boolean verifica5Primos(boolean num1IsPrimo,boolean num2IsPrimo, boolean num3IsPrimo, boolean num4IsPrimo,boolean num5IsPrimo, boolean num6IsPrimo) {
		
		boolean isCincoPrimos1 = num1IsPrimo && num2IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo;
		boolean isCincoPrimos2 = num1IsPrimo && num2IsPrimo && num3IsPrimo && num4IsPrimo && num6IsPrimo;
		boolean isCincoPrimos3 = num1IsPrimo && num2IsPrimo && num3IsPrimo && num5IsPrimo && num6IsPrimo;
		boolean isCincoPrimos4 = num1IsPrimo && num2IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;
		boolean isCincoPrimos5 = num1IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;
		boolean isCincoPrimos6 = num2IsPrimo && num3IsPrimo && num4IsPrimo && num5IsPrimo && num6IsPrimo;
		
		return isCincoPrimos1 || isCincoPrimos2 || isCincoPrimos3 || isCincoPrimos4 || isCincoPrimos5 || isCincoPrimos6;
		
	}

	public static boolean verificaPrimo(Double numero) {
					
		if(numero == 2 || numero == 1)
			return true;
		
		Double raizQuadrada = Math.sqrt(numero);
		int parteInteiraDaraizQuadrada = raizQuadrada.intValue();
		
		for(int index=2;index<parteInteiraDaraizQuadrada;index++) {
			
			if(parteInteiraDaraizQuadrada % index == 0)
				return false;
			
		}
		
		return true;
	}
	
	public static boolean isTudoImpar(Integer[] numeros) {
		for(int numero:numeros) {
			if(numero % 2 == 0)
				return false;
		}
		return true;		
	}
	
	public static boolean isTudoPar(Integer[] numeros) {
		for(int numero:numeros) {
			if(numero % 2 != 0)
				return false;
		}
		return true;		
	}
	
	public static boolean isTudoEmSequencia(Integer[] numeros, int qtdeDeNumerosParaTestar) {
		
		if(qtdeDeNumerosParaTestar > numeros.length)
			return false;
		
		for(int index=0; index < qtdeDeNumerosParaTestar-1;) {
									
			if(index + 1 == numeros.length)
				break;
			
			int num1 = numeros[index];
			int num2 = numeros[index+1];
			
			if(num1 != num2-1)
				return false;
				
			index+=1;
								
		}		
		return true;
	}
	
	public static boolean existeSequencia(Integer[] numeros, int qtdeDeNumerosEmSequencia) {
		
		if(qtdeDeNumerosEmSequencia > numeros.length)
			return false;
		
		int numEmSequencia = 1;
		int numAnterior = 0;
		
		for(int index=0;index < numeros.length; index++) {
			
			if(numAnterior == 0)
				numAnterior = numeros[index];
			
			if((numeros[index] != numAnterior) && (numAnterior == numeros[index]-1)) {
				
				numEmSequencia+=1;
								
			} else {
				numEmSequencia=1;
			}
			
			numAnterior = numeros[index];
			
			if(numEmSequencia == qtdeDeNumerosEmSequencia) {
				return true;
			}
			
		}
		
		return false;
		
	}

}
