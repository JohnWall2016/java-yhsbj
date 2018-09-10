package yhsbj.cjb.hncjb.service;

import java.util.HashMap;
import java.util.List;

public class PageService extends CustomService {
    int page, pagesize;
    List<Object> filtering = List.of();
    List<Object> sorting = List.of();
    List<Object> totals = List.of();

    public void setSorting(HashMap<String, String> sorting) {
        this.sorting = List.of(sorting);
    }

    public void setTotals(HashMap<String, String> totals) {
        this.totals = List.of(totals);
    }

    public PageService(String id, int page, int size) {
        super(id);
        this.page = page;
        this.pagesize = size;
    }

    public PageService(String id) {
        this(id, 1, 15);
    }
}
