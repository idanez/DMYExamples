package br.com.caelum.hibernate.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Conta {
	
	@Id  @GeneratedValue
	private Integer id;
	private String numero;
	private String agencia;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Transacao> transacoes;
	
	public Conta(String numero, String agencia) {
		this.numero = numero;
		this.agencia = agencia;
	}
	
	private Conta(){}
	
	
	public Transacao novaTranscacao(BigDecimal valor, Tipo tipo, Calendar data) {
	
		if(this.transacoes == null) {
			this.transacoes = new ArrayList<Transacao>();
		}
		
		Transacao transacao = new Transacao(valor, data, tipo);
		this.transacoes.add(transacao);
		return transacao;
	}


	public Integer getId() {
		return id;
	}


	public String getNumero() {
		return numero;
	}


	public String getAgencia() {
		return agencia;
	}


	public List<Transacao> getTransacoes() {
		return Collections.unmodifiableList(this.transacoes);
	}
	
	

}
