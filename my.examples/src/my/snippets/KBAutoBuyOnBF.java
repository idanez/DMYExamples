package my.snippets;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class KBAutoBuyOnBF {

	public static void main(final String[] args) throws Exception {
		KBAutoBuyOnBF kabumBlackFriday = new KBAutoBuyOnBF();
		kabumBlackFriday.run();
	}

	private void run() throws IOException {
		// URL oracle = new
		// URL("http://www.kabum.com.br/cgi-local/site/produtos/descricao_blackfriday.cgi?codigo=78761");
		String urlToBuy = "";
		int tries = 0;
		int maxTries = 5000;
		while (urlToBuy.isEmpty() && tries < maxTries) {
			tries += 1;
			System.out.println("TENTATIVA: " + tries);
			String urlBase = "http://www.kabum.com.br/cgi-local/site/produtos/descricao_blackfriday.cgi?codigo=";
			String carregador = "65265";
			String monitor = "78761";
			String codigo = monitor; // <- Trocar pela string do codigo desejado
			URL url = new URL(urlBase + codigo);
			urlToBuy = readSite(url, tries, maxTries);
			if (urlToBuy.equals("ESGOTOU")) {
				System.out.println("ESGOTOU");
				break;
			}
			if (!urlToBuy.isEmpty()) {
				openUrlInBrowser(urlToBuy);
				break;
			}
		}
	}

	private String readSite(final URL url, int tries, final int maxTries) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String urlToBuy = "";
		String inputLine;
		boolean boxBuyFound = false;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("<div class=\"box_comprar-cm\">") || boxBuyFound) {
				boxBuyFound = true;
				urlToBuy = getUrlToBuy(inputLine);
				if (!urlToBuy.isEmpty()) {
					break;
				}
				if (inputLine.contains("ESGOTADO")) {
					tries = maxTries;
					urlToBuy = "ESGOTOU";
					break;
				}
				if (inputLine.contains("<div class=\"quantidades-cm\">")) {
					break;
				}
			} else if (inputLine.contains("<div class=\"quantidades-cm\">")) {
				break;
			}
		}
		in.close();
		return urlToBuy;
	}

	private void openUrlInBrowser(final String urlToBuy) {
		try {
			System.out.println("URL ENCONTRADA:");
			System.out.println(urlToBuy);
			System.out.println("ABRINDO BROWSER");
			Desktop.getDesktop().browse(new URL(urlToBuy).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getUrlToBuy(final String inputLine) {
		String url = "";
		if (inputLine.contains("<a onClick=\"window.l") && inputLine.contains("href=\"http://www.kabum.com.br")) {
			int beginIndex = inputLine.indexOf("http://www.kabum.com.br");
			int endIndex = inputLine.indexOf("\" ><div class");
			url = inputLine.substring(beginIndex, endIndex).trim();
		}
		return url;
	}
}
