package com.project.nra.news_lib.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hp.luong on 7/7/2016.
 */
public class JsonUtils {
    public static String getJsonResult(String webURL) throws MalformedURLException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;
        URL url = new URL(webURL);

        try {
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    return result.toString();
    }
}
