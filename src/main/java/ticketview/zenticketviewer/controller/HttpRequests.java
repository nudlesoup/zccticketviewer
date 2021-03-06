package ticketview.zenticketviewer.controller;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;
import java.io.*;

/*This class responsible for passing HTTP request and return the request response */

import java.io.*;
import java.sql.*;
import java.util.*;



public class HttpRequests {

	//===== Variables =====
	public static String USERNAME ;
	public static String PASSWORD ;
	public static String SUBDOMAIN ;

	public static int responseCode = 0;
	
	//===== Public Methods =====	
	/**
	* This method send HTTP GET request using Zendesk API. The username, password and subdomain are hardcoded.
	* @param targetUrl - the request's URI.
	* 	 	urlParameters - the request's parameters, if any.
	* @return String - the response from the server (Json content) or relevant error message.
	 * @throws IOException 
	* @exception failed to open HttpURLConnection, common http errors.
	*/
	public String sendGet(String endpoint, String urlParameters)
	{	
		//Getting Login details
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			    USERNAME = prop.getProperty("username");
				PASSWORD = prop.getProperty("password");
				SUBDOMAIN = prop.getProperty("subdomain");

			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		}catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
	
		HttpURLConnection connection = null;
		String targetUrl = SUBDOMAIN  + endpoint;
		
		try
		{
			//Create connection
			URL object = new URL(targetUrl + urlParameters);
			connection = (HttpURLConnection) object.openConnection();
			connection.setRequestProperty("Accept", "application/json");
			//Authentication
			Authenticator.setDefault (new Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication (USERNAME, PASSWORD.toCharArray());
			    }
			});
			byte[] message = (USERNAME+ ":" + PASSWORD).getBytes("UTF-8");
			String encoding = DatatypeConverter.printBase64Binary(message);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			/*
			 * java 8:
			 * import java.util.Base64;
			 * byte[] message = "hello world".getBytes(StandardCharsets.UTF_8);
			 * String encoded = Base64.getEncoder().encodeToString(message);
			 */
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");	

			//Get response
			responseCode = connection.getResponseCode();
			//System.out.println(responseCode);
			//HTTP Request success (2xx)
			if (responseCode < 300 && responseCode > 199)
			{
				//Get response message
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				//StringBuffer response = new StringBuffer();
				StringBuilder response = new StringBuilder();
				while ((inputLine = reader.readLine()) != null)
					response.append(inputLine);
				reader.close();

				return response.toString();
			}
			//HTTP Request failure
			else
				System.out.println("Error Code : (" + responseCode + ") " + printErrorMessage(responseCode));
				return printErrorMessage(responseCode);
		}
		catch (Exception e) {
			return "HTTP ERROR: Failed to open URL connection";
		}
		finally
		{
			if (connection != null) 
			      connection.disconnect();
		}
	}
	
	/**
	* This method print common HTTP error message (400, 401, 403, 404, 500, 503, 504).
	* @param code - the response code of the HTTP request.
	* @return string - the relevant error message of the http request.
	*/
	public String printErrorMessage(int code)
	{	
		
		switch(code)
		{
			case 400:
				return "Bad Request - the HTTP request that was sent to the server has invalid syntax";
			case 401:
				return "Unauthorized - the user trying to access the resource has not been authenticated";
			case 403:
				return "Forbidden - the user made a valid request but the server is refusing to serve the request";
			case 404:
				return "Not Found - unable to locate the requested file or resource";
			case 429:
				return "Unauthorized - Username/Password in config file";
			case 500:
				return "Internal Server Error - the server cannot process the request for an unknown reason, Check subdomain?";
			case 503:
				return "Service Unavailable - the server is overloaded or under maintenance";
			case 504:
				return "Gateway Timeout -  the server is a not receiving a response from the backend servers within the allowed time period";
			default:
				return "Unknown error occured";
		}
	}
}
