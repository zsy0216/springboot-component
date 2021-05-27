package io.zsy.common.search;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分页查询对象
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:05
 */
public class PageBounds extends RowBounds implements Serializable {

    public static final int NO_PAGE = 1;
    /**
     * 页号
     */
    protected int page = NO_PAGE;
    /**
     * 分页大小
     */
    protected int limit = NO_ROW_LIMIT;
    /**
     * 分页排序信息
     */
    private List<Order> orders = new ArrayList<>();
    /**
     * 结果集是否包含TotalCount
     */
    protected boolean containsTotalCount = true;

    protected Boolean asyncTotalCount;

    /**
     * 默认构造函数不提供分页，返回ArrayList
     */
    public PageBounds() {
    }

    public PageBounds(RowBounds rowBounds) {
        if (rowBounds instanceof PageBounds) {
            PageBounds pageBounds = (PageBounds) rowBounds;
            this.page = pageBounds.page;
            this.limit = pageBounds.limit;
            this.orders = pageBounds.orders;
            this.containsTotalCount = pageBounds.containsTotalCount;
            this.asyncTotalCount = pageBounds.asyncTotalCount;
        } else {
            this.page = (rowBounds.getOffset() / rowBounds.getLimit()) + 1;
            this.limit = rowBounds.getLimit();
        }

    }

    /**
     * Query TOP N, default containsTotalCount = false
     * 取 TOP N 作，返回ArrayList
     *
     * @param limit
     */
    public PageBounds(int limit) {
        this.limit = limit;
        this.containsTotalCount = false;
    }

    /**
     * 默认分页，返回PageList
     *
     * @param page
     * @param limit
     */
    public PageBounds(int page, int limit) {
        this(page, limit, new ArrayList<>(), true);
    }

    /**
     * 分页不排序，使用containsTotalCount来决定查不查询totalCount，即返回ArrayList还是PageList
     *
     * @param page
     * @param limit
     * @param containsTotalCount
     */
    public PageBounds(int page, int limit, boolean containsTotalCount) {
        this(page, limit, new ArrayList<>(), containsTotalCount);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * 只排序不分页，返回ArrayList
     *
     * @param orders
     */
    public PageBounds(List<Order> orders) {
        this(NO_PAGE, NO_ROW_LIMIT, orders, false);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * 只排序不分页，返回ArrayList
     *
     * @param order
     */
    public PageBounds(Order... order) {
        this(NO_PAGE, NO_ROW_LIMIT, order);
        this.containsTotalCount = false;
    }

    /**
     * 分页加排序，返回PageList
     *
     * @param page
     * @param limit
     * @param order
     */
    public PageBounds(int page, int limit, Order... order) {
        this(page, limit, Arrays.asList(order), true);
    }

    /**
     * 分页加排序，返回PageList
     *
     * @param page
     * @param limit
     * @param orders
     */
    public PageBounds(int page, int limit, List<Order> orders) {
        this(page, limit, orders, true);
    }

    /**
     * 分页并排序，使用containsTotalCount来决定查不查询totalCount，即返回ArrayList还是PageList
     *
     * @param page
     * @param limit
     * @param orders
     * @param containsTotalCount
     */
    public PageBounds(int page, int limit, List<Order> orders, boolean containsTotalCount) {
        this.page = page;
        this.limit = limit;
        this.orders = orders;
        this.containsTotalCount = containsTotalCount;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isContainsTotalCount() {
        return containsTotalCount;
    }

    public void setContainsTotalCount(boolean containsTotalCount) {
        this.containsTotalCount = containsTotalCount;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Boolean getAsyncTotalCount() {
        return asyncTotalCount;
    }

    public void setAsyncTotalCount(Boolean asyncTotalCount) {
        this.asyncTotalCount = asyncTotalCount;
    }

    @Override
    public int getOffset() {
        if (page >= 1) {
            return (page - 1) * limit;
        }
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageBounds{");
        sb.append("page=").append(page);
        sb.append(", limit=").append(limit);
        sb.append(", orders=").append(orders);
        sb.append(", containsTotalCount=").append(containsTotalCount);
        sb.append(", asyncTotalCount=").append(asyncTotalCount);
        sb.append('}');
        return sb.toString();
    }
}
