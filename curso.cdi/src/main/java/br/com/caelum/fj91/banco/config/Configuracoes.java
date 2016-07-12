package br.com.caelum.fj91.banco.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Configurações globais da aplicação, lidas uma vez só no início com post construct. 
 * Depois, um método produtor que usa qualifiers para especializar o resultado produzido.
 */

@ApplicationScoped
public class Configuracoes {

	private Properties properties;

	@PostConstruct
	public void init() throws IOException {
		this.properties = new Properties();
		properties.load(Configuracoes.class.getResourceAsStream("/configuracoes.properties"));
	}
	
	@Produces @TaxaJuros
	public BigDecimal produzTaxaDeJuros() {
		return new BigDecimal(properties.getProperty("taxaJuros"));
	}
	
	@Produces @TaxaFixa
	public BigDecimal produzTaxaFixa() {
		return new BigDecimal(properties.getProperty("taxaFixa"));
	}
}
