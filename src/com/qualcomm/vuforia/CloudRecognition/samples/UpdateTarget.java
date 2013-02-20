package com.qualcomm.vuforia.CloudRecognition.samples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.qualcomm.vuforia.VisualSearch.utils.SignatureBuilder;




//See the Vuforia Web Services Developer API Specification - https://developer.vuforia.com/resources/dev-guide/updating-target-cloud-database

public class UpdateTarget {

	//Server Keys
	private String accessKey = "dfc31f7aedf81798e0e01d493fa9b61806da85bf";
	private String secretKey = "dc28eb3c89dbcd2cfb6fb9550e49e6b58754bb8e";
		
	private String targetId = "5eed74235bb746d9bb9020203d378217";
	private String url = "https://vws.vuforia.com";

	private void updateTarget() throws URISyntaxException, ClientProtocolException, IOException, JSONException {
		HttpPut putRequest = new HttpPut();
		HttpClient client = new DefaultHttpClient();
		putRequest.setURI(new URI(url + "/targets/" + targetId));
		JSONObject requestBody = new JSONObject();
		
		setRequestBody(requestBody);
		putRequest.setEntity(new StringEntity(requestBody.toString()));
		setHeaders(putRequest); // Must be done after setting the body
		
		HttpResponse response = client.execute(putRequest);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	
	private void setRequestBody(JSONObject requestBody) throws IOException, JSONException {
		//requestBody.put("active_flag", true); // Optional
		//requestBody.put("application_metadata", Base64.encodeBase64String("Vuforia test metadata".getBytes())); // Optional
	}
	
	private void setHeaders(HttpUriRequest request) {
		SignatureBuilder sb = new SignatureBuilder();
		request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
		request.setHeader(new BasicHeader("Content-Type", "application/json"));
		request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
	}
	
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException, JSONException {
		UpdateTarget u = new UpdateTarget();
		u.updateTarget();
	}
}
