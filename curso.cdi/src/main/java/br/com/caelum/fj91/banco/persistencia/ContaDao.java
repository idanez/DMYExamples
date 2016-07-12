package br.com.caelum.fj91.banco.persistencia;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import br.com.caelum.fj91.banco.modelo.Conta;

public class ContaDao {

	@Inject
	private Sessao sessao;
	
	public Iterable<Conta> listaContasAbertasEm(int ano) {
		System.out.println("Listando contas abertas em " + ano);
		
		ArrayList<Conta> resultado = new ArrayList<Conta>();
		for (Conta conta : sessao.lista(Conta.class)) {
			if (conta.getDataAbertura().get(Calendar.YEAR) == ano)
				resultado.add(conta);
		}
		return resultado;
	}	
	
	public Conta contaComNumero(int numero) {
		System.out.println("Procurando conta #" + numero);
		
		for (Conta conta : sessao.lista(Conta.class)) {
			if (conta.getNumero() == numero)
				return conta;
		}
		return null;
	}
}
