package br.com.caelum.fj91.banco.persistencia;

import java.util.Collections;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class Sessao {

	@Inject
	private BancoDeDados bd;
	
	public void salva(Object o) {
		System.out.println("Adicionando novo objeto " + o.getClass().getName());
		bd.tabela(o.getClass()).add(o);
	}
	
	public void atualiza(Object o) {
		// nao faz nada, ja esta na memoria
	}
	
	public void remove(Object o) {
		System.out.println("Removendo objeto " + o.getClass().getName());
		bd.tabela(o.getClass()).remove(o);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> lista(Class<T> clazz) {
		return (List<T>) Collections.unmodifiableList(bd.tabela(clazz));
	}
	
	@PreDestroy
	public void close() {
		System.out.println("Fechando sessão");
	}
}
