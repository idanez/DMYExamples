package br.com.caelum.fj91.banco.eventos;

import java.math.BigDecimal;

import javax.enterprise.event.Observes;

import br.com.caelum.fj91.banco.modelo.Conta;

/**
 * Contas de clientes especiais (começam com "1"),
 * tem o limite aumentado automaticamente caso estejam
 * se aproximando do fim. 
 */
public class AumentaLimiteEmContasEspeciais {

	public void observaContasEspeciais(@Observes Saque saque) {
		Conta conta = saque.getConta();
		
		if (conta.especial() && conta.estaUsandoOitentaPorcentoDoLimite()) {
			BigDecimal novoLimite = conta.getLimite().multiply(new BigDecimal("1.25"));
			conta.setLimite(novoLimite);
			
			System.out.printf("[NOTIFICAÇÃO] A conta %d teve seu limite aumentado para %s reais\n",
					conta.getNumero(),
					novoLimite);
		}
	}
}
