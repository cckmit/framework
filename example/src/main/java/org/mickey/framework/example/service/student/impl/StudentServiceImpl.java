package org.mickey.framework.example.service.student.impl;

import org.mickey.framework.core.service.GenericServiceBase;
import org.mickey.framework.example.mapper.IStudentMapper;
import org.mickey.framework.example.po.Student;
import org.mickey.framework.example.service.student.IStudentService;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author wangmeng
 * @version 1.0.0
 * @ClassName StudentServiceImpl.java
 * @Description TODO
 * @createTime 2020年06月19日 11:21:00
 */
@Service
public class StudentServiceImpl extends GenericServiceBase<IStudentMapper, Student> implements IStudentService {
}
