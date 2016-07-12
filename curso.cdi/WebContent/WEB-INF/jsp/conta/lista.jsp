<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<h1>Lista contas</h1>
	
	<form>
		<select name="ano">
			<option value="2011">2011</option>
			<option value="2010">2010</option>
			<option value="2009">2009</option>
			<option value="2008">2008</option>
		</select>
		<input type="submit" value="Ver ano">
	</form>
	
	<table border="1">
		<thead>
			<tr>
				<th>Número</th>
				<th>Data de Abertura</th>
				<th>Saldo atual</th>
				<th>Limite</th>
				<th>Sacar:</th>
				<th>Depositar:</th>
				<th>Tributação</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${iterable}" var="conta">
				<tr>
					<td>${conta.numero}</td>
					<td><fmt:formatDate value="${conta.dataAbertura.time}" pattern="dd/MM/yyyy"/></td>
					<td>${conta.saldo} reais</td>
					<td>${conta.limite} reais</td>
					<td>
						<form action="saca" method="post">
							<input type="hidden" name="numero" value="${conta.numero}">
							<input type="text" name="valor" size="8">
						</form>
					</td>
					<td>
						<form action="deposita" method="post">
							<input type="hidden" name="numero" value="${conta.numero}">
							<input type="text" name="valor" size="8">
						</form>
					</td>
					<td><a href="tributa?numero=${conta.numero}">Tributar essa conta</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</html>