package yhsbj.cjb.hncjb.service;

import java.util.ArrayList;

public class RowsService<T> extends CustomService {
	ArrayList<T> rows = new ArrayList<>();

	public void addRow(T row) {
		rows.add(row);
	}

	public RowsService(String id) {
		super(id);
	}
}
