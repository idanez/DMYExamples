package br.com.caelum.fj91.banco.tributacao;

import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.caelum.fj91.banco.config.TaxaFixa;
import br.com.caelum.fj91.banco.modelo.Conta;

/**
 * Todas as contas com saldo negativo devem pagar uma taxa mensal
 * fixa pelo uso do Cheque Especial. 
 */
public class TributaUsoDoChequeEspecial implements Tributo {
	
	@Inject @TaxaFixa
	private BigDecimal taxaFixa;
	
	@Override
	public BigDecimal calculaDesconto(Conta conta) {
		if (conta.estaNegativa()) {
			return taxaFixa;
		} else {
			return BigDecimal.ZERO;
		}
	}
}
