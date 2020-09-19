package org.mickey.framework.core.service;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.PageRequest;
import org.mickey.framework.common.po.AbstractCommonPo;
import org.mickey.framework.core.mybatis.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class GenericServiceBase<M extends BaseMapper<T>, T extends AbstractCommonPo> {

    @Autowired
    private M mapper;

    public int insert(T dto) {
        return mapper.insert(dto);
    }

    public int update(T dto) {
        return mapper.update(dto);
    }

    public int delete(String id) {
        return mapper.delete(id);
    }

    public T query(String id) {
        T po = mapper.get(id);
        return po;
    }

    public List<T> findAll() {
        List<T> poList = mapper.findAll();
        return poList;
    }

    public PageInfo<T> find(int pageNumber, int pageSize) {
        PageInfo<T> pageResponse = mapper.findPage(new PageRequest(pageNumber, pageSize));
        return pageResponse;
    }
}
