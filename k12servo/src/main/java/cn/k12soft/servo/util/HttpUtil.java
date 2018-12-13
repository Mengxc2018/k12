package cn.k12soft.servo.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static final String RESPONSE_CODE = "responseCode";
    public static final String RESPONSE_CONTENT = "responseContent";

    public static JSONObject getRequest4Json(String url, Map<String, Object> params){
        String res = getRequest(url, params);
        return JSONObject.fromObject(res);
    }

    public static String getRequest(String url, Map<String, Object> params){
        Options options = Options.build();
        Params p = new Params();
        if(params != null && params.size()>0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                p.addParam(entry.getKey(), entry.getValue().toString());
            }
        }
        String sendUrl = buildUrl(url,p);
        URL serverUrl = null;
        try {
            serverUrl = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(options.connectTimeout);
            conn.setReadTimeout(options.readTimeout);
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            options.setPropertiesInConnect(conn);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                String res = toString(conn.getInputStream(), options.encoding);
                return res;
            } else {
                String res = toString(conn.getInputStream(), options.encoding);
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildUrl(String url,Params p){
        if(p == null || p.isEmpty()){
            return url;
        }
        if(url.contains("?")){
            return url+"&"+p.httpBuildQuery();
        }else{
            return url+"?"+p.httpBuildQuery();
        }
    }

    private static String toString(InputStream is, String encoding) throws IOException {
        InputStreamReader in = new InputStreamReader(is, encoding);
        StringWriter sw = new StringWriter();
        char[] b = new char[1024 * 4];
        int n = 0;
        while (-1 != (n = in.read(b))) {
            sw.write(b, 0, n);
        }
        in.close();
        sw.close();
        return sw.toString();
    }

    public static String urlEncode(String str){
        try{
            return URLEncoder.encode(str,"UTF-8");
        }catch (Exception e){
            return str;
        }
    }

    public static class Options{
        protected int connectTimeout = 3000;
        protected int readTimeout = 10000;
        protected String encoding = "UTF-8";
        private Map<String,String> properties;
        private List<Header> headers;
        private Options(){
            properties = new HashMap<String, String>();
            headers = new ArrayList<Header>();
        }
        public static Options build(){
            return new Options();
        }
        public Options setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }
        public Options setEncoding(String encoding) {
            this.encoding = encoding;
            return this;
        }
        public Options setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Options setContentType(String contentType){
            Header contentTypeHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,contentType);
            headers.add(contentTypeHeader);
            return this;
        }

        public Options setAuthorization(String authorization){
            Header authorizationHeader = new BasicHeader(HttpHeaders.AUTHORIZATION,authorization);
            headers.add(authorizationHeader);
            return this;
        }
        public Options addProperty(String key,String val){
            this.properties.put(key,val);
            return this;
        }

        protected void setPropertiesInConnect(HttpURLConnection conn){
            for(String k:this.properties.keySet()){
                conn.setRequestProperty(k,this.properties.get(k));
            }
        }

        protected RequestConfig getRequestConfig(){
            RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build();
            return config;
        }

        protected Header[] getHeader(){
            return headers.isEmpty() ? null : headers.toArray(new Header[headers.size()]);
        }
    }

    public static class Params{

        Map<String,String> p = null;

        private Params(){
            p = new HashMap<String, String>();
        }

        public Params addParam(String key,String val){
            if(StringUtils.isBlank(key)){
                return this;
            }
            if(StringUtils.isBlank(val)){
                val = "";
            }
            this.p.put(key,val);
            return this;
        }

        public String httpBuildQuery() {
            List<String> querys = new ArrayList<String>();
            for(String key : this.p.keySet()){
                querys.add(key+"="+HttpUtil.urlEncode(this.p.get(key)));
            }
            return StringUtils.join(querys,"&");
        }

        protected boolean isEmpty(){
            return this.p.isEmpty();
        }

        public static Params build(){
            return new Params();
        }

        protected HttpEntity getUrlEncodeEntry(){
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String k : p.keySet()){
                String v = p.get(k);
                nameValuePairs.add(new BasicNameValuePair(k,v));
            }
            try {
                return new UrlEncodedFormEntity(nameValuePairs);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public static JSONObject doPost(String url, Map<String, Object> httpParams){
        return doPost(url, httpParams, false, null);
    }

    public static JSONObject doPost(String url, Map<String, Object> httpParams, boolean setEntity, String contentType){
        int responseCode = 0;
        String httpParam = "";
        HttpURLConnection connection = null;
        String ret = "";
        try {
            URL urlObj = new URL(url);
            // 打开和URL之间的连接
            connection = (HttpURLConnection)urlObj.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            if(StringUtils.isNotBlank(contentType))
                connection.setRequestProperty("Content-type", contentType);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            if(setEntity)
                httpParam = mapTooJsonStr(httpParams);
            else
                httpParam = buildParam(httpParams);
            out.print(httpParam);
            // flush输出流的缓冲
            out.flush();
            out.close();
            responseCode = connection.getResponseCode();
            switch (responseCode)
            {
                case 200:
                    BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer buffer = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        buffer.append(inputLine);
                    }
                    in.close();
                    ret = buffer.toString();
                    break;
            }
        } catch (Exception e){
            System.out.println("dopost error, url " + url + " httpParam:" + httpParam);
            e.printStackTrace();
        } finally {
            if(connection != null)
                connection.disconnect();
        }
//        Map<String, Object> retMap = new HashMap<>();
//        retMap.put(RESPONSE_CODE, responseCode);
//        retMap.put(RESPONSE_CONTENT, ret);
        return JSONObject.fromObject(ret);
    }

    private static String buildParam(Map<String, Object> httpParam){
        List<String> httpParamList = new ArrayList<>();
        try {
            for(String key:httpParam.keySet()) {
                httpParamList.add(key + "=" + URLEncoder.encode(String.valueOf(httpParam.get(key)), "utf-8"));
            }
            return StringUtils.join(httpParamList, "&");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String mapTooJsonStr(Map<String, Object> map){
        return JSONObject.fromObject(map).toString();
    }

}
