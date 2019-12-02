package org.mickey.framework.core.service.impl;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.CommonPo;
import org.mickey.framework.core.service.IBaseService;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class BaseServiceImpl<T extends CommonPo>  {

    //implements IBaseService<T>

//    @Override
//    public int insert(T dto) {
//        return 0;
//    }
//
//    @Override
//    public int update(T dto) {
//        return 0;
//    }
//
//    @Override
//    public int delete(String id) {
//        return 0;
//    }
//
//    @Override
//    public T query(String id) {
//        return null;
//    }
//
//    @Override
//    public List<T> findAll() {
//        return null;
//    }
//
//    @Override
//    public PageInfo<T> find(int pageNumber, int pageSize) {
//        return null;
//    }
}
