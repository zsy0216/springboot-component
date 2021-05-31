package io.zsy.poi.util;

import io.zsy.poi.service.ReadService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangshuaiyin
 * @date 2021-05-31 10:46
 */
@Slf4j
@SpringBootTest
public class StockReadTest {
    @Autowired
    ReadService readService;

    final String path = "D:\\Projects\\springboot-component\\springboot-poi\\src\\test\\resources\\stock.xlsx";

    @Test
    public void test() throws IOException {
        System.out.println("readService.readCell(path, 0, 1, 3) = " + readService.readCell(path, 0, 1, 3));
    }

    @Test
    public void readStock() throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook("D:\\Projects\\springboot-component\\springboot-poi\\src\\test\\resources\\stock.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        // 得到最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        log.info("共有 {} 行", lastRowNum + 1);
        List<Stock> stockList = new ArrayList<>();
        Stock stock;
        // 遍历行
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            // 遍历列
            stock = new Stock();
            stock.setId((String) ExcelUtils.getValue(row.getCell(0)));
            stock.setCardNo((String) ExcelUtils.getValue(row.getCell(1)));
            stock.setCardPass(ExcelUtils.getValue(row.getCell(2)).toString());
            stock.setBatchNo(ExcelUtils.getValue(row.getCell(3)).toString());
            String dates = (String) ExcelUtils.getValue(row.getCell(4));
            String[] split = dates.split("至");
            stock.setEffectStartTime(string2Date(split[0]));
            stock.setEffectEndTime(string2Date(split[1]));
            stock.setEffectDuration((String) ExcelUtils.getValue(row.getCell(5)));
            stock.setActiveStatus("0");
            stock.setCreateTime(string2Date((String) ExcelUtils.getValue(row.getCell(7))));
            stock.setChannel((String) ExcelUtils.getValue(row.getCell(8)));
            stock.setCreateUser("SYSTEM");
            stock.setUpdateUser("SYSTEM");
            stock.setUpdateTime(stock.getCreateTime());
            stock.setVersionNumber(1);
            stockList.add(stock);
        }
        log.info("添加了 {} 条数据", stockList.size());
        stockList.forEach(System.out::println);
    }

    public Date string2Date(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}

@Data
class Stock {
    /**
     * 序列号
     */
    private String id;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 卡密
     */
    private String cardPass;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 有效起始时间
     */
    private Date effectStartTime;

    /**
     * 有效结束时间
     */
    private Date effectEndTime;

    /**
     * 有效时长
     */
    private String effectDuration;

    /**
     * 激活状态 0:未使用 1:已使用
     */
    private String activeStatus;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间, 首次默认值为创建时间
     */
    private Date updateTime;

    /**
     * 更新人, 首次默认值为创建人
     */
    private String updateUser;

    /**
     * 记录版本号
     */
    private Integer versionNumber;
}
