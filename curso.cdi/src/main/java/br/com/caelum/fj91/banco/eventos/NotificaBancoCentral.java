package br.com.caelum.fj91.banco.eventos;

import java.math.BigDecimal;

import javax.enterprise.event.Observes;

/**
 * Deve notificar o banco central em saques muito altos 
 */
public class NotificaBancoCentral {

	private final BigDecimal MAXIMO = new BigDecimal("1000");
	
	public void observaSaquesAltos(@Observes Saque saque) {
		if (saque.getValorSacado().compareTo(MAXIMO) >= 0) {
			System.out.printf("[NOTIFICAÇÃO] A conta %d teve um saque alto, de %s reais\n",
					saque.getConta().getNumero(),
					saque.getValorSacado());
		}
	}
	
}
