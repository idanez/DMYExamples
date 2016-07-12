package br.com.caelum.hibernate.controller;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.hibernate.modelo.Conta;
import br.com.caelum.hibernate.modelo.Tipo;
import br.com.caelum.hibernate.modelo.Transacao;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
@Path("/contas")
public class ContasController {

	private final Session session;
	private final Result result;
	private final SessionFactory factory;

	public ContasController(Session session, Result result, SessionFactory factory) {
		this.session = session;
		this.result = result;
		this.factory = factory;
	}
	
	@Path("/index")
	public void index() {}
	
	@SuppressWarnings("unchecked")
	@Path("/listar")
	public void listar() {
		List<Conta> contas = session.createQuery("from Conta c").list();
		result.include("contas", contas);
	}
	
	@Path("/gerar")
	public void gerarContas() {
		Random random = new Random();
		char c = 65;
		for (int i = 0; i < 25; i++) {
			String numero = "" + c  + (random.nextInt(1000));
			Conta conta = new Conta(numero, "" + random.nextInt(10000));
			System.out.println("Adicionando conta " + i);
			for(int a = 0; a < 4; a++) {
				Transacao transacao = conta.novaTranscacao(
						new BigDecimal(random.nextDouble()*10000), 
						Tipo.values()[random.nextInt(2)], 
						new GregorianCalendar(random.nextInt(6)+2006, random.nextInt(12), random.nextInt(29)));
				session.persist(transacao);
			}
			session.persist(conta);
			c++;
			
		}
		result.include("mensagem", "Contas geradas!");
		result.forwardTo(ContasController.class).index();
	}
	
}
