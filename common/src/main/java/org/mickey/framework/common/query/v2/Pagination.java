package org.mickey.framework.common.query.v2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class Pagination<T> implements Serializable {
    //是否自动计算总行数
    private boolean counting = true;
    //是否自动分页
    private boolean paging = true;
    //每页记录数
    private int pageSize = 10;
    //页码号
    private int pageNo = 1;
    //起始记录数 默认-1
    private int start = 0;
    //最大记录数 默认-1
    private long count = 0;
    //结果记录
    private List<T> rows;
    //查询条件
    private Criteria criteria;
}
