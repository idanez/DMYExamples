package br.com.caelum.fj91.banco.modelo;

import java.math.BigDecimal;
import java.util.Calendar;

import br.com.caelum.fj91.banco.tributacao.Tributo;

public class Conta {

	private BigDecimal saldo = BigDecimal.ZERO;
	private BigDecimal limite = BigDecimal.ZERO;
	
	private Cliente titular;
	private final int numero;
	private final Calendar dataAbertura;
	
	public Conta(int numero, Calendar dataAbertura) {
		this.numero = numero;
		this.dataAbertura = dataAbertura;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public Calendar getDataAbertura() {
		return dataAbertura;
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}

	public BigDecimal getLimite() {
		return limite;
	}
	
	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}
	
	public Cliente getTitular() {
		return titular;
	}
	
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}
	
	@Override
	public String toString() {
		return "{Conta #" + this.numero + "}";
	}

	/**
	 * Saca dinheiro de uma conta bancária
	 * @param valor Valor a ser sacado
	 */
	public void saca(BigDecimal valor) {
		BigDecimal novoSaldo = getSaldo().subtract(valor);

		if (novoSaldo.compareTo(limite.negate()) >= 0) {
			this.saldo = novoSaldo;
		} else {
			throw new RuntimeException("Saldo insuficiente");
		}
	}

	/**
	 * Deposita dinheiro em uma conta bancária
	 * @param valor Valor a ser sacado
	 */
	public void deposita(BigDecimal valor) {
		BigDecimal novoSaldo = getSaldo().add(valor);
		this.saldo = novoSaldo;
	}
	
	/**
	 * Transfere dinheiro entre contas.
	 * @param outra Conta para onde transferir
	 * @param valor Valor a ser transferido
	 */
	public void transferePara(Conta outra, BigDecimal valor) {
		this.saca(valor);
		outra.deposita(valor);
	}
	
	/**
	 * @return Devolve true se a conta está com saldo negativo
	 */
	public boolean estaNegativa() {
		return this.saldo.compareTo(BigDecimal.ZERO) < 0;
	}
	
	/**
	 * Desconta o valor devido do tributo
	 * @param tributo Regra do tributo a ser aplicado
	 */
	public void aplica(Tributo tributo) {
		this.saldo = saldo.subtract(tributo.calculaDesconto(this));
	}
	
	/**
	 * Verifica se a conta é especial, começa com 1
	 */
	public boolean especial() {
		return Integer.valueOf(numero).toString().charAt(0) == '1';
	}
	
	/**
	 * @return Devolve true se a conta está usando mais de 80% do limite
	 */
	public boolean estaUsandoOitentaPorcentoDoLimite() {
		final BigDecimal oitentaPorcento = new BigDecimal("0.80");
		
		return this.saldo.compareTo(BigDecimal.ZERO) < 0 &&
				this.saldo.abs().compareTo(limite.multiply(oitentaPorcento)) >= 0;
	}
}