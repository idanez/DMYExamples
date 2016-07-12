package br.com.caelum.hibernate.linhaDeComando;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

import br.com.caelum.hibernate.modelo.Tipo;
import br.com.caelum.hibernate.modelo.Transacao;

public class GeraArquivoCom10MDeRegistros {
	public static void main(String[] args) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("arquivoGigante")));
		Random random = new Random();
		for (int i = 0; i < 10000000; i++) {
			Transacao transacao = new Transacao();
			transacao.setTipoDeTransacao(Tipo.values()[random.nextInt(2)]);
			transacao.setData(new GregorianCalendar(random.nextInt(6)+2006, random.nextInt(12), random.nextInt(29)));
			transacao.setValor(new BigDecimal(random.nextDouble()*10000));
			writer.append(transacao.toFile(new SimpleDateFormat("dd/MM/yyyy")));
			writer.append("\n");
//			System.out.println(i);
		}
	}

}
