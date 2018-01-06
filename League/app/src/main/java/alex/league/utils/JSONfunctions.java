package alex.league.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONfunctions {

	public static JSONArray getJSONfromURL(String url) {
		InputStream is = null;
		String result = "";
		JSONArray jArray = null;

		System.out.println("URL "+url);
		// Download JSON data from URL
		try {
			
			 
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet(url);
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			System.out.println("RETORNO "+is);

		} catch (Exception e)
		{
			System.out.println("EXCEPTION "+e.getMessage());
		}

		// Convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
		}

		try {

			System.out.println("result "+result);
			jArray = new JSONArray(result);
		} catch (JSONException e) {
			 System.out.println("error "+e.toString());
		}

		System.out.println("jArray "+jArray);
		return jArray;
	}
}

