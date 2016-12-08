package com.wyl.test;

public class Main {
	public static void main(String[] args) {
		String appKey = "appKey";
		String restApiKey = "restApiKey";
		HttpRequest request = new HttpRequest(appKey, restApiKey);
		//get
		String resp = request.sendGet("https://api.bmob.cn/1/classes/test", "");
		System.out.println(resp);
		
		//post
		resp = request.sendPost("https://api.bmob.cn/1/users", "{\"username\":\"code\",\"password\":\"123\"}");
		System.out.println(resp);
	}
}
