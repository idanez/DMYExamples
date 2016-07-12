package br.com.caelum.fj91.banco.servlet;

import java.io.IOException;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.fj91.banco.modelo.Conta;
import br.com.caelum.fj91.banco.persistencia.ContaDao;
import br.com.caelum.fj91.banco.tributacao.Tributo;

public class AplicaTributos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject
	private ContaDao dao;

	@Inject @Any
	private Instance<Tributo> tributos;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int numero = Integer.parseInt(request.getParameter("numero"));
		
		Conta conta = dao.contaComNumero(numero);
		for (Tributo tributo : this.tributos) {
			System.out.println("Aplicando tributo: " + tributo.getClass().getSimpleName());
			conta.aplica(tributo);
		}
		
		response.sendRedirect("lista");
	}

}
