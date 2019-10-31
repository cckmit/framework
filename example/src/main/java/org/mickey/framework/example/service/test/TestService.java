package org.mickey.framework.example.service.test;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.service.BaseService;
import org.mickey.framework.example.mapper.TestMapper;
import org.mickey.framework.example.po.Test;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Service
public class TestService extends BaseService<TestMapper, Test> {
}
