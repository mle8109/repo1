package com.project.nra.news_lib.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp.luong on 7/7/2016.
 */
public class Test {
    public static void main(String args[]) {
        System.out.println("Testing...");
        ArrayList<String> list = getListNews();
        System.out.println("Testing...list = " + list.size());

        /*String apiKey = "de2f02ca31e54052bf41b1ae7daceafa";
        String source = "techcrunch";
        String sourceQuery = "https://newsapi.org/v1/sources";
        String status = null;
        String query = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=3e22f2fcc1344975ae2b2e69379e2a6e";
        try {
            String sourceResult = JsonUtils.getJsonResult(sourceQuery);
            List<String> sourceList = new ArrayList<String>();
            JSONObject sourceObject = new JSONObject(sourceResult.trim());
            JSONArray sourceArray = sourceObject.getJSONArray("sources");
            for(int i = 0 ; i < sourceArray.length() ; i++){
                sourceList.add(sourceArray.getJSONObject(i).getString("id"));
                System.out.println("Source : " + sourceArray.getJSONObject(i).getString("id"));
                source = sourceArray.getJSONObject(i).getString("id");

                query = "https://newsapi.org/v1/articles?source=" + source + "&apiKey=" + apiKey;

                String result = JsonUtils.getJsonResult(query);
                System.out.println("result = '" + result + "'");
                if (!result.equals("")) {
                    JSONObject jObject = new JSONObject(result.trim());

                    List<String> list = new ArrayList<String>();
                    status = jObject.getString("status");
                    if (status.equals("ok")) {
                        JSONArray array = jObject.getJSONArray("articles");
                        for (int j = 0; j < array.length(); j++) {
                            list.add(array.getJSONObject(j).getString("title"));
                            System.out.println("\t News " + j + ": " + array.getJSONObject(j).getString("title"));
                        }
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    public static ArrayList<String> getListNews() {
        ArrayList<String> listNews = new ArrayList<String>();
        System.out.println("Testing...");

        try {  String apiKey = "de2f02ca31e54052bf41b1ae7daceafa";
            String source = "techcrunch";
            String sourceQuery = "https://newsapi.org/v1/sources";
            String status = null;
            String query = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=3e22f2fcc1344975ae2b2e69379e2a6e";
            String sourceResult = JsonUtils.getJsonResult(sourceQuery);
            if (!sourceResult.equals("")) {
                List<String> sourceList = new ArrayList<String>();
                JSONObject sourceObject = new JSONObject(sourceResult.trim());
                JSONArray sourceArray = sourceObject.getJSONArray("sources");
                for (int i = 0; i < sourceArray.length(); i++) {
                    sourceList.add(sourceArray.getJSONObject(i).getString("id"));
                    System.out.println("Source : " + sourceArray.getJSONObject(i).getString("id"));
                    source = sourceArray.getJSONObject(i).getString("id");

                    query = "https://newsapi.org/v1/articles?source=" + source + "&apiKey=" + apiKey;

                    String result = JsonUtils.getJsonResult(query);
                    System.out.println("result = '" + result + "'");
                    try {
                        if (!result.equals("")) {
                            JSONObject jObject = new JSONObject(result.trim());

                            //listNews = new ArrayList<String>();
                            status = jObject.getString("status");
                            if (status.equals("ok")) {
                                JSONArray array = jObject.getJSONArray("articles");
                                for (int j = 0; j < array.length(); j++) {
                                    listNews.add(array.getJSONObject(j).getString("title"));
                                    System.out.println("\t News " + j + ": " + array.getJSONObject(j).getString("title"));
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Exception = " + e.toString());
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException = " + e.toString());
        } catch (JSONException e) {
            System.out.println("JSONException = " + e.toString());
        }
        System.out.println("listNews size = '" + listNews.size() + "'");
        return listNews;
    }
}
