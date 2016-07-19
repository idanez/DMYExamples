package my.snippets;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import cn.com.believer.songyuanframework.openapi.storage.box.BoxExternalAPI;
import cn.com.believer.songyuanframework.openapi.storage.box.factories.BoxRequestFactory;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetAuthTokenRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetAuthTokenResponse;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetTicketRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetTicketResponse;
import cn.com.believer.songyuanframework.openapi.storage.box.impl.simple.SimpleBoxImpl;
import cn.com.believer.songyuanframework.openapi.storage.box.objects.BoxException;
import cn.com.believer.songyuanframework.openapi.storage.box.objects.BoxUser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BoxNetTeste2 {

	static GetMethod method;
	
	private static String userName = "danmotta2@yahoo.com.br";
	private static String userPwd = "2ibnzrg";

	private BoxNetTeste2() {
	}

	public static void main(String[] args) {
		// the global API interface
		BoxExternalAPI iBoxExternalAPI = new SimpleBoxImpl();

		String apiKey = "zjz0f2g9krjjqffa52qvprxzjo02kn7s";

		try {

			GetTicketRequest getTicketRequest = BoxRequestFactory.createGetTicketRequest(apiKey);

			GetTicketResponse getTicketResponse = iBoxExternalAPI.getTicket(getTicketRequest);

			autenticarUsuario(getTicketResponse);
	
			GetAuthTokenRequest req = BoxRequestFactory.createGetAuthTokenRequest(apiKey, getTicketResponse.getTicket());

			//Pega o token de autorizacao apos autenticacao do usuario.
			GetAuthTokenResponse response = iBoxExternalAPI.getAuthToken(req);

			BoxUser user = response.getUser();

			System.out.println("EMAIL: "+user.getEmail());
			System.out.println("LOGIN: "+user.getLogin());
			System.out.println("ESPACO TOTAL: "+user.getSpaceAmount());
			System.out.println("ESPACO USADO: "+user.getSpaceUsed());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BoxException e) {
			e.printStackTrace();
		}

	}

	private static void autenticarUsuario(GetTicketResponse getTicketResponse) {
		
		System.out.println(">>>>>>>>>>> URL: http://www.box.net/api/1.0/auth/" + getTicketResponse.getTicket());

		//Monta a URL para conectar
		String url = "http://www.box.net/api/1.0/auth/" + getTicketResponse.getTicket();
		HttpClient client = new HttpClient();
		method = new GetMethod(url);

		//Tenta conectar na URL informada
		int statusCode;
		try {
			statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
			
			 webClient.setThrowExceptionOnScriptError(false);
			    webClient.setRefreshHandler(new RefreshHandler() {
					public void handleRefresh(Page page, URL url, int arg) throws IOException {
						System.out.println("handleRefresh");
					}

			    });
			
			HtmlPage page = (HtmlPage) webClient.getPage(url);
			HtmlForm form = page.getFormByName("login_form1");

			// Enter login and passwd
			form.getInputByName("login").setValueAttribute(userName);
			form.getInputByName("password").setValueAttribute(userPwd);

			// Click "Sign In" button/link
			page = (HtmlPage) form.getElementById("login_button_credentials").click();
 

		} catch(Exception e) {
		
		}
	}

}
