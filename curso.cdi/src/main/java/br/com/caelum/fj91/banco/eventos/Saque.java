package br.com.caelum.fj91.banco.eventos;

import java.math.BigDecimal;

import br.com.caelum.fj91.banco.modelo.Conta;

/**
 * Representa evento de sacar dinheiro 
 */
public class Saque {

	private final BigDecimal valorSacado;
	private final Conta conta;

	public Saque(String valorSacado, Conta conta) {
		this.valorSacado = new BigDecimal(valorSacado);
		this.conta = conta;
	}

	public BigDecimal getValorSacado() {
		return valorSacado;
	}
	
	public Conta getConta() {
		return conta;
	}
}
