package com.metanonia.xmlsample.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

public class RestService {
    private static Logger log = Logger.getLogger(RestService.class.getName());

    public static String Rest(String method, String path, String body) {
        String rBody = "";

        try {
            String Sign = null;
            String timeStamp = String.valueOf(new Date().getTime());
            String preSign = timeStamp + method.toUpperCase() + path;
            if(body != null) preSign = preSign + body;

            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(60000); //60 secs
            connection.setReadTimeout(60000); //60 secs
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            if(body != null) {
                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                byte[] wBuf = body.getBytes("UTF-8");
                os.write(wBuf, 0, wBuf.length);
                os.flush();
                os.close();
            }
            Integer code = connection.getResponseCode();
            connection.getResponseMessage();

            if(code==200 ) {
                BufferedReader rd;
                try {
                    rd = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    StringBuffer strbuf = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        strbuf.append(line);
                    }
                    rBody = strbuf.toString();
                } catch (Exception e1) {
                    log.info(e1.toString());
                    rd = null;
                }
            }
            else if(code==400 ) {
                BufferedReader rd;
                try {
                    rd = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream()));
                    StringBuffer strbuf = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        strbuf.append(line);
                    }
                    rBody = strbuf.toString();
                } catch (UnsupportedOperationException e1) {
                    log.info(e1.toString());
                    rd = null;
                } catch (IOException e1) {
                    log.info(e1.toString());
                    rd = null;
                }
            }
            else {
                BufferedReader rd;
                try {
                    rd = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream()));
                    StringBuffer strbuf = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        strbuf.append(line);
                    }
                    rBody = strbuf.toString();
                } catch (UnsupportedOperationException e1) {
                    log.info(e1.toString());
                    rd = null;
                } catch (IOException e1) {
                    log.info(e1.toString());
                    rd = null;
                }
            }

            connection.disconnect();
        }
        catch(Exception e) {
            log.info(e.toString());
        }
        return rBody;
    }
}