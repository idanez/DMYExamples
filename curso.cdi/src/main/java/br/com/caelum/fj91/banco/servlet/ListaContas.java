package br.com.caelum.fj91.banco.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.fj91.banco.modelo.Conta;
import br.com.caelum.fj91.banco.persistencia.ContaDao;

/**
 * Servlet implementation class ListaContas
 */
public class ListaContas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private ContaDao dao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramAno = request.getParameter("ano");
		Integer ano;
		if (paramAno == null)
			ano = Calendar.getInstance().get(Calendar.YEAR);
		else
			ano = Integer.valueOf(paramAno);
		
		Iterable<Conta> iterable = dao.listaContasAbertasEm(ano);
		request.setAttribute("iterable", iterable);

		request.getRequestDispatcher("/WEB-INF/jsp/conta/lista.jsp").forward(request, response);
	}
}
