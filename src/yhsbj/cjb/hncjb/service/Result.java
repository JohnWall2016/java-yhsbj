package yhsbj.cjb.hncjb.service;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Result<T> {
    int rowcount, page, pagesize;
    String serviceid, type, vcode, message, messagedetail;

    ArrayList<T> datas = new ArrayList<T>();

    public int getRowcount() {
        return rowcount;
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public String getServiceid() {
        return serviceid;
    }

    public String getType() {
        return type;
    }

    public String getVcode() {
        return vcode;
    }

    public String getMessage() {
        return message;
    }

    public String getMessagedetail() {
        return messagedetail;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public static <T> Result<T> fromJson(String json, Class<T> datasType) {
        var type = TypeToken.getParameterized(Result.class, datasType).getType();
        return new Gson().<Result<T>>fromJson(json, type);
    }

    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}
