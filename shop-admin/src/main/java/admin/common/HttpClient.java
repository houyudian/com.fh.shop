package admin.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HttpClient {

    public static final String JSON = "json";
    public static final String FORM = "form";

    public static String get(String url, Map<String, String> params) {
        CloseableHttpClient client = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        String result = null;

        try {
            client = HttpClientBuilder.create().build();
            if (null != params && params.size() > 0) {
                ArrayList<BasicNameValuePair> pairs = new ArrayList<>();
                params.forEach((x, y) -> pairs.add(new BasicNameValuePair(x, y)));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "utf-8");
                String s = EntityUtils.toString(urlEncodedFormEntity, "utf-8");
                url += "?" + s;
            }
            httpGet = new HttpGet();
            response = client.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response, httpGet, client);
        }
        return result;

    }

    public static String delete(String url, Map<String, String> params) {
        CloseableHttpClient client = null;
        HttpDelete httpDelete = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            client = HttpClientBuilder.create().build();
            if (null!=params&&params.size()>0 ){
                ArrayList<BasicNameValuePair> pairs = new ArrayList<>();
              params.forEach((x,y)->pairs.add(new BasicNameValuePair(x,y)));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "utf-8");
                String s = EntityUtils.toString(urlEncodedFormEntity, "utf-8");
                url+="?"+s;
            }

            httpDelete = new HttpDelete(url);
            response = client.execute(httpDelete);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(response, httpDelete, client);
        }
        return result;
    }

    public static String post(String url, Map<String, String> params, String type) {
        CloseableHttpClient client = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            client = HttpClientBuilder.create().build();
            if (null!=params&&params.size()>0){
                if (type.equals(JSON)){
                    String s = JSONObject.toJSONString(params);
                    StringEntity stringEntity = new StringEntity(s, "utf-8");
                    stringEntity.setContentType("application/json;charset=utf-8");
                    httpPost.setEntity(stringEntity);

                }else if (type.equals(FORM)){
                    ArrayList<BasicNameValuePair> pairs = new ArrayList<>();
                params.forEach((x,y)->pairs.add(new BasicNameValuePair(x,y)));
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "utf-8");
                   httpPost.setEntity(urlEncodedFormEntity);
                }
            }
            httpPost = new HttpPost(url);
            response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response, httpPost, client);
        }
        return result;
    }

    public static String put(String url, Map<String, String> params) {
        CloseableHttpClient client = null;
        HttpPut httpPut = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            client = HttpClientBuilder.create().build();
            if (null!=params&&params.size()>0){

                String s = JSONObject.toJSONString(params);
                StringEntity stringEntity = new StringEntity(s, "utf-8");
                stringEntity.setContentType("application/json;charset=utf-8");
                httpPut.setEntity(stringEntity);
            }
            httpPut = new HttpPut(url);
            response = client.execute(httpPut);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response, httpPut, client);
        }
        return result;
    }

    public static void close(CloseableHttpResponse response, HttpRequestBase request, CloseableHttpClient client) {
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != request) {
            request.releaseConnection();
        }
        if (null != client) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
