package yhsbj.cjb.hncjb.service;

import java.util.List;
import java.util.Map;
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

    public JsonService(String serviceId, T params) {
        Objects.requireNonNull(params);
        this.serviceid = serviceId;
        this.params = params;
        this.datas = List.of(params);
    }

    @SuppressWarnings("unchecked")
    public static <T> JsonService<T> withoutParams(String serviceId) {
        return (JsonService<T>) new JsonService<Map<Object, Object>>(serviceId, Map.of());
    }

    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}
