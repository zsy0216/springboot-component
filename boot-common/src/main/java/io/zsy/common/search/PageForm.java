package io.zsy.common.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页条件
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:05
 **/
public class PageForm implements Serializable {

    public static final String CHINESE_PINYIN = "nlssort(? ,'NLS_SORT=SCHINESE_PINYIN_M')";

    private Integer page;
    private Integer limit;
    private String sort;
    private List<Order> orders;

    public PageForm() {

    }

    public PageForm(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public PageForm(int page, int limit, String sort) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        toOrders();
    }

    public PageForm(String sort) {
        this.sort = sort;
        toOrders();
    }

    private void toOrders() {
        List<Order> orders = Order.formString(sort);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (orderExprs.get(order.getProperty()) != null) {
                orders.set(i, new Order(
                        order.getProperty(),
                        order.getDirection(),
                        orderExprs.get(order.getProperty()))
                );
            }
        }
        this.orders = orders;
    }

    private Map<String, String> orderExprs = new HashMap<>();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        toOrders();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrderExpr(String property, String expr) {
        this.orderExprs.put(property, expr);
    }

    public PageBounds toPageBounds() {
        toOrders();
        if (page == null && limit == null && orders.isEmpty()) {
            return new PageBounds();
        } else if (page == null && limit != null && orders.isEmpty()) {
            return new PageBounds(limit);
        } else if (page != null && limit != null) {
            return new PageBounds(page, limit);
        } else if (page != null && !orders.isEmpty()) {
            return new PageBounds(page, limit, orders);
        } else {
            return new PageBounds();
        }
    }

    public int getOffset() {
        if (page >= 1) {
            return (page - 1) * limit;
        }
        return 0;
    }

}
