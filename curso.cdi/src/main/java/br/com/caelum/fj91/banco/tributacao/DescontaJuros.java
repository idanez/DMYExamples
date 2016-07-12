package br.com.caelum.fj91.banco.tributacao;

import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.caelum.fj91.banco.config.TaxaJuros;
import br.com.caelum.fj91.banco.modelo.Conta;

/**
 * Aplica uma certa taxa de juros ao valor negativo do saldo da Conta. 
 */
public class DescontaJuros implements Tributo {

	@Inject @TaxaJuros
	private BigDecimal taxaDeJuros;
	
	public BigDecimal calculaDesconto(Conta conta) {
		BigDecimal desconto = BigDecimal.ZERO;
		
		if (conta.estaNegativa()) {
			BigDecimal valorEmprestado = conta.getSaldo().abs();
			desconto = valorEmprestado.multiply(taxaDeJuros);
		}
		
		return desconto;
	}
}
