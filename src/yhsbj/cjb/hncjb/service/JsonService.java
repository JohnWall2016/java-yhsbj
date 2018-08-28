package yhsbj.cjb.hncjb.service;

import java.util.List;
import java.util.Objects;
import com.google.gson.GsonBuilder;

public class JsonService<T> {
	String serviceid = "";
	String target = "";
	String sessionid;
	String loginname;
	String password;
	
	T params;
	List<T> datas;
	
	public String getLoginName() {
		return loginname;
	}

	public JsonService<T> setLoginName(String loginname) {
		this.loginname = loginname;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public JsonService<T> setPassword(String password) {
		this.password = password;
		return this;
	}

	public JsonService(String serviceid, T params) {
		Objects.requireNonNull(params);
		this.serviceid = serviceid;
		this.params = params;
		this.datas = List.of(params);
	}
	
	@Override
	public String toString() {
		return new GsonBuilder().serializeNulls().create().toJson(this);
	}
}
