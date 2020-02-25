package com.roj.eztalk.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Http {
    // for sending http requests
    public static String get(String base, Map<String, String> params) throws Exception {
        String urlStr = params.isEmpty() ? base : base + "?" + ParameterStringBuilder.getParameterString(params);
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("connection", "Keep-Alive");
        con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        con.connect();

        // Map<String, List<String>> map = con.getHeaderFields();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    public static String post(String urlString, String jsonString) throws Exception {
        byte[] out = jsonString.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http  = (HttpURLConnection) con;
        http.setRequestMethod("POST");
        http.setRequestProperty("accept", "*/*");
        http.setRequestProperty("connection", "Keep-Alive");
        http.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.setDoOutput(true);

        try(OutputStream os = http.getOutputStream()){
            os.write(out);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        http.disconnect();
        return content.toString();
    }
}