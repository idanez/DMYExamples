<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<h1>OpenSessionInView ou Queries planejadas</h1>
	<br />
	<br />
	<div><strong>Popular o banco</strong></div> 
	<ul>
		<li><a href="/hibernate/contas/gerar">Gerar contas e algumas transacoes</a></li>
	</ul>
	<br />
	<div><strong>Mostrar selects n+1</strong></div> 
	<ul>
		<li><a href="/hibernate/contas/listar">Lista todas as contas</a>
		
		<br/><br/>
			Apos listar as contas, verifique a console no Eclipse. Em seguida abre a classe ContasController, procure o metodo listar().
			<br/><br/>
			Nao use OpenSessionInView e planeja a query:
			<br/>
			<code><br/>
			Session sessao = factory.openSession();<br/>
			List<Conta> contas = sessao.createQuery("from Conta c left join fetch c.transacoes").list();<br/>
			result.include("contas", contas);<br/>
			sessao.close();<br/>
			</code>
		</li>
	</ul>
</body>
</html>