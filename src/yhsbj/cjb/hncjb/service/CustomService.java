package yhsbj.cjb.hncjb.service;

import com.google.gson.annotations.Expose;

public class CustomService implements IService {
	@Expose(serialize = false, deserialize = false)
	private String id;
	
	@Override
	public String Id() {
		return id;
	}

	public CustomService(String id) {
		this.id = id;
	}
}
