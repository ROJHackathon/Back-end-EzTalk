package com.roj.eztalk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod("POST");
    http.setRequestProperty("accept", "*/*");
    http.setRequestProperty("connection", "Keep-Alive");
    http.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    http.setFixedLengthStreamingMode(length);
    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    http.setDoOutput(true);

    try (OutputStream os = http.getOutputStream()) {
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

  public static String postAuthentication(String urlString, String jsonString, String username, String password) throws Exception {

    byte[] out = jsonString.getBytes(StandardCharsets.UTF_8);
    int length = out.length;

    URL url = new URL(urlString);
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod("POST");
    http.setRequestProperty("accept", "*/*");
    http.setRequestProperty("connection", "Keep-Alive");
    http.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    http.setFixedLengthStreamingMode(length);
    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    http.setDoOutput(true);
    http.setRequestProperty("Authorization", userNamePasswordBase64(username, password));

    try (OutputStream os = http.getOutputStream()) {
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
  // public static void main (String args[]){
  // /*
  // ** args[0] is the URL protected
  // ** args[1] is the username
  // ** args[2] is the password
  // */
  // try {
  // BufferedReader in = new BufferedReader(
  // new InputStreamReader
  // (openURLForInput(new URL(args[0]), args[1], args[2])));
  // String line;
  // while ((line = in.readLine()) != null) {
  // System.out.println(line);
  // }
  // }
  // catch (IOException e) {
  // e.printStackTrace();
  // }
  // }

  public static InputStream openURLForInput(URL url, String uname, String pword) throws IOException {
    URLConnection conn = url.openConnection();
    conn.setDoInput(true);
    conn.setRequestProperty("Authorization", userNamePasswordBase64(uname, pword));
    conn.connect();
    return conn.getInputStream();
  }

  public static String userNamePasswordBase64(String username, String password) {
    return "Basic " + base64Encode(username + ":" + password);
  }

  private final static char base64Array[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
      'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
      'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
      '9', '+', '/' };

  private static String base64Encode(String string) {
    String encodedString = "";
    byte bytes[] = string.getBytes();
    int i = 0;
    int pad = 0;
    while (i < bytes.length) {
      byte b1 = bytes[i++];
      byte b2;
      byte b3;
      if (i >= bytes.length) {
        b2 = 0;
        b3 = 0;
        pad = 2;
      } else {
        b2 = bytes[i++];
        if (i >= bytes.length) {
          b3 = 0;
          pad = 1;
        } else
          b3 = bytes[i++];
      }
      byte c1 = (byte) (b1 >> 2);
      byte c2 = (byte) (((b1 & 0x3) << 4) | (b2 >> 4));
      byte c3 = (byte) (((b2 & 0xf) << 2) | (b3 >> 6));
      byte c4 = (byte) (b3 & 0x3f);
      encodedString += base64Array[c1];
      encodedString += base64Array[c2];
      switch (pad) {
        case 0:
          encodedString += base64Array[c3];
          encodedString += base64Array[c4];
          break;
        case 1:
          encodedString += base64Array[c3];
          encodedString += "=";
          break;
        case 2:
          encodedString += "==";
          break;
      }
    }
    return encodedString;
  }
}