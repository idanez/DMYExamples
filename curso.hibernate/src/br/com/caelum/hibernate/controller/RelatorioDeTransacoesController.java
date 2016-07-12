package br.com.caelum.hibernate.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.hibernate.modelo.Tipo;
import br.com.caelum.hibernate.modelo.Transacao;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
@Path("/relatorio")
public class RelatorioDeTransacoesController {
	
	private static final int QTD_REGISTROS = 200000;
	private final HttpServletResponse response;
	private final Session session;
	private final Result result;
	private final SessionFactory sessionFactory;

	public RelatorioDeTransacoesController(HttpServletResponse response, Session session, Result result, SessionFactory sessionFactory) {
		this.response = response;
		this.session = session;
		this.result = result;
		this.sessionFactory = sessionFactory;
	}
	
	@Path("/hibernate-cursor")
	public void geraRelatorioHibernateCursor() throws IOException{
		long antes = System.currentTimeMillis();
		ScrollableResults results = sessionFactory.openStatelessSession().createCriteria(Transacao.class).setMaxResults(QTD_REGISTROS).scroll();
		System.out.printf("Pesquisa feita em %dms%n", System.currentTimeMillis()-antes);
		PrintWriter writer = response.getWriter();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		int i=0;
		while(results.next()){
			if(i++%20000==0){
				System.out.println(i);
			}
			Transacao t = (Transacao) results.get()[0];
			writer.write(t.toFile(dateFormat)+"\n");
		}
		writer.close();
		result.use(Results.nothing());
		System.out.println(System.currentTimeMillis()-antes+" milisegundos");
	}
	
	
	@SuppressWarnings("deprecation")
	@Path("/jdbc")
	public void geraRelatorioJdbc() throws IOException, SQLException{
		long antes = System.currentTimeMillis();
		Connection connection = session.connection();
		PreparedStatement statement = connection.prepareStatement("select * from Transacao limit 0,200000");
		ResultSet set = statement.executeQuery();
		System.out.printf("Pesquisa feita em %dms%n", System.currentTimeMillis()-antes);
		PrintWriter writer = response.getWriter();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		int i=0;
		while(set.next()){
			if(i++%20000==0){
				System.out.println(i);
			}
			Transacao t = new Transacao();
			t.setId(set.getLong(1));
			Calendar instance = Calendar.getInstance();
			instance.setTimeInMillis(set.getDate(2).getTime());
			t.setData(instance);
			t.setTipoDeTransacao(Tipo.values()[set.getInt(3)]);
			t.setValor(new BigDecimal(set.getDouble(4)));
			writer.write(t.toFile(dateFormat)+"\n");
		}
		writer.close();
		result.use(Results.nothing());
		System.out.println(System.currentTimeMillis()-antes+" milisegundos");
	}
	
	@Path("/hibernate-puro")
	public void geraRelatorioHibernatePuro() throws IOException{
		long antes = System.currentTimeMillis();
		List<Transacao> list = session.createCriteria(Transacao.class).setMaxResults(200000).list();
		System.out.printf("Pesquisa feita em %dms%n", System.currentTimeMillis()-antes);
		PrintWriter writer = response.getWriter();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		int i=0;
		for (Transacao transacao : list) {
			if(i++%20000==0){
				System.out.println(i);
			}
			writer.write(transacao.toFile(dateFormat)+"\n");
		}
		writer.close();
		result.use(Results.nothing());
		System.out.println(System.currentTimeMillis()-antes+" milisegundos");
	}

}
