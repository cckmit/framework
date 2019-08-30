package org.mickey.framework.example.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.example.mapper.UserMapper;
import org.mickey.framework.example.po.UserPo;
import org.mickey.framework.example.service.user.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper mapper;

    @Override
    public void insert(UserPo user) {
        int i = mapper.insert(user);
        log.debug(StringUtil.valueOf(i));
    }

    @Override
    public void update(UserPo user) {
        int i = mapper.update(user);
        log.debug(StringUtil.valueOf(i));
    }

    @Override
    public void delete(String id) {
        int i = mapper.delete(id);
        log.debug(StringUtil.valueOf(i));
    }

    @Override
    public UserPo query(String id) {
        UserPo userPo = mapper.get(id);
        log.debug(JSON.toJSONString(userPo, SerializerFeature.PrettyFormat));
        return userPo;
    }

    @Override
    public List<UserPo> findAll() {
        List<UserPo> poList = mapper.findAll();
        log.debug(JSON.toJSONString(poList, SerializerFeature.PrettyFormat));
        return poList;
    }
}
