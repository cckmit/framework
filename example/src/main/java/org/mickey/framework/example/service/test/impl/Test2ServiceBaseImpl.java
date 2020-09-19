package org.mickey.framework.example.service.test.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.service.GenericServiceBase;
import org.mickey.framework.example.mapper.Test2Mapper;
import org.mickey.framework.example.po.Test2;
import org.mickey.framework.example.service.test.ITest2Service;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Service
public class Test2ServiceBaseImpl extends GenericServiceBase<Test2Mapper, Test2> implements ITest2Service {
}
