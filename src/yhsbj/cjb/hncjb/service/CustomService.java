package yhsbj.cjb.hncjb.service;

public class CustomService implements IService {
    private transient String id;

    @Override
    public String id() {
        return id;
    }

    public CustomService(String id) {
        this.id = id;
    }
}
