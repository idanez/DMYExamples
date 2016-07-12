package br.com.caelum.fj91.banco.persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import br.com.caelum.fj91.banco.modelo.Cliente;
import br.com.caelum.fj91.banco.modelo.Conta;
import br.com.caelum.fj91.banco.modelo.ContaBuilder;

@ApplicationScoped
public class BancoDeDados {
	// guarda objetos de determinada classe
	private Map<Class<?>, List<Object>> contexto = new HashMap<Class<?>, List<Object>>();

	// inicializa mapas de persistencia
	public BancoDeDados() {
		contexto.put(Conta.class, new ArrayList<Object>());
		contexto.put(Cliente.class, new ArrayList<Object>());
	}

	// insere alguns dados iniciais
	@PostConstruct
	public void popula() {
		contexto.get(Conta.class).add(
				ContaBuilder.novaConta().comNumero(123)
						.comDataDeAbertura(2011, 10, 20).comLimite("1000")
						.eDepositoInicial("250").toConta());

		contexto.get(Conta.class).add(
				ContaBuilder.novaConta().comNumero(456)
						.comDataDeAbertura(2011, 7, 12).comLimite("500")
						.eDepositoInicial("510").toConta());

		contexto.get(Conta.class).add(
				ContaBuilder.novaConta().comNumero(789)
						.comDataDeAbertura(2011, 5, 18).comLimite("300")
						.eDepositoInicial("200").toConta());

		contexto.get(Conta.class).add(
				ContaBuilder.novaConta().comNumero(234)
						.comDataDeAbertura(2011, 9, 14).comLimite("300")
						.eDepositoInicial("300").toConta());
	}
	
	public List<Object> tabela(Class<?> classe) {
		return contexto.get(classe);
	}
}
