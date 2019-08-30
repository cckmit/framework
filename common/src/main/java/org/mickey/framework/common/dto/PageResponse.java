package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class PageResponse<T> {
    //结果记录
    private List<T> rows;
    //总记录数
    private long total;
}
