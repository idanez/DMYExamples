package br.com.caelum.hibernate.controller;

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.hibernate.modelo.Transacao;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
@Path("/")
public class RegistrosDe2011 {
	private final Session session;
	private final Result result;

	public RegistrosDe2011(Session session, Result result) {
		this.session = session;
		this.result = result;
	}
	
	@Path("/registrosDe2011")
	public void mostrar(){
		long antes = System.currentTimeMillis();
		Criteria criteria = session.createCriteria(Transacao.class);
//		criteria.setCacheable(true);
		criteria.add(Restrictions.ge("data", new GregorianCalendar(2011, 0, 1)));
		List<Transacao> transacoes = criteria.list();
		long tempoGasto = System.currentTimeMillis()-antes;
		System.out.printf("tempo total gasto %dms%n", tempoGasto);
		result.include("tempoGasto", tempoGasto);
	}
}
