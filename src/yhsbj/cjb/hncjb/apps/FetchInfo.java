package yhsbj.cjb.hncjb.apps;

import yhsbj.cjb.hncjb.Session;
import yhsbj.cjb.hncjb.transaction.Grinfo;
import yhsbj.cjb.hncjb.transaction.GrinfoQuery;
import yhsbj.util.Excels;
import yhsbj.util.Paths;

public class FetchInfo {

	public static void main0(String[] args) {
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

	public static void main(String[] args) {
		var fileName = "D:\\待遇认证\\2018年\\乡镇街上报认证汇总表\\汇总表.xls";
		try (var wb = Excels.load(fileName)) {
			Session.user002(session -> {
				var sheet = wb.getSheetAt(0);
				var last = sheet.getLastRowNum();
				for (var i = 4; i <= last; i++) {
					var row = sheet.getRow(i);
					var idcard = row.getCell(5).getStringCellValue();
					session.send(new GrinfoQuery(idcard));
					var result = session.getResult(Grinfo.class);
					var state = "未参加居保";
					if (result.getDatas().size() > 0) {
						state = result.getDatas().get(0).getJbztCN();
					}
					var cell = row.getCell(9);
					if (cell == null)
						cell = row.createCell(9);
					cell.setCellValue(state);
					System.out.format("%d of %d : %s %s\n", i, last, idcard, state);
				}
			});
			Excels.save(wb, Paths.AppendToFileName(fileName, ".new"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
