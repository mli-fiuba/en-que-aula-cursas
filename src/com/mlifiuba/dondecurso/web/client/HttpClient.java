package com.mlifiuba.dondecurso.web.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

	private static final String ENCODING = "UTF-8";
	private static final int BUFFER = 1024;

	public static String get(String urlPath) {
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(urlPath);
			urlConnection = (HttpURLConnection) url.openConnection();

			return readStream(new BufferedInputStream(urlConnection.getInputStream()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return null;
	}

	public static String readStream(InputStream is) throws IOException {
		if (is == null) {
			return "";
		}
		Writer writer = new StringWriter();

		char[] buffer = new char[BUFFER];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is, ENCODING));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		return writer.toString();
	}
}
