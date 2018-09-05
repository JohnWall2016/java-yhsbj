package yhsbj.cjb.hncjb.apps;

import yhsbj.cjb.hncjb.Session;
import yhsbj.cjb.hncjb.transaction.Grinfo;
import yhsbj.cjb.hncjb.transaction.GrinfoQuery;
import yhsbj.util.Excels;
import yhsbj.util.Paths;

public class FetchInfo {

	public static void main(String[] args) {
		var fileName = "D:\\Downloads\\2017年居保代缴人员名单200180904.xlsx";
		try (var wb = Excels.load(fileName)) {
			Session.user002(session -> {
				var sheet = wb.getSheetAt(0);
				var last = sheet.getLastRowNum();
				for (var i = 1; i <= last; i++) {
					var row = sheet.getRow(i);
					var idcard = row.getCell(2).getStringCellValue();
					
					var phone = "";
					session.send(new GrinfoQuery(idcard));
					var result = session.getResult(Grinfo.class);
					if (result.getDatas().size() > 0) {
						phone = result.getDatas().get(0).getPhone();
					}
					var cell = row.getCell(8);
					if (cell == null)
						cell = row.createCell(8);
					cell.setCellValue(phone);
					System.out.format("%d of %d : %s %s\n", i, last, idcard, phone);
				}
			});
			Excels.save(wb, Paths.AppendToFileName(fileName, ".new"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
