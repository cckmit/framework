package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.query.v2.Criteria;

/**
 * 分页请求参数
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class PageRequest {
    /**
     * 是否自动计算总行数
     */
    private boolean needCount = true;
    /**
     * 是否自动分页
     */
    private boolean needPaging = true;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 页码号
     */
    private int pageNo;
    /**
     * 查询条件
     */
    private Criteria criteria;

    public PageRequest() {
        this.pageNo = 1;
        this.pageSize = 10;
    }

    public PageRequest(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
