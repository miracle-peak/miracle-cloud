package pers.miracle.miraclecloud.common.utils;

/**
 * @author miracle
 */
public class PageInfo {

    private Long total;
    private Long pageSize;
    private Long currentPage;
    private Object rows;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public PageInfo() {
    }

    public PageInfo(Long total, Long pageSize, Long currentPage, Object rows) {
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "total=" + total +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}
