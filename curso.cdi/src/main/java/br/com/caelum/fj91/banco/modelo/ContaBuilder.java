package br.com.caelum.fj91.banco.modelo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ContaBuilder {

	public static ObrigatorioNumero novaConta() {
		return new ObrigatorioNumero();
	}
	
	// começo da interface, obriga a passar o numero
	public static class ObrigatorioNumero {
		public ObrigatorioData comNumero(int numero) {
			return new ObrigatorioData(numero);
		}
	}
	
	// obriga a passar a data
	public static class ObrigatorioData {
		private final int numero;
		private ObrigatorioData(int numero) {
			this.numero = numero;
		}
		
		public OutrosDados comDataDeAbertura(Calendar data) {
			return new OutrosDados(numero, data);
		}
		
		public OutrosDados comDataDeAbertura(int ano, int mes, int dia) {
			return new OutrosDados(numero, new GregorianCalendar(ano, mes, dia));
		}		
	}
	
	// fim da interface, metodos auxiliares
	public static class OutrosDados {
		private Conta conta;
		private OutrosDados(int numero, Calendar data) {
			this.conta = new Conta(numero, data);
		}
		
		public OutrosDados comLimite(String valor) {
			conta.setLimite(new BigDecimal(valor));
			return this;
		}
		
		public OutrosDados eDepositoInicial(String valor) {
			conta.deposita(new BigDecimal(valor));
			return this;
		}
		
		public Conta toConta() {
			return conta;
		}
	}
	
}
