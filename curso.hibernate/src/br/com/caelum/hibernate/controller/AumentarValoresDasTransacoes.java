package br.com.caelum.hibernate.controller;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import br.com.caelum.hibernate.modelo.Transacao;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
@Path("/aumentarValorTransacoes")
public class AumentarValoresDasTransacoes {
	
	private static final int QTD_REGISTROS = 200000;
	private final Session session;
	private final Result result;
	private final SessionFactory factory;

	public AumentarValoresDasTransacoes(SessionFactory factory, Session session, Result result) {
		this.factory = factory;
		this.session = session;
		this.result = result;
	}
	
	@Path("/batch")
	public void aumentarBatchUpdate(){
		long antes = System.currentTimeMillis();
		session.createQuery("update Transacao t set t.valor = t.valor*1.1").executeUpdate();
		long tempo = System.currentTimeMillis()-antes;
		System.out.printf("Tempo gasto %dms%n", tempo);
		result.include("mensagem", "Atualizacao realizada com sucesso (" + tempo + "ms)");
		result.forwardTo(IndexController.class).index();
	}
	
	@Path("/stateless")
	public void aumentarStatelessSession(){
		long antes = System.currentTimeMillis();
		StatelessSession statelessSession = factory.openStatelessSession();
		ScrollableResults results = statelessSession.createCriteria(Transacao.class).setFetchSize(QTD_REGISTROS).scroll();

		Transaction transaction = statelessSession.beginTransaction();
		
		while(results.next()) {
			Transacao transacao = (Transacao) results.get()[0];
			transacao.setValor(transacao.getValor().multiply(new BigDecimal("1.1")));
			statelessSession.update(transacao);
		}
		transaction.commit();
		
		long tempo = System.currentTimeMillis()-antes;
		System.out.printf("Tempo gasto %dms%n", tempo);
		result.include("mensagem", "Atualizacao realizada com sucesso (" + tempo + "ms)");
		result.forwardTo(IndexController.class).index();
	}
	
	@Path("/puro")
	public void aumentarStatefullSession(){
		long antes = System.currentTimeMillis();
		List<Transacao> list = session.createCriteria(Transacao.class).list();
		Transaction transaction = session.beginTransaction();
		for (Transacao transacao : list) {
			transacao.setValor(transacao.getValor().multiply(new BigDecimal("1.1")));
		}
		transaction.commit();
		long tempo = System.currentTimeMillis()-antes;
		System.out.printf("Tempo gasto %dms%n", tempo);
		result.include("mensagem", "Atualizacao realizada com sucesso (" + tempo + "ms)");
		result.forwardTo(IndexController.class).index();
	}

}
