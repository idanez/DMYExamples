package br.com.caelum.fj91.banco.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.fj91.banco.modelo.Conta;
import br.com.caelum.fj91.banco.persistencia.ContaDao;

public class Depositar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject
	private ContaDao dao;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int numero = Integer.parseInt(request.getParameter("numero"));
		String valor = request.getParameter("valor");
		
		Conta conta = dao.contaComNumero(numero);
		conta.deposita(new BigDecimal(valor));
		
		response.sendRedirect("lista");
	}

}
