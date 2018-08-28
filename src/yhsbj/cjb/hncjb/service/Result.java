package yhsbj.cjb.hncjb.service;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Result<T> {
	int rowcount, page, pagesize;
	String serviceid, type, vcode, message, messagedetail;
	
	ArrayList<T> datas;
	
	public static<T> T fromJson(String json) {
		var type = new TypeToken<T>() {}.getType();
		return new Gson().fromJson(json, type);
	}
}
