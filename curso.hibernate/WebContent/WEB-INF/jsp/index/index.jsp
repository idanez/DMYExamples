<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Boas e más práticas com o Hibernate</title>
</head>
<body>

	<h2>Otimações do Hibernate</h2>
	<div><strong>${mensagem}</strong></div> 
	
	<br />

	<div><strong>Popular o banco</strong></div> 
	<ul>
		<li><a href="/hibernate/gerarTransacoes">Gerar 1 milhao de registros (demora alguns minutos)</a></li>
	</ul>
	
	<div><strong>Carregar dados sem ou com cursor (executa cada opcao pelo menos 2x)</strong></div> 
	<ul>
		<li><a href="/hibernate/relatorio/hibernate-puro">Gera relatorio de 200.000 registros sem Cursor (Hibernate)</a></li>
		<li><a href="/hibernate/relatorio/hibernate-cursor">Gera relatorio de 200.000 registros com Cursor (Hibernate)</a></li>
		<li><a href="/hibernate/relatorio/jdbc">Gera relatorio de 200.000 registros com Cursor (JDBC)</a></li>
	</ul>

	<div><strong>Carregar dados sem ou com cache de segundo nivel (executa cada opcao pelo menos 3x)</strong></div> 
	<ul>
		<li><a href="/hibernate/registrosDe2011">Mostrar registros de 2011</a>
			<br/><br/>
			Para habilitar o cache, descomente as linhas:
			<br/>
			<ul>
				<li>No arquivo Transacao.java: <pre>@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)</pre></li>
				<li>No arquivo RegistrosDe2011:<pre>criteria.setCacheable(true);</pre></li>
			</ul>
		</li>
	</ul>		
		
	<div><strong>Atualizacao em lote	 ou objeto por objeto</strong></div> 
	<ul>
		<li><a href="/hibernate/aumentarValorTransacoes/batch">Aumentar 10% dos valores das transacoes (batch update)</a></li>
		<li><a href="/hibernate/aumentarValorTransacoes/stateless">Aumentar 10% dos valores das transacoes (stateless session)</a></li>
		<li><a href="/hibernate/aumentarValorTransacoes/puro">Aumentar 10% dos valores das transacoes (objeto por objeto)</a></li>
	</ul>

	<div><strong>OpenSessionInView vs Queries Planejadas</strong></div> 
	<ul>
		<li><a href="/hibernate/contas/index">Acesse as contas</a></li>
	</ul>


</body>
</html>