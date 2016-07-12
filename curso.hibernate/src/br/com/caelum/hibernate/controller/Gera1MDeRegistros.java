package br.com.caelum.hibernate.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import br.com.caelum.hibernate.modelo.Tipo;
import br.com.caelum.hibernate.modelo.Transacao;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
@Path("/")
public class Gera1MDeRegistros {

	private final SessionFactory sessionFactory;

	public Gera1MDeRegistros(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Path("/gerarTransacoes")
	public void gerar() throws IOException {
		StatelessSession session = sessionFactory.openStatelessSession();
		Random random = new Random();
		Transaction transaction = session.beginTransaction();
		for (int i = 0; i < 1000000; i++) {
			System.out.println("Adicionando transacao " + i);
			Transacao transacao = new Transacao();
			transacao.setTipoDeTransacao(Tipo.values()[random.nextInt(2)]);
			transacao.setData(new GregorianCalendar(random.nextInt(6) + 2006,
					random.nextInt(12), random.nextInt(29)));
			transacao.setValor(new BigDecimal(random.nextDouble() * 10000));
			session.insert(transacao);
		}
		transaction.commit();
	}

}
