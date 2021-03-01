package com.yfy.json;

import com.yfy.final_tag.stringtool.StringJudge;

import org.json.JSONException;
import org.json.JSONObject;



public class JsonParser {







	public static boolean isSuccess(String json) {
		if (StringJudge.isEmpty(json)){
			return false;
		}
		try {
			JSONObject obj = new JSONObject(json);
			String result = obj.getString("result");
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getErrorCode(String json) {
		if (StringJudge.isEmpty(json)){
			return "没有数据";
		}
		try {
			JSONObject obj = new JSONObject(json);
			return obj.getString("error_code");
		} catch (JSONException e) {
			e.printStackTrace();
			return json;
		}

	}







}
