package io.zsy.poi.service.impl;

import io.zsy.poi.service.ReadService;
import io.zsy.poi.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/18
 */
public class ReadServiceImpl implements ReadService {

	@Override
	public Object readCell(String path, Integer sheetIndex, Integer rowIndex, Integer cellIndex) throws IOException {
		Workbook workbook = new XSSFWorkbook(path);
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Row row = sheet.getRow(rowIndex);
		Cell cell = row.getCell(cellIndex);
		return ExcelUtils.getValue(cell);
	}
}
