package br.com.caelum.fj91.banco.persistencia;

import javax.inject.Inject;

public class Dao<T> {

	@Inject
	private Sessao sessao;
	
	public void adiciona(T t) {
		sessao.salva(t);
	}
	
	public void remove(T t) {
		sessao.remove(t);
	}
	
	public void atualiza(T t) {
		sessao.atualiza(t);
	}
}
