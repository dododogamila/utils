package cn.zxj.utils.page.dto;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<E> implements Serializable {

    private static final long serialVersionUID = 1730123421731807246L;

    public static int DEFAULT_PAGE_SIZE = 20;
    public static int DEFAULT_PAGE_NO = 1;

    private int pageSize; // 页面大小 需赋值
    private int pageNo; // 页码 1based 需赋值
    private long total; // 数据总数 需赋值
    private long pageCount;
    private long limitStart;
    private List<E> rows;

    public Page() {
        this(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE, 0, new ArrayList<E>());
    }

    public long getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(long limitStart) {
        this.limitStart = limitStart;
    }

    public Page(int pageNo, int pageSize, long total, List<E> rows) {
        super();
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.total = total;
        this.rows = rows;
        this.pageCount = getTotalPage();
    }

    public Page(int pageNo, int pageSize) {
        super();
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.total = 0;
        this.rows = new ArrayList<E>();
    }

    public void setData(List<E> rows, long totalRowCount) {
        this.rows = rows;
        this.total = totalRowCount;
        this.pageCount = getTotalPage();
    }

    public void addPageCondition(int pageSize, int pageNo) {
        this.pageNo = pageNo == 0 ? Page.DEFAULT_PAGE_NO : pageNo;
        this.pageSize = pageSize == 0 ? Page.DEFAULT_PAGE_SIZE : pageSize;
        this.limitStart = (pageNo - 1) * this.pageSize;
    }

    public boolean hasNextPage() {
        return this.pageNo < this.pageCount;
    }

    public RowBounds rowBounds() {
        return new RowBounds((pageNo - 1) * pageSize, pageSize);
    }

    public static int getStartOfPage(int current, int number) {
        return (current - 1) * number;
    }

    /**
     * 获得总页数
     */
    public long getTotalPage() {
        return total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }

    public static int getDEFAULT_PAGE_SIZE() {
        return DEFAULT_PAGE_SIZE;
    }

    public static void setDEFAULT_PAGE_SIZE(int dEFAULT_PAGE_SIZE) {
        DEFAULT_PAGE_SIZE = dEFAULT_PAGE_SIZE;
    }

    public static int getDEFAULT_PAGE_NO() {
        return DEFAULT_PAGE_NO;
    }

    public static void setDEFAULT_PAGE_NO(int dEFAULT_PAGE_NO) {
        DEFAULT_PAGE_NO = dEFAULT_PAGE_NO;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
}
