package io.zsy.poi.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/18
 */
@SpringBootTest
public class ExcelReadTest {

	@Test
	public void readCell() throws IOException {
		Workbook workbook = new XSSFWorkbook("C:\\Users\\59498\\Desktop\\list.xlsx");
		Sheet sheet = workbook.getSheetAt(2);
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(0);
		Object value = ExcelUtils.getValue(cell);
		System.out.println(value.toString());
	}

	/**
	 * 读取该Excel表中所有的行，按行输出
	 * 文件格式为xlsx 对应 XSSF
	 * 不能有空行！！！
	 *
	 * @throws IOException
	 */
	@Test
	public void readData() throws IOException {
		Workbook workbook = new XSSFWorkbook("C:\\Users\\59498\\Desktop\\list.xlsx");
		Sheet sheet = workbook.getSheetAt(2);
		// 得到最后一行行号
		int lastRowNum = sheet.getLastRowNum();
		System.out.println("共有" + (lastRowNum + 1) + "行");
		for (int i = 0; i <= lastRowNum; i++) {
			// 得到第i行
			Row row = sheet.getRow(i);
			short lastCellNum = row.getLastCellNum();
			System.out.print("第" + (i + 1) + "行有" + lastCellNum + "个数据: ");
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < lastCellNum; j++) {
				// 得到第j个单元格
				Cell cell = row.getCell(j);
				// 根据类型得到值
				Object value = ExcelUtils.getValue(cell);
				sb.append(value + " ");
			}
			System.out.println(sb.toString());
		}
	}


	public String generateSql(String lang, String code, String i18n) {
		String sql = "insert into i18n_info(language_type, source_value, i18n_value, create_by, update_by) values('" + lang + "', '" + code + "', '" + i18n + "', 'System', 'System');";
		return sql;
	}
	@Test
	public void I18N() throws IOException{
		String language;
		Workbook workbook = new XSSFWorkbook("C:\\Users\\59498\\Desktop\\list.xlsx");
		Sheet sheet = workbook.getSheetAt(2);
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			short lastCellNum = row.getLastCellNum();
			String code = ExcelUtils.getValue(row.getCell(0)).toString();
			for (int j = 1; j < lastCellNum; j++) {
				Cell cell = row.getCell(j);
				if (j == 1) {
					language = "zh";
					String i18n_zh = ExcelUtils.getValue(cell).toString();
					System.out.println(generateSql(language, code, i18n_zh));
				}
				if (j ==2){
					language = "en";
					String i18n_en = ExcelUtils.getValue(cell).toString();
					System.out.println(generateSql(language, code, i18n_en));
				}
			}
		}
	}
}
