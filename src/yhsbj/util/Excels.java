package yhsbj.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excels {

	public enum Type {
		XLS, XLSX, AUTO
	}

	public static Workbook load(String fileName, Type type) throws IOException {
		if (type == Type.AUTO) {
			var fn = fileName.toLowerCase();
			type = fn.endsWith(".xls") ? Type.XLS :
				fn.endsWith(".xlsx") ? Type.XLSX : Type.AUTO;
		}

		switch (type) {
		case XLS:
			return new HSSFWorkbook(Files.newInputStream(Paths.get(fileName)));
		case XLSX:
			return new XSSFWorkbook(Files.newInputStream(Paths.get(fileName)));
		case AUTO:
		}
		throw new UnsupportedOperationException("Unknown excel type");
	}
	
	public static Workbook load(String fileName) throws IOException {
		return load(fileName, Type.AUTO);
	}
	
	public static void save(Workbook wb, String fileName) throws IOException {
		try (var out = Files.newOutputStream(Paths.get(fileName))) {
			wb.write(out);
		}
	}
	
	public static Row createRow(Sheet sheet, int dstRowIdx, int srcRowIdx) {
		var dstRow = sheet.createRow(dstRowIdx);
		var srcRow = sheet.getRow(srcRowIdx);
		dstRow.setHeight(srcRow.getHeight());
		for (var idx = srcRow.getFirstCellNum(); idx < srcRow.getPhysicalNumberOfCells(); idx++) {
			var dstCell = dstRow.createCell(idx);
			var srcCell = srcRow.getCell(idx);
			dstCell.setCellType(srcCell.getCellTypeEnum());
			dstCell.setCellStyle(srcCell.getCellStyle());
			dstCell.setCellValue("");
		}
		return dstRow;
	}
	
	public static Row getOrCopyRowFrom(Sheet sheet, int dstRowIdx, int srcRowIdx) {
		if (dstRowIdx <= srcRowIdx) 
			return sheet.getRow(srcRowIdx);
		else {
			if (sheet.getLastRowNum() >= dstRowIdx)
				sheet.shiftRows(dstRowIdx, sheet.getLastRowNum(), 1, true, false);
			return createRow(sheet, dstRowIdx, srcRowIdx);
		}
	}
	
	public static void CopyRowsFrom(Sheet sheet, int start, int count, int srcRowIdx) {
		sheet.shiftRows(start, sheet.getLastRowNum(), count, true, false);
		for (var i = 0; i < count; i++) {
			createRow(sheet, start + i, srcRowIdx);
		}
	}

}
