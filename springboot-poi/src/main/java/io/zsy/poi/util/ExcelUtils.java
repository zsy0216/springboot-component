package io.zsy.poi.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/18
 */
public class ExcelUtils {
	/**
	 * 处理 Excel 数据类型
	 */
	public static Object getValue(Cell cell) {
		Object value = null;
		switch (cell.getCellType()) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			// 数字类型（包含日期和普通数字）
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					value = cell.getDateCellValue();
				} else {
					value = cell.getNumericCellValue();
				}
				break;
			case FORMULA:
				value = cell.getCellFormula();
				break;
			default:
				break;
		}
		return value;
	}
}
