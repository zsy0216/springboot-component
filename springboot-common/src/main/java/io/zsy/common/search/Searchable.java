package io.zsy.common.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.zsy.common.search.exception.SearchException;
import io.zsy.common.search.filter.Condition;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 查询条件
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:05
 */
public final class Searchable implements Serializable {

    /**
     * 查询参数 Map
     */
    private Map<Object, Object> searchMap = Maps.newHashMap();
    /**
     * 查询参数 List
     */
    private List<Object> searchList = Lists.newArrayList();

    /**
     * 查询连接符，如：= != like
     */
    private List<Condition> operators = Lists.newArrayList();

    /**
     * controller层分页表单
     * 如:
     * .getPageForm().setPage(1);
     * .getPageForm().setLimit(5);
     * .getPageForm().setSort("id.asc,username.desc");
     * .getPageForm().toPageBounds()
     */
    private PageForm pageForm = new PageForm();

    /**
     * 创建一个新的查询
     *
     * @return
     */
    public static Searchable newSearchable() {
        return new Searchable();
    }

    public Map<Object, Object> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<Object, Object> searchMap) {
        this.searchMap = searchMap;
    }

    public Searchable addSearchMap(Object key, Object value) {
        this.searchMap.put(key, value);
        return this;
    }

    public List<Object> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Object> searchList) {
        this.searchList = searchList;
    }

    public Searchable addSearchList(Object object) {
        this.searchList.add(object);
        return this;
    }

    public List<Condition> getOperators() {
        return operators;
    }

    public Searchable addCondition(Condition condition) {
        this.operators.add(condition);
        return this;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public Searchable addSearchParam(final String key, final Object value) throws SearchException {
        this.operators.add(new Condition(key, value));
        return this;
    }

    public Searchable addSearchParam(final String key, SearchOperator operator, final Object value) throws SearchException {
        this.operators.add(new Condition(key.concat(Condition.SEPARATOR).concat(operator.name()), value));
        return this;
    }

    /** 删除某个搜索条件 */
    public void removeSearchParam(final String key) throws SearchException {
        String[] keys = key.split("_");
        for (int i=0; i<operators.size(); i++) {
            Condition condition = operators.get(i);
            if (Condition.isAcronym(keys[0]).equals(condition.getSearchProperty())
                    && keys[1].equals(condition.getOperator().name())) {
                operators.remove(i);
            }
        }
    }

    /** 取得某个搜索条件的值 */
    public Object getValue(String key) {
        String[] keys = key.split("_");
        Iterator iterator = operators.iterator();
        while (iterator.hasNext()) {
            Condition condition = (Condition) iterator.next();
            if (Condition.isAcronym(keys[0]).equals(condition.getSearchProperty())
                    && keys[1].equals(condition.getOperator().name())) {
                return condition.getValue();
            }
        }
        return null;
    }
}
