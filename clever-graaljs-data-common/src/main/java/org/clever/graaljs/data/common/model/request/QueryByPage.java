package org.clever.graaljs.data.common.model.request;

import lombok.Getter;
import lombok.Setter;
import org.clever.graaljs.data.common.support.mybatisplus.IPage;
import org.clever.graaljs.data.common.support.mybatisplus.Page;

import java.util.List;

/**
 * 分页查询基础类
 * <p>
 * 作者：lzw <br/>
 * 创建时间：2017-09-02 00:41 <br/>
 */
public class QueryByPage extends QueryBySort {
    private static final long serialVersionUID = 1L;

    /**
     * 每页的数据量 - 最大值
     */
    public static final int PAGE_SIZE_MAX = 1000;
    /**
     * 每页的数据量(1 <= pageSize <= 1000)
     */
    private int pageSize = 10;

    /**
     * 当前页面的页码数(pageNo >= 1)
     */
    private int pageNo = 1;
    /**
     * 是否进行 count 查询
     */
    @Setter
    @Getter
    private boolean isSearchCount = true;
    /**
     * 分页
     */
    private IPage<?> page;

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/

    public int getPageSize() {
        if (pageSize > PAGE_SIZE_MAX) {
            pageSize = PAGE_SIZE_MAX;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > PAGE_SIZE_MAX) {
            pageSize = PAGE_SIZE_MAX;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    /**
     * 设置分页对象
     */
    public void page(IPage<?> page) {
        this.page = page;
    }

    /**
     * 获取请求参数对应的 IPage 对象<br />
     * <strong>注意: 当前方法指定要在分页查询执行之后调用否则数据不准确</strong>
     * <pre>
     *     {@code
     *     return query.result(permissionMapper.findByPage(query));
     *     }
     * </pre>
     */
    public <T> Page<T> result(List<T> records) {
        if (page == null) {
            page = new Page<>(getPageNo(), getPageSize());
        }
        Page<T> newPage = new Page<>();
        newPage.setTotal(page.getTotal());
        newPage.setSize(page.getSize());
        newPage.setCurrent(page.getCurrent());
        newPage.setSearchCount(page.isSearchCount());
        newPage.setPages(page.getPages());
        newPage.setRecords(records);
        return newPage;
    }
}
