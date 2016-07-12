package br.com.caelum.hibernate.teste;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import br.com.caelum.hibernate.modelo.Tipo;
import br.com.caelum.hibernate.modelo.Transacao;

public class Main {

	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		
		Transacao transacao = new Transacao();
		transacao.setTipoDeTransacao(Tipo.SAIDA);
		transacao.setValor(new BigDecimal("10"));
		session.save(transacao);

		session.getTransaction().commit();
		session.close();
		sf.close();
	}

}
