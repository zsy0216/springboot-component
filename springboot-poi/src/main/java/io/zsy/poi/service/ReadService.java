package io.zsy.poi.service;

import java.io.IOException;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/18
 */
public interface ReadService {

	/**
	 * 读取指定单元格信息
	 *
	 * @param path       excel 所在路径
	 * @param sheetIndex 所在页索引
	 * @param rowIndex   所在行索引
	 * @param cellIndex  所在列索引
	 * @return 单元格数据
	 * @throws IOException IOException
	 */
	Object readCell(String path, Integer sheetIndex, Integer rowIndex, Integer cellIndex) throws IOException;

}
