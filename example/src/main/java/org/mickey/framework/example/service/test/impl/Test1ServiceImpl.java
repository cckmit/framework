package org.mickey.framework.example.service.test.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.service.GenericService;
import org.mickey.framework.example.mapper.Test1Mapper;
import org.mickey.framework.example.po.Test1;
import org.mickey.framework.example.service.test.ITest1Service;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Service
public class Test1ServiceImpl extends GenericService<Test1Mapper, Test1> implements ITest1Service {
}
