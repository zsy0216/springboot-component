package io.zsy.common.search;

import io.zsy.common.search.exception.SearchException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 *
 * <p>查询操作符</p>
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:05
 **/
public enum SearchOperator {
    eq("等于", "="), ne("不等于", "!="),
    gt("大于", ">"), gte("大于等于", ">="), lt("小于", "<"), lte("小于等于", "<="),
    prefixLike("前缀模糊匹配", "like"), prefixNotLike("前缀模糊不匹配", "not like"),
    suffixLike("后缀模糊匹配", "like"), suffixNotLike("后缀模糊不匹配", "not like"),
    like("模糊匹配", "like"), notLike("不匹配", "not like"),
    isNull("空", "is null"), isNotNull("非空", "is not null"),
    in("包含", "in"), notIn("不包含", "not in"), custom("自定义默认的", null),
    dateYMEq("日期月等于", "="), dateYMne("日期月不等于", "!="),
    dateYMgt("日期月大于", ">"), dateYMgte("日期月大于等于", ">="), dateYMlt("日期月小于", "<"), dateYMlte("日期月小于等于", "<="),
    dateYMDEq("日期等于", "="), dateYMDne("日期不等于", "!="),
    dateYMDgt("日期大于", ">"), dateYMDgte("日期大于等于", ">="), dateYMDlt("日期小于", "<"), dateYMDlte("日期小于等于", "<="),
    eqOr("等于", "=");

    private final String info;
    private final String symbol;

    SearchOperator(final String info, String symbol) {
        this.info = info;
        this.symbol = symbol;
    }

    public String getInfo() {
        return info;
    }

    public String getSymbol() {
        return symbol;
    }

    public static String toStringAllOperator() {
        return Arrays.toString(SearchOperator.values());
    }

    /**
     * 操作符是否允许为空
     *
     * @param operator
     * @return
     */
    public static boolean isAllowBlankValue(final SearchOperator operator) {
        return operator == SearchOperator.isNotNull || operator == SearchOperator.isNull;
    }


    public static SearchOperator valueBySymbol(String symbol) throws SearchException {
        symbol = formatSymbol(symbol);
        for (SearchOperator operator : values()) {
            if (operator.getSymbol().equals(symbol)) {
                return operator;
            }
        }

        throw new SearchException("SearchOperator not method search operator symbol : " + symbol);
    }

    public static SearchOperator valueByOperator(String op) throws SearchException {
        for (SearchOperator operator : values()) {
            if (operator.toString().equals(op)) {
                return operator;
            }
        }

        throw new SearchException("SearchOperator not method search operator : " + op);
    }

    private static String formatSymbol(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return symbol;
        }
        return symbol.trim().toLowerCase().replace("  ", " ");
    }
}
