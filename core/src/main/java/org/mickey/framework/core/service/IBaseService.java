package org.mickey.framework.core.service;

import com.github.pagehelper.PageInfo;
import org.mickey.framework.common.po.CommonPo;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface IBaseService<T extends CommonPo> {

    int insert(T dto);

    int update(T dto);

    int delete(String id);

    T query(String id);

    List<T> findAll();

    PageInfo<T> find(int pageNumber, int pageSize);
}
