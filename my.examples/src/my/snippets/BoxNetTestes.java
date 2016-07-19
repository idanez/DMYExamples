package my.snippets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import cn.com.believer.songyuanframework.openapi.storage.box.BoxExternalAPI;
import cn.com.believer.songyuanframework.openapi.storage.box.constant.BoxConstant;
import cn.com.believer.songyuanframework.openapi.storage.box.factories.BoxRequestFactory;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.CreateFolderRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.CreateFolderResponse;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.DeleteRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.DownloadRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetAccountTreeRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetAuthTokenRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetAuthTokenResponse;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetTicketRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.GetTicketResponse;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.LogoutRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.PublicShareRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.RegisterNewUserRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.UploadRequest;
import cn.com.believer.songyuanframework.openapi.storage.box.functions.UploadResponse;
import cn.com.believer.songyuanframework.openapi.storage.box.impl.simple.SimpleBoxImpl;
import cn.com.believer.songyuanframework.openapi.storage.box.objects.BoxException;
import cn.com.believer.songyuanframework.openapi.storage.box.objects.UploadResult;

/**
 * @author jjia
 * 
 */
public final class BoxNetTestes {

	/**
	 * 
	 */
	private BoxNetTestes() {
	}

	/**
	 * @param args
	 *            no use
	 */
	public static void main(String[] args) {
		// the global API interface
		BoxExternalAPI iBoxExternalAPI = new SimpleBoxImpl();

		// get user's API key
		System.out.println(">>>>>>>>>>> Please enter your API key, it should be like 'e7ak8t2je0rxoq97k9sl2fh2mld1xxxx'");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String apiKey = null;
		try {
			apiKey = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// create a user for the preparation.
			String email = "test" + System.currentTimeMillis() + "@test.com";
			String password = "888888";
			RegisterNewUserRequest registerNewUserRequest = BoxRequestFactory.createRegisterNewUserRequest(apiKey,
					email, password);
			iBoxExternalAPI.registerNewUser(registerNewUserRequest);
			System.out.println(">>>>>>>>>>> A test user has been created for you");
			System.out.println(">>>>>>>>>>> The username is " + email);
			System.out.println(">>>>>>>>>>> The password is 888888");

			// get a ticket by API key.
			GetTicketRequest getTicketRequest = BoxRequestFactory.createGetTicketRequest(apiKey);
			GetTicketResponse getTicketResponse = iBoxExternalAPI.getTicket(getTicketRequest);

			// after you get the ticket, you need to navigate to the URL
			// http://www.box.net/api/1.0/auth/<ticket> to enter the user name and password to authenticate.
			System.out.println(">>>>>>>>>>> Your ticket is " + getTicketResponse.getTicket());
			System.out.println(">>>>>>>>>>> please authenticate from this URL: http://www.box.net/api/1.0/auth/" + getTicketResponse.getTicket());
			System.out.println(">>>>>>>>>>> press any key after you are authenticated from box.net page.");
			br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String temp = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			GetAuthTokenRequest getAuthTokenRequest = BoxRequestFactory.createGetAuthTokenRequest(apiKey,
					getTicketResponse.getTicket());
			GetAuthTokenResponse getAuthTokenResponse = iBoxExternalAPI.getAuthToken(getAuthTokenRequest);

			if (BoxConstant.STATUS_NOT_LOGGED_IN.equals(getAuthTokenResponse.getStatus())) {
				return;
			}
			String authToken = getAuthTokenResponse.getAuthToken();

			// create a folder
			CreateFolderRequest createFolderRequest = BoxRequestFactory.createCreateFolderRequest(apiKey, authToken,
					"0", "folderName" + System.currentTimeMillis(), false);
			CreateFolderResponse createFolderResponse = iBoxExternalAPI.createFolder(createFolderRequest);
			String createdFolderId = createFolderResponse.getFolder().getFolderId(); 

			// create a temp file for upload
			File tmpFile = File.createTempFile("This-is-a-Temp-File" + System.currentTimeMillis(), ".txt");
			tmpFile.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFile));
			out.write("this is a test file for upload" + +System.currentTimeMillis());
			out.close();

			// upload a file to the created folder.
			Map fileMap = new HashMap();
			fileMap.put(tmpFile.getName(), tmpFile);
			UploadRequest uploadRequest = BoxRequestFactory.createUploadRequest(authToken, true, createdFolderId,
					fileMap);
			UploadResponse uploadResponse = iBoxExternalAPI.upload(uploadRequest);

			UploadResult uploadResult = (UploadResult) uploadResponse.getUploadResultList().get(0);
			String uploadedFileId = uploadResult.getFile().getFileId();

			HashMap nameBytesHashMap = new HashMap();
			nameBytesHashMap.put("fileName.txt", "fileName.txt".getBytes());
			uploadRequest = BoxRequestFactory.createUploadRequest(authToken, false, createdFolderId, nameBytesHashMap);
			iBoxExternalAPI.upload(uploadRequest);

			// share this folder
			PublicShareRequest publicShareRequest = BoxRequestFactory.createPublicShareRequest(apiKey, authToken,
					"folder", "888888", createdFolderId, "this is my public folder !", null);
			iBoxExternalAPI.publicShare(publicShareRequest);

			// get account file/folder tree structure
			String[] params = { "nozip" };
			GetAccountTreeRequest getAccountTreeRequest = BoxRequestFactory.createGetAccountTreeRequest(apiKey,
					authToken, "0", params);
			iBoxExternalAPI.getAccountTree(getAccountTreeRequest);

			// download the file
			File tmpFile2 = new File("downloadedFileNo." + System.currentTimeMillis() + ".txt");
			tmpFile2.createNewFile();
			DownloadRequest downloadRequest = BoxRequestFactory.createDownloadRequest(authToken, uploadedFileId, true,
					tmpFile2);
			iBoxExternalAPI.download(downloadRequest);

			// delete this file
			DeleteRequest deleteRequest = BoxRequestFactory.createDeleteRequest(apiKey, authToken, "file",
					uploadedFileId);
			iBoxExternalAPI.delete(deleteRequest);

			// logout
			LogoutRequest logoutRequest = BoxRequestFactory.createLogoutRequest(apiKey, authToken);
			iBoxExternalAPI.logout(logoutRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BoxException e) {
			e.printStackTrace();
		}

	}
}
