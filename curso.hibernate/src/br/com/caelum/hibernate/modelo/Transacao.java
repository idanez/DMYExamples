package br.com.caelum.hibernate.modelo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transacao {

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;

	@Enumerated
	private Tipo tipoDeTransacao;

	private BigDecimal valor;

	Transacao(BigDecimal valor, Calendar data, Tipo tipo) {
		this.valor = valor;
		this.data = data;
		tipoDeTransacao = tipo;
	}

	
	public Transacao() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Tipo getTipoDeTransacao() {
		return tipoDeTransacao;
	}

	public void setTipoDeTransacao(Tipo tipoDeTransacao) {
		this.tipoDeTransacao = tipoDeTransacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	

	@Override
	public String toString() {
		return String.format(
				"%s%.2f",tipoDeTransacao.equals(Tipo.ENTRADA)? "+" : "-", this.valor);
	}

	public String toFile(SimpleDateFormat sdf) {
		return String.format("%s|%s|%.4f", sdf.format(data.getTime()), tipoDeTransacao, valor.doubleValue());
	}

}
