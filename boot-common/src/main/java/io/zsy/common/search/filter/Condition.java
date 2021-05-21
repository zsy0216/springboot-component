package io.zsy.common.search.filter;

import io.zsy.common.search.SearchOperator;
import io.zsy.common.search.exception.SearchException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 筛选条件
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:05
 **/
public final class Condition implements SearchFilter, Serializable {
    /**
     * 查询参数分隔符
     */
    public static final String SEPARATOR = "_";

    private String key;
    private String sqlSeparator = "and";
    private final String sqlOrSeparator = "or";
    private String searchProperty;
    private SearchOperator operator;
    private Object value;

    public Condition() {

    }

    /**
     * 根据查询key和值生成Condition
     *
     * @param key   如 name_like
     * @param value
     * @return
     */
    public Condition(final String key, final Object value) {

        Assert.notNull(key, "Condition key must not null");

        String[] searches = StringUtils.split(key, SEPARATOR);

        if (searches.length == 0) {
            throw new SearchException("Condition key format must be : property or property_op");
        }

        this.searchProperty = isAcronym(searches[0]);
        this.operator = SearchOperator.valueByOperator(searches[1]);
        this.searchProperty = formSearchProperty(this.searchProperty);
        this.value = formValue(operator, value);
        if (operator == SearchOperator.eqOr) {
            this.key = this.sqlOrSeparator + " " + this.searchProperty + " " + getOperatorStr();
        } else {
            this.key = this.sqlSeparator + " " + this.searchProperty + " " + getOperatorStr();
        }
    }

    public Condition(final String sqlSeparator, final String searchProperty, final SearchOperator operator, final Object value) {
        this.sqlSeparator = sqlSeparator;
        this.searchProperty = isAcronym(searchProperty);
        this.operator = operator;
        this.searchProperty = formSearchProperty(this.searchProperty);
        this.value = formValue(operator, value);
        if (operator == SearchOperator.eqOr) {
            this.key = this.sqlOrSeparator + " " + this.searchProperty + " " + getOperatorStr();
        } else {
            this.key = this.sqlSeparator + " " + this.searchProperty + " " + getOperatorStr();
        }
    }

    public Condition(final String searchProperty, final SearchOperator operator, final Object value) {
        this.searchProperty = isAcronym(searchProperty);
        this.operator = operator;
        this.searchProperty = formSearchProperty(this.searchProperty);
        this.value = formValue(operator, value);
        if (operator == SearchOperator.eqOr) {
            this.key = this.sqlOrSeparator + " " + this.searchProperty + " " + getOperatorStr();
        } else {
            this.key = this.sqlSeparator + " " + this.searchProperty + " " + getOperatorStr();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSqlSeparator() {
        return sqlSeparator;
    }

    public void setSqlSeparator(String sqlSeparator) {
        this.sqlSeparator = sqlSeparator;
    }

    public String getSearchProperty() {
        return searchProperty;
    }

    public void setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperatorStr() {
        if (operator != null) {
            return operator.getSymbol();
        }
        return "";
    }

    public static String isAcronym(String word) {
        StringBuilder v = new StringBuilder();
        String tmp = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                tmp = "_" + String.valueOf(c).toLowerCase();
            } else {
                tmp = String.valueOf(c);
            }
            v.append(tmp);
        }
        return v.toString();
    }

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private Object formValue(SearchOperator operator, Object value) {
        if (SearchOperator.isAllowBlankValue(operator)) {
            return "";
        }
        if (TRUE.equals(value)) {
            value = true;
        }
        if (FALSE.equals(value)) {
            value = false;
        }
        if (operator == SearchOperator.like || operator == SearchOperator.notLike) {
            return "%" + value + "%";
        }
        if (operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
            return value + "%";
        }
        if (operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
            return "%" + value;
        }
        return (operator == SearchOperator.in || operator == SearchOperator.notIn) && value instanceof String ? "(" + value + ")" : value;
    }

    private static final String REG_YM = "DATE_FORMAT(%s," + "'%Y-%m')";
    private static final String REG_YMD = "DATE_FORMAT(%s," + "'%Y-%m-%d')";

    private String formSearchProperty(String searchProperty) {
        if (operator == SearchOperator.dateYMEq) {
            return String.format(REG_YM, searchProperty);
        } else if (operator == SearchOperator.dateYMne) {
            return String.format(REG_YM, searchProperty);
        } else if (operator == SearchOperator.dateYMgt) {
            return String.format(REG_YM, searchProperty);
        } else if (operator == SearchOperator.dateYMgte) {
            return String.format(REG_YM, searchProperty);
        } else if (operator == SearchOperator.dateYMlt) {
            return String.format(REG_YM, searchProperty);
        } else if (operator == SearchOperator.dateYMlte) {
            return String.format(REG_YM, searchProperty);
        } else if (operator == SearchOperator.dateYMDEq) {
            return String.format(REG_YMD, searchProperty);
        } else if (operator == SearchOperator.dateYMDne) {
            return String.format(REG_YMD, searchProperty);
        } else if (operator == SearchOperator.dateYMDgt) {
            return String.format(REG_YMD, searchProperty);
        } else if (operator == SearchOperator.dateYMDgte) {
            return String.format(REG_YMD, searchProperty);
        } else if (operator == SearchOperator.dateYMDlt) {
            return String.format(REG_YMD, searchProperty);
        } else if (operator == SearchOperator.dateYMDlte) {
            return String.format(REG_YMD, searchProperty);
        } else {
            return searchProperty;
        }
    }
}
