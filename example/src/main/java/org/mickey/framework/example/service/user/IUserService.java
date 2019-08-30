package org.mickey.framework.example.service.user;

import org.mickey.framework.example.po.UserPo;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface IUserService {
    void insert(UserPo user);
    void update(UserPo user);
    void delete(String id);
    UserPo query(String id);
    List<UserPo> findAll();
}
