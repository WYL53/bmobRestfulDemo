package com.wyl.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {
	
	private String applicationId;
	private String restApiKey;
	
	public HttpRequest(String applicationId,String restApiKey){
		this.applicationId = applicationId;
		this.restApiKey = restApiKey;
	}
	
	public String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            
            URLConnection connection = realUrl.openConnection();
            
            connection.setRequestProperty("X-Bmob-Application-Id", applicationId);
            connection.setRequestProperty("X-Bmob-REST-API-Key", restApiKey);
            connection.setRequestProperty("Content-Type","application/json");
           
            connection.connect();

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 get 请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
           try {
                if (in != null) {
                    in.close();
                }
          } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }
	
    public String sendPost(String url, String param) {
    	DataOutputStream  out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpsURLConnection connection = (HttpsURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-Bmob-Application-Id", applicationId);
            connection.setRequestProperty("X-Bmob-REST-API-Key", restApiKey);
            connection.setRequestProperty("Content-Type","application/json");
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new DataOutputStream (connection.getOutputStream());
            // 发送请求参数
            out.writeBytes(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
//            System.out.println("responseCode:"+connection.getResponseCode());
            if(connection.getResponseCode() == 404){
            	return "responseCode:404";
            }
            
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }    
}
