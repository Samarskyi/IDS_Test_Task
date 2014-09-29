package test.ids.sgv.ids_testtask.model;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import test.ids.sgv.ids_testtask.R;

/**
 * Created by sgv on 21.09.14.
 */
public class SearchEngine {

    String query = null;
    long start = 0;
    long num = 0;
    Customsearch.Cse.List list = null;
    Context context;

    public SearchEngine(String q, Context context) {
        this.context = context;
        query = q;
        start = Long.valueOf(1);
        num = Long.valueOf(10);
        Customsearch customsearch = new Customsearch(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        });

        try {
            list = customsearch.cse().list(query);
        } catch (IOException e) {
            Log.e(SearchEngine.class.getSimpleName(), e.toString());
        }

        list.setKey(context.getResources().getString(R.string.key));
        list.setCx(context.getResources().getString(R.string.cx));
        list.setStart(start);
        list.setFileType(context.getResources().getString(R.string.type));
        list.setGooglehost(context.getResources().getString(R.string.host));
        list.setNum(num);
        Log.d(SearchEngine.class.getSimpleName(), "SEARCH ENGINE IS CREATED");
    }

    public synchronized List<ResultWrapper> getResult() {

        List<ResultWrapper> resultWrappers = null;
        list.setStart(start);
        try {
            Search results = list.execute();
            List<Result> items = results.getItems();
            resultWrappers = getResultList(items);
//            for (ResultWrapper resultWrapper : resultWrappers) {
//                Log.d(SearchEngine.class.getSimpleName(),resultWrapper.toString());
//            }
            start += num;

        } catch (IOException e) {
            Log.e(SearchEngine.class.getSimpleName(), e.toString());
        }

        return resultWrappers;
    }

    private ResultWrapper getWrapper(Result result) {
        JSONObject object = null;
        ResultWrapper resultWrapper = null;
        try {
            object = new JSONObject(result.toString());
            JSONObject object2 = object.getJSONObject("pagemap");
            org.json.JSONArray jsonArray = object2.getJSONArray("cse_image");
            JSONObject object3 = jsonArray.getJSONObject(0);
            String url = object3.get("src").toString();
            String title = object.getString("title");
            resultWrapper = new ResultWrapper(url, title);
        } catch (JSONException e) {
            Log.e(SearchEngine.class.getSimpleName(), e.toString());
            Log.e(SearchEngine.class.getSimpleName(), e.getMessage());
            return null;
        }
        return resultWrapper;
    }

    private List<ResultWrapper> getResultList(List<Result> items) {

        ArrayList<ResultWrapper> resultWrappers = new ArrayList<ResultWrapper>();
        for (Result result : items) {
            ResultWrapper resultWrapper = getWrapper(result);
            if (resultWrapper != null) {
                resultWrappers.add(resultWrapper);
                Log.i(SearchEngine.class.getSimpleName(), resultWrapper.toString());
            }
        }
        return resultWrappers;
    }

//    private  JSONObject getResultObjectAsJson(Result result) {
//        JSONObject object = new JSONObject(result.toString());
//        JSONObject object2 = object.getJSONObject("pagemap");
//        org.json.JSONArray jsonArray = object2.getJSONArray("cse_image");
//        JSONObject object3 = jsonArray.getJSONObject(0);
//        return object3;
//    }

    public void getResult2() throws IOException {
        String key = "AIzaSyAamYR0fn4fU90UYoyO5r6epPTkkKg55Xw";
        String qry = "house";
        String cx = "006550512854887181422:hhghfbg29ie";
        URL url = new URL(
                "https://www.googleapis.com/customsearch/v1?" +
                        "key=" + key +
                        "&cx=" + cx +
                        "&q=" + qry +
                        "&fileType=jpg" +
                        "&num=10" +
                        "&start=1" +
                        "&alt=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {

//            if(output.contains("\"link\": \"")){
//                String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
            System.out.println(output);       //Will print the google search links
//            }
        }
        conn.disconnect();
    }
}
