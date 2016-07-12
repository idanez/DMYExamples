package br.com.caelum.fj91.banco.tributacao;

import java.math.BigDecimal;

import br.com.caelum.fj91.banco.modelo.Conta;

public interface Tributo {

	/**
	 * Calcula o desconto que deve ser aplicado a essa Conta
	 * @param conta
	 * @return Desconto a ser efetuado
	 */
	public abstract BigDecimal calculaDesconto(Conta conta);

}