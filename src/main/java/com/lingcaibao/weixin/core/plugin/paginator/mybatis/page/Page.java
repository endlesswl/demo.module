package com.lingcaibao.weixin.core.plugin.paginator.mybatis.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页封装函数
 */
public class Page<T> {
    /**
     * 分页数据
     */
    protected List<T> result;

    private Map<String, Object> searchFields;

    private Map<String, Object> sortFields;

    /**
     * 总页数 这个数是计算出来的
     */
    private int totalPages;

    /**
     * 每页显示几条记录
     */
    private int pageSize = 10;

    /**
     * 默认 当前页 为第一页 这个数是计算出来的
     */
    private int currentPage = 1;

    /**
     * 总记录数
     */
    private int totalCount = 0;

    /**
     * 从第几条记录开始
     */
    private int startPage;

    /**
     * 规定显示5个页码
     */
    private int pagecode = 5;

    public Page() {
    }

    public Page(PageRequest p, int totalCount) {
        this(p.getPage(), p.getLimit(), totalCount);
    }

    public Page(int pageNumber, int pageSize, int totalCount) {
        this(pageNumber, pageSize, totalCount, new ArrayList<T>(0));
    }

    public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {
        if (pageSize <= 0)
            throw new IllegalArgumentException(
                    "[pageSize] must great than zero");
        this.pageSize = pageSize;
        this.currentPage = PageUtils.computePageNumber(pageNumber, pageSize,
                totalCount);
        this.totalCount = totalCount;
        setResult(result);
    }

    /**
     * 使用构造函数，，强制必需输入 每页显示数量　和　当前页
     *
     * @param pageSize 　　每页显示数量
     * @param pageNow  　当前页
     */
    public Page(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    /**
     * 使用构造函数，，强制必需输入 当前页
     *
     * @param pageNow 　当前页
     */
    public Page(int currentPage) {
        this.currentPage = currentPage;
        startPage = (this.currentPage - 1) * this.pageSize;
    }

    /**
     * 要获得记录的开始索引　即　开始页码
     *
     * @return
     */
    public int getFirstResult() {
        return (this.currentPage - 1) * this.pageSize;
    }

    /**
     * 是否是首页（第一页），第一页页码为1
     *
     * @return 首页标识
     */
    public boolean isFirstPage() {
        return getCurrentPage() == 1;
    }

    /**
     * 是否是最后一页
     *
     * @return 末页标识
     */
    public boolean isLastPage() {
        return getCurrentPage() >= getLastPageNumber();
    }

    /**
     * 是否有下一页
     *
     * @return 下一页标识
     */
    public boolean hasNextPage() {
        return getLastPageNumber() > getCurrentPage();
    }

    /**
     * 是否有上一页
     *
     * @return 上一页标识
     */
    public boolean hasPreviousPage() {
        return getCurrentPage() > 1;
    }

    /**
     * 获取最后一页页码，也就是总页数
     *
     * @return 最后一页页码
     */
    public int getLastPageNumber() {
        return PageUtils.computeLastPageNumber(totalCount, pageSize);
    }

    /**
     * 获取下一页编码
     *
     * @return 下一页编码
     */
    public int getNextPageNumber() {
        return getCurrentPage() + 1;
    }

    /**
     * 获取上一页编码
     *
     * @return 上一页编码
     */
    public int getPreviousPageNumber() {
        return getCurrentPage() - 1;
    }

    /**
     * 查询结果方法 把　记录数　结果集合　放入到　PageView对象
     *
     * @param totalCount 总记录数
     * @param result     结果集合
     */

    public void setQueryResult(int totalCount, List<T> result) {
        setTotalCount(totalCount);
        setResult(result);
    }

    public void setResult(List<T> elements) {
        if (elements == null)
            throw new IllegalArgumentException("'result' must be not null");
        this.result = elements;
    }

    /**
     * 当前页包含的数据
     *
     * @return 当前页数据源
     */
    public List<T> getResult() {
        return result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        setTotalPages(this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : this.totalCount / this.pageSize + 1);
    }

    public int getPagecode() {
        return pagecode;
    }

    public void setPagecode(int pagecode) {
        this.pagecode = pagecode;
    }

    public Map<String, Object> getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(Map<String, Object> searchFields) {
        this.searchFields = searchFields;
    }

    public Map<String, Object> getSortFields() {
        return sortFields;
    }

    public void setSortFields(Map<String, Object> sortFields) {
        this.sortFields = sortFields;
    }

    /**
     * 显示的页码
     * <p/>
     * <p/>
     * 这个工具类　返回的是页索引　PageIndex
     * <p/>
     * 在这个方法中存在一个问题，每页显示数量　和　当前页 不能为空 必需输入
     *
     * @param pagecode  要获得记录的开始索引,即开始页码
     * @param pageNow   当前页
     * @param pageCount 总页数
     * @return
     */
    public static void getPageIndex(long pagecode, int pageNow, long pageCount,
                                    PageIndex pageIndex) {
        long startpage = pageNow
                - (pagecode % 2 == 0 ? pagecode / 2 - 1 : pagecode / 2);
        long endpage = pageNow + pagecode / 2;
        if (startpage < 1) {
            startpage = 1;
            if (pageCount >= pagecode)
                endpage = pagecode;
            else
                endpage = pageCount;
        }
        if (endpage > pageCount) {
            endpage = pageCount;
            if ((endpage - pagecode) > 0)
                startpage = endpage - pagecode + 1;
            else
                startpage = 1;
        }
        pageIndex.setStartindex(startpage);
        pageIndex.setEndindex(endpage);
    }

}
